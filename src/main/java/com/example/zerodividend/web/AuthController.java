package com.example.zerodividend.web;

import com.example.zerodividend.model.Member;
import com.example.zerodividend.securiry.TokenProvider;
import com.example.zerodividend.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Member.SignUp request) {
        Member member = this.memberService.register(request);
        return ResponseEntity.ok(member);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody Member.SignIn request) {
        Member member = this.memberService.authenticate(request);
        String token = this.tokenProvider.generateToken(member.getUsername(), member.getRole());
        log.info("user login -> " + request.getUsername());
        return ResponseEntity.ok(token);
    }
}
