package com.fridgerescuer.springboot.security.controller;

import com.fridgerescuer.springboot.data.dto.LoginForm;
import com.fridgerescuer.springboot.data.dto.MemberDTO;
import com.fridgerescuer.springboot.security.dto.TokenDto;
import com.fridgerescuer.springboot.security.jwt.JwtFilter;
import com.fridgerescuer.springboot.security.jwt.TokenProvider;
import com.fridgerescuer.springboot.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/security")
public class UserController {
    @Autowired
    private final UserService userService;
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
        return ResponseEntity.ok(userService.signup(memberDTO));
    }

    //회원가입
    @PostMapping("/signin")
    public ResponseEntity<TokenDto> signIn(
            @Valid @RequestBody LoginForm loginForm
    ) {
        TokenDto tokenDto = userService.singIn(loginForm);  //잘못된 아이디라면 런타입 예외 발생

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + tokenDto.getToken());

        return new ResponseEntity<>(tokenDto, httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/member")
    public void getMyMemberInfo(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        String token = bearerToken.substring(7);

        tokenProvider.getClaimsFromToken(token);
    }

    //'USER','ADMIN' 두가지 권한 모두 호출 가능한 api
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<MemberDTO> getMyUserInfo(HttpServletRequest request) {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities());
    }

    //'ADMIN' 권한만 가능한 호출 가능한 api
    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<MemberDTO> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserWithAuthorities(username));
    }
}
