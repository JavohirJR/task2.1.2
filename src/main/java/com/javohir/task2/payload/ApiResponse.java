package com.javohir.task2.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiResponse {

    private String message;
    private boolean status;
    private Object object;

    public ApiResponse(String message, boolean status) {
        this.message = message;
        this.status = status;
    }

    public ApiResponse(String message, boolean status, Object object) {
        this.message = message;
        this.status = status;
        this.object = object;
    }
}
