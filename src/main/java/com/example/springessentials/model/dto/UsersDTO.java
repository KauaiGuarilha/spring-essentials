package com.example.springessentials.model.dto;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsersDTO {

    @NotEmpty private String username;

    @NotEmpty private String password;

    @NotEmpty private String name;

    @NotEmpty private boolean admin;
}
