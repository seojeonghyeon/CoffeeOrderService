package com.justin.teaorderservice.infra.auditor;

import com.justin.teaorderservice.infra.session.SessionConst;
import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.modules.member.MemberAdapter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;
import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
        if(null != authentication && authentication.isAuthenticated()){
            return getAuthentication(authentication);
        }

        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        HttpSession session = request.getSession();
        if(null != session.getAttribute(SessionConst.LOGIN_MEMBER)){
            return getSession(session);
        }

        return null;
    }

    private Optional<String> getAuthentication(Authentication authentication){
        MemberAdapter memberAdapter = (MemberAdapter) authentication.getPrincipal();
        Member member = memberAdapter.getMember();
        return Optional.of(member.getId());
    }

    private Optional<String> getSession(HttpSession session){
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        return Optional.of(member.getId());
    }
}
