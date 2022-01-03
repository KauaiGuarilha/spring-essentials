package com.example.springessentialssenders.model.dto;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String id;

    @NotEmpty(message = "The username field is required")
    private String username;

    @NotEmpty(message = "The password field is required")
    private String password;

    @NotEmpty(message = "The name field is required")
    private String name;

    @NotEmpty(message = "The admin option is required")
    private boolean admin;
}
