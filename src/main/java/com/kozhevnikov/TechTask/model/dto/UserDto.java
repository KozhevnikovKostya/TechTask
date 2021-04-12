package com.kozhevnikov.TechTask.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String username;
    private String status;
    private String role;
}
