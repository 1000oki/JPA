package com.example.practice.dto.user;

import com.example.practice.entity.user.UserEntity;
import jakarta.validation.constraints.NotEmpty;
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
