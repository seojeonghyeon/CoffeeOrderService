package com.justin.teaorderservice.modules.order;

import com.justin.teaorderservice.modules.exception.ComplexException;
import com.justin.teaorderservice.modules.exception.ErrorCode;
import com.justin.teaorderservice.modules.order.form.ItemOrderForm;
import com.justin.teaorderservice.modules.order.form.ItemPurchaseForm;
import com.justin.teaorderservice.modules.order.request.RequestItemOrder;
import com.justin.teaorderservice.modules.order.request.RequestItemPurchase;
import com.justin.teaorderservice.modules.tea.Tea;
import com.justin.teaorderservice.modules.tea.TeaOrder;
import com.justin.teaorderservice.modules.tea.TeaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final TeaService teaService;

    @Override
    public Order findById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Transactional
    @Override
    public Long addViewOrder(BindingResult bindingResult, ItemPurchaseForm itemPurchaseForm) {
        List<Tea> teas = teaService.findAll();
        List<TeaOrder> teaOrderList = new ArrayList<>();

        List<ItemOrderForm> itemOrderFormList = itemPurchaseForm.getItemOrderFormList();

        int tea_max = teas.size();

        for(int i = 0; i < tea_max; ++i){
            ItemOrderForm itemOrderForm = itemOrderFormList.get(i);
            boolean isNotZeroTheOrderQuantity = itemOrderForm.getOrderQuantity() != 0;
            if(isNotZeroTheOrderQuantity) {
                Tea tea = teas.get(i);
                Integer remaining = tea.getQuantity() - itemOrderForm.getOrderQuantity();
                boolean isNoRemaining = remaining < 0;

                /**
                 * 재고가 없을 경우
                 */
                if(isNoRemaining){
                    bindingResult.reject("noRemaining",
                            new Object[]{itemOrderForm.getOrderQuantity(), tea.getQuantity()}, null);
                }

                /**
                 * 사용자의 Point가 없을 경우
                 */
                //추가 필요

                TeaOrder teaOrder = TeaOrder.builder()
                        .id(tea.getId())
                        .teaName(tea.getTeaName())
                        .quantity(tea.getQuantity())
                        .orderQuantity(itemOrderForm.getOrderQuantity())
                        .price(tea.getPrice())
                        .build();
                teaOrderList.add(teaOrder);
            }
        }
        if(bindingResult.hasErrors()){
            return 0L;
        }

        Order saveOrder = saveOrder(itemPurchaseForm.getUserId(), teaOrderList, tea_max);

        return saveOrder.getId();
    }

    @Transactional
    @Override
    public Long addApiOrder(RequestItemPurchase requestItemPurchase) throws ComplexException {
        Map<String, String> errors = new HashMap<>();
        List<Tea> teas = teaService.findAll();
        List<TeaOrder> teaOrderList = new ArrayList<>();
        List<RequestItemOrder> requestItemOrderList = requestItemPurchase.getRequestItemOrderList();

        int tea_max = teas.size();

        for(int i = 0; i < tea_max; ++i){
            RequestItemOrder requestItemOrder = requestItemOrderList.get(i);
            boolean isNotZeroTheOrderQuantity = requestItemOrder.getOrderQuantity() != 0;
            if(isNotZeroTheOrderQuantity) {
                Tea tea = teas.get(i);
                Integer remaining = tea.getQuantity() - requestItemOrder.getOrderQuantity();
                boolean isNoRemaining = remaining < 0;

                /**
                 * 재고가 없을 경우
                 */
                if(0 == tea.getQuantity()){
                    errors.put(
                            requestItemOrderList.get(i).toString(),
                            String.format(
                                    ErrorCode.NoQuantity.getDescription()
                            )
                    );
                }else if(isNoRemaining){
                    errors.put(
                            requestItemOrderList.get(i).toString(),
                            String.format(
                                    ErrorCode.LessQuantityThanOrderQuantity.getDescription(),
                                    tea.getQuantity(),
                                    requestItemOrder.getOrderQuantity()
                            )
                    );
                }

                /**
                 * 사용자의 Point가 없을 경우
                 */
                //추가 필요
            }
        }
        if(!errors.isEmpty()){
            throw new ComplexException(errors);
        }

        Order saveOrder = saveOrder(requestItemPurchase.getUserId(), teaOrderList, tea_max);

        return saveOrder.getId();
    }

    private Order saveOrder(String userId, List<TeaOrder> teaOrderList, int tea_max) {
        /**
         * 재고 감소
         */
        for(int i = 0; i < tea_max; ++i){
            TeaOrder teaOrder = teaOrderList.get(i);
            boolean isNotZeroTheOrderQuantity = teaOrder.getOrderQuantity() != 0;
            if(isNotZeroTheOrderQuantity) {
                Tea tea = teaService.findById(teaOrder.getId());
                Integer remaining = tea.getQuantity() - teaOrder.getOrderQuantity();
                teaOrder.setOrderQuantity(remaining);
                tea.setQuantity(remaining);
                teaService.update(tea.getId(), tea);
            }
        }
        /**
         * 사용자 Point 감소
         */

        /**
         * 주문 저장
         */
        Order order = Order.builder()
                .userId(userId)
                .teaOrderList(teaOrderList)
                .build();
        Order saveOrder = this.save(order);
        return saveOrder;
    }
}
