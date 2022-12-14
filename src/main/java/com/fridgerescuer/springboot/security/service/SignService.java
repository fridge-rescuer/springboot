package com.fridgerescuer.springboot.security.service;

import java.util.Collections;

import com.fridgerescuer.springboot.data.dao.MemberDao;
import com.fridgerescuer.springboot.data.dto.LoginForm;
import com.fridgerescuer.springboot.data.dto.MemberDTO;
import com.fridgerescuer.springboot.data.entity.Member;
import com.fridgerescuer.springboot.data.mapper.MemberMapper;
import com.fridgerescuer.springboot.data.repository.MemberRepository;
import com.fridgerescuer.springboot.exception.errorcodeimpl.MemberError;
import com.fridgerescuer.springboot.exception.exceptionimpl.MemberException;
import com.fridgerescuer.springboot.security.dto.TokenDTO;
import com.fridgerescuer.springboot.security.entity.Authority;
import com.fridgerescuer.springboot.security.jwt.TokenProvider;
import com.fridgerescuer.springboot.security.util.SecurityUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignService {
    @Autowired
    private final MemberDao memberDao;
    @Autowired
    private final MemberRepository memberRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final TokenProvider tokenProvider;
    @Autowired
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final MemberMapper memberMapper = MemberMapper.INSTANCE;

    //회원가입 로직
    @Transactional
    public MemberDTO signup(MemberDTO memberDTO) {
        if (memberRepository.findOneWithAuthoritiesById(memberDTO.getId()).orElse(null) != null) {
            throw new MemberException(MemberError.DUPLICATE_ID);    //이미 존재하는 아이디
        }

        //이미 존재 x인 경우에맘 권한 정보를 넣어서 만듬, 일반 권한
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        Member member = Member.builder()
                .id(memberDTO.getId())
                .password(passwordEncoder.encode(memberDTO.getPassword()))
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        //from으로 미리 권한을 주입해서 만듬
        return memberMapper.memberToDto(memberRepository.save(member));
    }

    @Transactional
    public TokenDTO singIn(LoginForm loginForm){
        MemberDTO memberDTO = memberDao.findById(loginForm.getId());

        //패스워드 비교
        if(!passwordEncoder.matches(loginForm.getPassword(), memberDTO.getPassword())){
            throw new MemberException(MemberError.INCORRECT_PASSWORD);
        }
        log.info("SingIn Success! id ={}", loginForm.getId());

        TokenDTO tokenDto = TokenDTO.builder().token(getJwtToken(loginForm)).build();

        return tokenDto;
    }


    private String getJwtToken(LoginForm loginForm){
        //받아온 post파라미터로 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginForm.getId(), loginForm.getPassword());

        //loadUserByUserName이 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        //생성된 authentication를 SecurityContext에 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //jwt 토큰 생성
        String jwt = tokenProvider.createToken(authentication);
        return jwt;
    }


    public String getMemberIdByToken(String jwtToken){
        Claims claimsFromToken = tokenProvider.getClaimsFromToken(jwtToken);

        return claimsFromToken.getSubject();
    }


    //아래는 권한 정보를 가져오는 메서드드
   @Transactional(readOnly = true)  //db에 저장된 정보에 접근
    public MemberDTO getMemberDtoWithAuthorities(String id) {
        return memberMapper.memberToDto(memberRepository.findOneWithAuthoritiesById(id).orElse(null));
    }

    @Transactional(readOnly = true) //오직 securityContext내에 저장된 데이터에서 username으로 찾음
    public MemberDTO getMyMemberWithAuthorities() {

        return memberMapper.memberToDto(
                SecurityUtil.getCurrentUsername()
                        .flatMap(memberRepository::findOneWithAuthoritiesById)
                        .orElseThrow(() -> new MemberException(MemberError.NOT_EXIST))  //존재 하지 않는 멤버
        );
    }
}