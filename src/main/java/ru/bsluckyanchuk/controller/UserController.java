package ru.bsluckyanchuk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bsluckyanchuk.entity.Sessions;
import ru.bsluckyanchuk.entity.Users;
import ru.bsluckyanchuk.service.SessionService;
import ru.bsluckyanchuk.service.UserService;

import javax.websocket.Session;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @GetMapping("/me")
    public ResponseEntity<Users> getUserInfo(@RequestBody Map<String,String> tokenData) {

        String token = tokenData.get("token");
        Sessions session = sessionService.getSessionByToken(token);

        if (session != null) {
            Users user = session.getUser();
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}