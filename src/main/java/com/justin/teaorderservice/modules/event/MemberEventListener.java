package com.justin.teaorderservice.modules.event;

import com.justin.teaorderservice.infra.config.AppProperties;
import com.justin.teaorderservice.infra.mail.EmailMessage;
import com.justin.teaorderservice.infra.mail.EmailService;
import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.modules.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Slf4j
@Async
@Component
@Transactional
@RequiredArgsConstructor
public class MemberEventListener {

    static final String ROOT = "/api/order/members";

    private final MemberRepository memberRepository;
    private final AppProperties appProperties;
    private final TemplateEngine templateEngine;
    private final EmailService emailService;

    @EventListener
    public void handleOrderCreatedEvent(MemberCreatedEvent memberCreatedEvent){
        Member member = memberRepository.findById(memberCreatedEvent.getMember().getId()).orElse(null);
        String emailSubject = "[TeaOrderService] 계정 생성";
        String contextMessage = "가입을 축하합니다.";
        sendOrderCreatedEmail(member, contextMessage, emailSubject);
    }
    private void sendOrderCreatedEmail(Member member, String contextMessage, String emailSubject) {
        Context context = new Context();

        context.setVariable("name", member.getMemberName());
        context.setVariable("message", contextMessage);
        context.setVariable("host", appProperties.getHost() + ROOT + "/" + member.getEmail() + "/detail");
        String message = templateEngine.process("mail/member-result", context);

        EmailMessage emailMessage = EmailMessage.builder()
                .subject(emailSubject)
                .to(member.getEmail())
                .message(message)
                .build();

        emailService.sendEmail(emailMessage);
    }

}
