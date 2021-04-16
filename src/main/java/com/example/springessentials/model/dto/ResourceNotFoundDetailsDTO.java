package com.example.springessentials.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceNotFoundDetailsDTO {

    private String title;
    private int status;
    private String detail;
    private long timestamp;
    private String developerMessage;
}
