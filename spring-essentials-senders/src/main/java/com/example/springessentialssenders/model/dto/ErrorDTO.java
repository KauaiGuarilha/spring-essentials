package com.example.springessentialssenders.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class ErrorDTO {

    private Integer code;
    private String message;
    private String field;
}