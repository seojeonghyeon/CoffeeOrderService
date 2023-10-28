package com.justin.teaorderservice.modules.member;

import com.justin.teaorderservice.infra.MockMvcTest;
import com.justin.teaorderservice.modules.authority.Authority;
import com.justin.teaorderservice.modules.authority.AuthorityRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@MockMvcTest
class MemberTest {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @DisplayName("USER 권한의 Member 생성")
    @Test
    void createUserMember() throws Exception{
        Member member = createMember("seojeonghyeon0630@gmail.com", passwordEncoder.encode("SEOjh1234!"), passwordEncoder.encode("1234"));
        Member saveMember = memberRepository.save(member);
        Authority authority = authorityRepository.findByAuthorityName("USER").orElse(null);

        Set<Authority> authoritySet = saveMember.getAuthorities();

        assertThat(true).isEqualTo(authoritySet.contains(authority));
    }

    private Member createMember(String email, String encryptedPwd, String simpleEncryptedPwd){
        return Member.createUserMember(email, encryptedPwd, simpleEncryptedPwd);
    }

}