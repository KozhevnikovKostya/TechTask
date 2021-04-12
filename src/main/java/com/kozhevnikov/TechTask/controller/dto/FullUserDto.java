package com.kozhevnikov.TechTask.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FullUserDto {
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
