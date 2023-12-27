package com.bikkadit.electronicstore.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class JwtRequest {

    private String email;
     private String password;
}
