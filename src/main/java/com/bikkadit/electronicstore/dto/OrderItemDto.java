package com.bikkadit.electronicstore.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderItemDto {
    private Integer orderItemId;

    private Integer quantity;

    private Integer totalPrice;

    private ProductDto productDto;

    private OrderDto orderDto;
}
