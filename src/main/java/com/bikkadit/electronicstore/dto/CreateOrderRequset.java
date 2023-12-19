package com.bikkadit.electronicstore.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CreateOrderRequset {

    private String userId;

    private String cartId;

    private String orderStatus="PENDING";

    private String paymentStatus="NOT-PAID";

    private String billingAddress;

    private String billingPhone;

    private String billingName;
}
