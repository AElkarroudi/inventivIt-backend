package com.inventivIt.dreamCase.util.apiResponse.success;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessDTO<T> {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private T payload;

    public SuccessDTO(T payload) {
        this.payload = payload;
    }

}
