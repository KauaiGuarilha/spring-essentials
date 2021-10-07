package com.example.springessentialssenders.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {

    private String id;

    @NotEmpty(message = "O campo name é obrigatório")
    private String name;

    @Email
    @NotEmpty(message = "O campo email é obrigatório")
    private String email;
}
