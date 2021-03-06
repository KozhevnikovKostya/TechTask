package com.kozhevnikov.TechTask.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FullUserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(hidden = true)
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    @ApiModelProperty(hidden = true)
    private String status;
    @ApiModelProperty(hidden = true)
    private String role;
}
