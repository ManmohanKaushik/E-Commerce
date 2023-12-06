package com.bikkadit.electronicstore.controller.payload;


import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse {
    private String message;
    private boolean Success;
    private HttpStatus status;

    public ApiResponse(String resourcedelete, boolean b) {
    }
}
