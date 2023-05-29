package ru.bsluckyanchuk.service;

import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bsluckyanchuk.dto.UserDTO;
import ru.bsluckyanchuk.entity.Users;
import ru.bsluckyanchuk.repository.UserRepository;
import ru.bsluckyanchuk.utils.Compute;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Users registerUser(UserDTO dto) {
        // Проверка уникальности имени пользователя и адреса электронной почты
        if (!dto.getEmail().contains("@")) {
            throw new IllegalArgumentException("Incorrect format of email");
        }

        if (userRepository.findByUsername(dto.getUsername()) != null) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.findByEmail(dto.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists");
        }
        // Хеширование пароля
        String Hash = Compute.calculateHash(dto.getPassword());
        dto.setPassword(Hash);


        Users newUser = Users.builder().username(dto.getUsername()).email(dto.getEmail())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .passwordHash(dto.getPassword())
                .role(dto.getRole())
                .build();

        // Сохранение пользователя в базу данных
        return userRepository.save(newUser);
    }

    public Users getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Users getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
