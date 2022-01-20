package com.example.springessentialssenders.model.dto;

import com.example.springessentialssenders.model.domain.ERole;
import com.example.springessentialssenders.validators.ValueEnumInteger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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

    @ValueEnumInteger(classeEnum = ERole.class)
    @NotNull(message = "The admin option is required")
    private Integer admin;
}
