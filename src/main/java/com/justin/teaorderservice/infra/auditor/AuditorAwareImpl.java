package com.justin.teaorderservice.infra.auditor;

import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.modules.member.MemberAdapter;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
        if(null != authentication && authentication.isAuthenticated()){
            return getAuthentication(authentication);
        }

        return null;
    }

    private Optional<String> getAuthentication(Authentication authentication){
        MemberAdapter memberAdapter = (MemberAdapter) authentication.getPrincipal();
        Member member = memberAdapter.getMember();
        return Optional.of(member.getId());
    }
}
