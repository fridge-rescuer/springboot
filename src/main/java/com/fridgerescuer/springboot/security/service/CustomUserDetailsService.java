package com.fridgerescuer.springboot.security.service;

import com.fridgerescuer.springboot.data.entity.Member;
import com.fridgerescuer.springboot.data.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private final MemberRepository memberRepository;


    @Override
    @Transactional  //로그인시 DB에서 유저 정보와 권한 정보를 갖옴 -> 활성화 상태시 유저 객체 리턴
    public UserDetails loadUserByUsername(final String userName) {
        return memberRepository.findOneWithAuthoritiesById(userName)
                .map(user -> createUser(userName, user))
                .orElseThrow(() -> new UsernameNotFoundException(userName + " -> 데이터베이스에서 찾을 수 없는 Member 입니다."));
    }

    private User createUser(String memberId, Member member) {
        if (!member.isActivated()) {
            throw new RuntimeException(memberId + " -> 활성화되어 있지 않습니다.");
        }

        List<GrantedAuthority> grantedAuthorities = member.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());

        return new User(member.getId(),
                member.getPassword(),
                grantedAuthorities);
    }
}
