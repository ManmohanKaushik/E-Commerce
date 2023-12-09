package com.bikkadit.electronicstore.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {

    private String cartId;
    private Date createdAt;
    private UserDto userDto;
    private List<CartItemDto> items=new ArrayList<>();
}

