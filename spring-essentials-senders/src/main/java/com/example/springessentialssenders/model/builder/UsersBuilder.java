package com.example.springessentialssenders.model.builder;

import com.example.springessentialssenders.model.dto.UserDTO;
import com.example.springessentialssenders.model.entity.Users;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
public class UsersBuilder {

    public Users toUsers(UserDTO dto){
        return Users.builder()
                .id(Objects.isNull(dto.getId()) ? null : UUID.fromString(dto.getId()))
                .name(dto.getName())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .admin(dto.isAdmin())
                .build();
    }

    public UserDTO toResponse(Users users){
        return UserDTO.builder()
                .id(users.getId().toString())
                .name(users.getName())
                .username(users.getUsername())
                .admin(users.isAdmin())
                .build();
    }
}
