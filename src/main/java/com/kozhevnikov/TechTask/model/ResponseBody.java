package com.kozhevnikov.TechTask.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;


/**
 * Entity for exception handling
 */
@Builder
@Getter
@ToString
public class ResponseBody {

    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private String error;
    private String path;
}