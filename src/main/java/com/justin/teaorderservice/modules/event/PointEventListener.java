package com.justin.teaorderservice.modules.event;


import com.justin.teaorderservice.infra.config.AppProperties;
import com.justin.teaorderservice.infra.mail.EmailMessage;
import com.justin.teaorderservice.infra.mail.EmailService;
import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.modules.point.Point;
import com.justin.teaorderservice.modules.point.PointRepository;
import com.justin.teaorderservice.modules.point.PointStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Slf4j
@Async
@Component
@Transactional
@RequiredArgsConstructor
public class PointEventListener {
    private static final String MESSAGE_PENDING = "준비";
    private static final String MESSAGE_CONFIRMED = "확인";
    private static final String MESSAGE_CANCELED = "취소";
    private static final String MESSAGE_REJECTED = "거절";
    private static final String MESSAGE_COMPLETED = "완료";

    static final String ROOT = "/api/order/points";

    private final PointRepository pointRepository;
    private final AppProperties appProperties;
    private final TemplateEngine templateEngine;
    private final EmailService emailService;

    @TransactionalEventListener
    public void handleOrderCreatedEvent(PointCreatedEvent pointCreatedEvent){
        Point point = pointRepository.findById(pointCreatedEvent.getPoint().getId()).orElse(null);
        Member member = point.getMember();
        String emailSubject = "[TeaOrderService] Point 충전";
        String prefixContent = "충전이 ";
        String suffixContent = " 되었습니다.";

        if (point.getStatus() == PointStatus.CONFIRMED) {
            sendPointCreatedEmail(point, member, prefixContent + MESSAGE_CONFIRMED + suffixContent, emailSubject);
        } else if (point.getStatus() == PointStatus.CANCELED) {
            sendPointCreatedEmail(point, member, prefixContent + MESSAGE_CANCELED + suffixContent, emailSubject);
        } else if (point.getStatus() == PointStatus.REJECTED) {
            sendPointCreatedEmail(point, member, prefixContent + MESSAGE_REJECTED + suffixContent, emailSubject);
        } else if (point.getStatus() == PointStatus.COMPLETED) {
            sendPointCreatedEmail(point, member, prefixContent + MESSAGE_COMPLETED + suffixContent, emailSubject);
        } else {
            sendPointCreatedEmail(point, member, prefixContent + MESSAGE_PENDING + suffixContent, emailSubject);
        }
    }

    private void sendPointCreatedEmail(Point point, Member member, String contextMessage, String emailSubject) {
        Context context = new Context();

        context.setVariable("name", member.getMemberName());
        context.setVariable("message", contextMessage);
        context.setVariable("status", point.getStatus());
        context.setVariable("currentPoint", point.getCurrentPoint());
        context.setVariable("addPoint", point.getAddPoint());
        context.setVariable("totalPoint", point.getCurrentPoint() + point.getAddPoint());
        context.setVariable("host", appProperties.getHost() + ROOT + "/" + point.getId() + "/detail");
        String message = templateEngine.process("mail/point-result", context);

        EmailMessage emailMessage = EmailMessage.builder()
                .subject(emailSubject)
                .to(member.getEmail())
                .message(message)
                .build();

        emailService.sendEmail(emailMessage);
    }

}
