package com.example.springessentials.model.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsersDTOResponse {

    private UUID id;
    private String username;
    private String password;
    private String name;
    private boolean admin;
}
