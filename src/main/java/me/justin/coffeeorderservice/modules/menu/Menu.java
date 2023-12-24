package me.justin.coffeeorderservice.modules.menu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import me.justin.coffeeorderservice.infra.exception.ErrorCode;
import me.justin.coffeeorderservice.infra.exception.NotEnoughStockException;
import me.justin.coffeeorderservice.modules.category.MenuCategory;
import me.justin.coffeeorderservice.modules.order.ProductOrder;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menus")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor
public class Menu {

    @Id @GeneratedValue
    @Column(name = "menu_id")
    private Long id;
    private String menuName;
    private Integer price;
    private Integer stockQuantity;
    private String menuImage;
    private String description;
    private Boolean disabled;

    @OneToMany(mappedBy = "menu")
    private List<MenuCategory> menuCategories = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "menu")
    private List<ProductOrder> productOrders;

    public void addProductOrder(ProductOrder productOrder){
        productOrders.add(productOrder);
        productOrder.setMenu(this);
    }

    public void addMenuCategory(MenuCategory menuCategory){
        menuCategories.add(menuCategory);
        menuCategory.setMenu(this);
    }

    public void addStock(Integer quantity){
        this.stockQuantity += quantity;
        if(disabled){
            setDisabled(false);
        }
    }

    public void removeStock(Integer quantity){
        int restStock = this.stockQuantity - quantity;
        if(restStock < 0){
            throw new NotEnoughStockException(ErrorCode.NOT_ENOUGH_STOCK);
        }
        this.stockQuantity = restStock;
        if(this.stockQuantity == 0){
            this.disabled = true;
        }
    }

}
