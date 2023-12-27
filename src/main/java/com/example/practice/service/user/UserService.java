package com.example.practice.service.user;

import com.example.practice.dto.user.UserDto;
import com.example.practice.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public String saveUser(UserDto userDto){
        String id = userDto.getId();

        if(!userRepository.findById(id).isEmpty()){
            throw new RuntimeException("이미 사용중인 아이디 입니다.");
        }
        userRepository.save(userDto.toEntity());
        return userDto.getId();
    }
}
