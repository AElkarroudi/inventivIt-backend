package com.inventivIt.dreamCase.util.apiResponse.fail;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FailDTO {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private FailType type;
    private String message;
    private Map<String, Object> errors;

    public FailDTO(FailType type, String message) {
        this.message = message;
    }

    public FailDTO(FailType type, Map<String, Object> errors) {
        this.errors = errors;
    }

}
