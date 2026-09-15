package com.example.demo.controller;

import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@CrossOrigin(origins = {
        "http://localhost:63342",
        "http://127.0.0.1:63342",
        "http://localhost:3000"
})
public class MemberController {

    private final MemberRepository memberRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Member member) {
        try {
            Member savedMember = memberRepository.save(member);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of(
                            "id", savedMember.getId(),
                            "userId", savedMember.getUserId(),
                            "name", savedMember.getName()
                    ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("회원가입 중 오류가 발생했습니다.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Member loginRequest) {
        return memberRepository.findByUserId(loginRequest.getUserId())
                .filter(member ->
                        member.getPassword().equals(loginRequest.getPassword()))
                .<ResponseEntity<?>>map(member -> ResponseEntity.ok(Map.of(
                        "id", member.getId(),
                        "userId", member.getUserId(),
                        "name", member.getName()
                )))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("아이디 또는 비밀번호가 틀렸습니다."));
    }
}