package com.bikkadit.electronicstore.services;

import com.bikkadit.electronicstore.dto.OrderDto;
import com.bikkadit.electronicstore.helper.PegeableResponse;

import java.util.List;

public interface OrderService {

    OrderDto createOrder(OrderDto orderDto,String userId,String cartId);

    void removeOrder(String orderId);

    List<OrderDto> getOrderofUser(String userId);

    PegeableResponse<OrderDto> getAllOrders(int pageNumber, int pageSize, String sortBy, String sortDir);


}
