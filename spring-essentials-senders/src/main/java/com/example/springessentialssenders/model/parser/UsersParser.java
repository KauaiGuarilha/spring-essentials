package com.example.springessentialssenders.model.parser;

import com.example.springessentialssenders.model.dto.UserDTO;
import com.example.springessentialssenders.model.dto.UserDTOResponse;
import com.example.springessentialssenders.model.entity.Users;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
public class UsersParser {

    public Users toUsers(UserDTO dto){
        return Users.builder()
                .id(Objects.isNull(dto.getId()) ? UUID.randomUUID() : UUID.fromString(dto.getId()))
                .name(dto.getName())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .admin(dto.getAdmin())
                .build();
    }

    public UserDTOResponse toResponse(Users users){
        return UserDTOResponse.builder()
                .id(users.getId().toString())
                .name(users.getName())
                .username(users.getUsername())
                .admin(users.getAdmin())
                .build();
    }


}
