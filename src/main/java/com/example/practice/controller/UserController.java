package com.example.practice.controller;

import com.example.practice.dto.UserDto;
import com.example.practice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity<String> saveUser(@Valid @RequestBody UserDto userDto){
        String userId = userService.saveUser(userDto);
        return new ResponseEntity<>(userId, HttpStatus.OK);
    }
}
