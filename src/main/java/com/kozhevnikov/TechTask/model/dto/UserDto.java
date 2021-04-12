package com.kozhevnikov.TechTask.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class UserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private String status;
    private String role;
}
