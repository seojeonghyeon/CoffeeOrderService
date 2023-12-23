package com.justin.teaorderservice.modules.member;

import com.justin.teaorderservice.modules.vo.RequestMemberSave;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

@RequiredArgsConstructor
public class WithAccountSecurityContextFactory implements WithSecurityContextFactory<WithAccount> {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SecurityContext createSecurityContext(WithAccount withAccount) {
        String email = withAccount.email();
        String password = withAccount.password();
        String simplePassword = withAccount.simplePassword();

        RequestMemberSave requestMemberSave = new RequestMemberSave();
        requestMemberSave.setEmail(email);
        requestMemberSave.setPassword(password);
        requestMemberSave.setSimplePassword(simplePassword);
        memberService.register(requestMemberSave.getEmail(), passwordEncoder.encode(requestMemberSave.getPassword()), passwordEncoder.encode(requestMemberSave.getSimplePassword()));

        UserDetails principal = memberService.loadUserByUsername(email);
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, principal.getPassword());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        return context;
    }
}
