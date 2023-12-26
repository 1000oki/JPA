package com.example.practice.dto;

import com.example.practice.entity.UserEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDto {
    @NotEmpty
    private String id;
    @NotEmpty
    private String password;
    private String name;
    private String email;
    private String tell;
    private String gender;

    public UserEntity toEntity(){
        return UserEntity.builder()
                .id(id)
                .password(password)
                .name(name)
                .email(email)
                .tell(tell)
                .gender(gender)
                .build();
    }
}
