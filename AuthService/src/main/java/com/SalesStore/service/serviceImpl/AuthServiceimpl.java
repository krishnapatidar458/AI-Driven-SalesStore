package com.SalesStore.service.serviceImpl;

import com.SalesStore.dto.SignupRequest;
import com.SalesStore.entity.Role;
import com.SalesStore.entity.User;
import com.SalesStore.exception.UserAlreadyExistsException;
import com.SalesStore.repository.UserRepository;
import com.SalesStore.exception.InvalidCredentialsException;
import com.SalesStore.request.LoginRequest;
import com.SalesStore.response.AuthResponse;
import com.SalesStore.security.JwtUtil;
import com.SalesStore.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceimpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public void signup(SignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new UserAlreadyExistsException("Email already registered");
        }

        User user = User.builder()
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return new AuthResponse(token);
    }

}
