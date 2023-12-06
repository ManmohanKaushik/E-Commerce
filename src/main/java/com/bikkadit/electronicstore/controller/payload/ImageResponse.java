package com.bikkadit.electronicstore.controller.payload;

import lombok.*;
import org.springframework.http.HttpStatus;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageResponse {
    private String imageName;
    private String message;
    private boolean Success;
    private HttpStatus status;
}
