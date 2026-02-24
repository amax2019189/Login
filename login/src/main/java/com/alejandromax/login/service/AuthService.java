package com.alejandromax.login.service;

import com.alejandromax.login.dto.LoginRequest;
import com.alejandromax.login.dto.LoginResponse;
import com.alejandromax.login.dto.RegisterRequest;
import com.alejandromax.login.entity.User;
import com.alejandromax.login.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register (RegisterRequest req) {
        if (userRepository.existsByEmail(req.email)) {
            throw new IllegalArgumentException("El email ya está registrado.");
        }

        String hash = passwordEncoder.encode(req.password);
        User user = new User(req.name, req.email, hash);
        userRepository.save(user);
    }

    public LoginResponse login (LoginRequest req) {
        User user = userRepository.findByEmail(req.email)
                .orElseThrow(() -> new IllegalArgumentException("Correo Incorrecto / no existe."));

        boolean ok = passwordEncoder.matches(req.password, user.getPasswordHash());
        if (!ok) throw new IllegalArgumentException("Contraseña incorrecta");

        return new LoginResponse("Login correcto: ", user.getId(), user.getName(), user.getEmail());
    }
}