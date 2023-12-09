package com.bikkadit.electronicstore.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {

    private Integer cartItemId;
    private ProductDto productDto;
    private Integer quantity;
    private Integer totalPrice;
    private CartDto cartDto;
}
