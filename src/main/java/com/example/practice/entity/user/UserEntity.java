package com.example.practice.entity.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "USER")
@NoArgsConstructor
public class UserEntity {
    @Id
    private String id;
    private String password;
    private String name;
    private String email;
    private String tell;
    private String gender;

    @Builder
    public UserEntity(
            String id,
            String password,
            String name,
            String email,
            String tell,
            String gender) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.tell = tell;
        this.gender = gender;
    }
}