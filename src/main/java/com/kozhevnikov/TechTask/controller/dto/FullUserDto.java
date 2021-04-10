package com.kozhevnikov.TechTask.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    private Long id;
    @NotNull
//    @JsonProperty("username")
    private String username;
    @NotNull
//    @JsonProperty("password")
    private String password;
    @NotNull
//    @JsonProperty("firstName")
    private String firstName;
    @NotNull
//    @JsonProperty("lastName")
    private String lastName;
    private String status;
    private String role;
}
