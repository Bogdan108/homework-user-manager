package ru.bsluckyanchuk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bsluckyanchuk.dto.UserDTO;
import ru.bsluckyanchuk.entity.Users;
import ru.bsluckyanchuk.security.JwtProvider;
import ru.bsluckyanchuk.service.SessionService;
import ru.bsluckyanchuk.service.UserService;
import ru.bsluckyanchuk.token.UserToken;
import ru.bsluckyanchuk.utils.Compute;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    final private JwtProvider jwtProvider = new JwtProvider();

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO user) {
        try {
            userService.registerUser(user);
            return ResponseEntity.ok("Registration successful");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> loginData) {

        String email = loginData.get("email");
        String password = loginData.get("password");
        if (isLoginValid(email, password)) {

            // Генерация JWT токена (реализация опущена)
            // String sessionToken = Compute.generateToken(userService.getUserByEmail(email));
            LocalDateTime expiresAt = LocalDateTime.now().plusHours(1);
            String sessionToken = jwtProvider.createToken(email);
            // Создание сессии
            Users user = userService.getUserByEmail(email);
            sessionService.createSession(user, sessionToken, expiresAt);
            return ResponseEntity.ok(sessionToken);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login credentials");
        }
    }

    boolean isLoginValid(String email, String password) {
        Users user = userService.getUserByEmail(email);
        if (user == null) {
            return false;
        }
        return user.getPasswordHash().equals(Compute.calculateHash(password));
    }
}
