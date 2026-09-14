package com.msgDissapear.controller;

import com.msgDissapear.dto.AuthRequest;
import com.msgDissapear.dto.AuthResponse;
import com.msgDissapear.dto.RegisterRequest;
import com.msgDissapear.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse res = userService.register(request);
        return res.isSuccess()
                ? ResponseEntity.ok(res)
                : ResponseEntity.badRequest().body(res);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        AuthResponse res = userService.login(request);
        return res.isSuccess()
                ? ResponseEntity.ok(res)
                : ResponseEntity.status(401).body(res);
    }

    @GetMapping
    public ResponseEntity<List<String>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsernames());
    }
}
