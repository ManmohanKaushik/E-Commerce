package com.bikkadit.electronicstore.dto;


import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDto {

    private String orderId;

    private String orderStatus="PENDING";

    private String paymentStatus="NOT-PAID";

    private Integer orderAmount;

    private String billingAddress;

    private String billingPhone;

    private String billingName;

    private Date orderedDate=new Date();

    private Date deliveredDate;

    private UserDto userDto;

    private List<OrderItemDto> orderItems = new ArrayList<>();

}
