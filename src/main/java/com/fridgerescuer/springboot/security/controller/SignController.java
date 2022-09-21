package com.fridgerescuer.springboot.security.controller;

import com.fridgerescuer.springboot.data.dto.LoginForm;
import com.fridgerescuer.springboot.data.dto.MemberDTO;
import com.fridgerescuer.springboot.security.dto.TokenDTO;
import com.fridgerescuer.springboot.security.jwt.JwtFilter;
import com.fridgerescuer.springboot.security.jwt.TokenProvider;
import com.fridgerescuer.springboot.security.service.SignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/security")
public class SignController {
    @Autowired
    private final SignService signService;
    @Autowired
    private final TokenProvider tokenProvider;


    @PostMapping("/test-redirect")
    public void testRedirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/security/user");
    }

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<MemberDTO> signup(
            @Valid @RequestBody MemberDTO memberDTO
    ) {
        return ResponseEntity.ok(signService.signup(memberDTO));
    }

    //회원가입
    @PostMapping("/signin")
    public ResponseEntity<TokenDTO> signIn(
            @Valid @RequestBody LoginForm loginForm
    ) {
        TokenDTO tokenDto = signService.singIn(loginForm);  //잘못된 아이디라면 런타입 예외 발생

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + tokenDto.getToken());

        return new ResponseEntity<>(tokenDto, httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/memberid")
    @ResponseBody
    public String getMyMemberId(HttpServletRequest request) {
        String token = getTokenFromHttpServletRequest(request);

        return signService.getMemberIdByToken(token);
    }

    private String getTokenFromHttpServletRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");

        return bearerToken.substring(7);
    }
/*
    //'USER','ADMIN' 두가지 권한 모두 호출 가능한 api
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<MemberDTO> getMyUserInfo(HttpServletRequest request) {
        return ResponseEntity.ok(signService.getMyUserWithAuthorities());
    }

    //'ADMIN' 권한만 가능한 호출 가능한 api
    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<MemberDTO> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(signService.getUserWithAuthorities(username));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {

        //받아온 post파라미터로 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getId(), loginDto.getPassword());

        //loadUserByUserName이 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        //생성된 authentication를 SecurityContext에 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //jwt 토큰 생성
        String jwt = tokenProvider.createToken(authentication);

        //헤더와 바디에 jwt토큰 넣어줌
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }
    */
}
