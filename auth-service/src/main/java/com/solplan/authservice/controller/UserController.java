package com.solplan.authservice.controller;

import com.solplan.authservice.domain.User;
import com.solplan.authservice.dto.UserJoinRequest;
import com.solplan.authservice.dto.UserLoginRequest;
import com.solplan.authservice.dto.UserLoginResponse;
import com.solplan.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // 회원가입 API
    @PostMapping("/signup")
    public ResponseEntity<String> register(@RequestBody UserJoinRequest dto) {
        userService.signup(dto);
        return ResponseEntity.ok("회원가입 성공!");
    }

    // 로그인 API
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest dto) {
        try {
            User user = userService.login(dto);
            return ResponseEntity.ok().body(Map.of(
                    "message", "로그인 성공",
                    "userId", user.getUserId(),
                    "role", user.getRole().name()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", e.getMessage()
            ));
        }
    }

}