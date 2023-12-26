package com.example.practice.service;

import com.example.practice.dto.UserDto;
import com.example.practice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public String saveUser(UserDto userDto){
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(userDto.toEntity());
        return userDto.getId();
    }
}
