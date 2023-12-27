package com.example.practice.controller.user;

import com.example.practice.dto.user.UserDto;
import com.example.practice.service.user.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<String> saveUser(@Valid @RequestBody UserDto userDto){
        log.info("userDto ? {}", userDto);
        String userId = userService.saveUser(userDto);
        return new ResponseEntity<>(userId, HttpStatus.OK);
    }
}
