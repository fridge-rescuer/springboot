package com.fridgerescuer.springboot.secu.service;

import java.util.Collections;
import java.util.Optional;
import com.fridgerescuer.springboot.secu.dto.UserDto;
import com.fridgerescuer.springboot.secu.entity.Authority;
import com.fridgerescuer.springboot.secu.entity.User;
import com.fridgerescuer.springboot.secu.exception.DuplicateMemberException;
import com.fridgerescuer.springboot.secu.exception.NotFoundMemberException;
import com.fridgerescuer.springboot.secu.repository.UserRepository;
import com.fridgerescuer.springboot.secu.util.SecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //주입받음
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //회원가입 로직
    @Transactional
    public UserDto signup(UserDto userDto) {
        if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        //이미 존재 x인 경우에맘 권한 정보를 넣어서 만듬, 일반 권한
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        //from으로 미리 권한을 주입해서 만듬
        return UserDto.from(userRepository.save(user));
    }


    //아래는 권한 정보를 가져오는 메서드드

   @Transactional(readOnly = true)  //db에 저장된 정보에 접근
    public UserDto getUserWithAuthorities(String username) {
        return UserDto.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
    }

    @Transactional(readOnly = true) //오직 securityContext내에 저장된 데이터에서 username으로 찾음
    public UserDto getMyUserWithAuthorities() {
        return UserDto.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(userRepository::findOneWithAuthoritiesByUsername)
                        .orElseThrow(() -> new NotFoundMemberException("Member not found"))
        );
    }
}