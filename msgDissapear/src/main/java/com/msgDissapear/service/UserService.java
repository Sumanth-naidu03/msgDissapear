package com.msgDissapear.service;

import com.msgDissapear.dto.AuthRequest;
import com.msgDissapear.dto.AuthResponse;
import com.msgDissapear.dto.RegisterRequest;
import com.msgDissapear.entity.User;
import com.msgDissapear.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        String username = request.getUsername().trim();
        String email = request.getEmail().trim().toLowerCase();
        String displayName = request.getDisplayName().trim();
        String bio = request.getBio() == null ? "" : request.getBio().trim();

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return new AuthResponse(null, "Passwords do not match", false);
        }
        if (userRepository.existsByUsername(username)) {
            return new AuthResponse(null, "Username already taken", false);
        }
        if (userRepository.existsByEmail(email)) {
            return new AuthResponse(null, "Email is already registered", false);
        }

        User user = new User();
        user.setUsername(username);
        user.setDisplayName(displayName);
        user.setEmail(email);
        user.setBio(bio.isBlank() ? null : bio);
        // Plain text for simplicity — no Spring Security dependency
        user.setPassword(request.getPassword());
        userRepository.save(user);
        return new AuthResponse(user.getUsername(), "Registered successfully", true);
    }

    @Transactional(readOnly = true)
    public AuthResponse login(AuthRequest request) {
        return userRepository.findByUsername(request.getUsername())
                .map(user -> {
                    if (user.getPassword().equals(request.getPassword())) {
                        return new AuthResponse(user.getUsername(), "Login successful", true);
                    }
                    return new AuthResponse(null, "Incorrect password", false);
                })
                .orElse(new AuthResponse(null, "User not found", false));
    }

    @Transactional(readOnly = true)
    public List<String> getAllUsernames() {
        return userRepository.findAllByOrderByUsernameAsc()
                .stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
    }
}
