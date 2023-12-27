package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.constants.MessageConstants;
import com.bikkadit.electronicstore.dto.OrderDto;
import com.bikkadit.electronicstore.helper.PegeableResponse;
import com.bikkadit.electronicstore.payload.ApiResponse;
import com.bikkadit.electronicstore.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apiOr")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/order/{userId}/{cartId}")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto,@PathVariable String userId, @PathVariable String cartId) {
        OrderDto order = this.orderService.createOrder(orderDto, userId, cartId);
        return new ResponseEntity<OrderDto>(order, HttpStatus.CREATED);
    }

    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse> removeOrder(@PathVariable String orderId) {
        this.orderService.removeOrder(orderId);
        ApiResponse response = ApiResponse.builder().status(HttpStatus.OK).Success(true).message(MessageConstants.ORDER_DELETE).build();
        return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
    }

    @GetMapping("/order/{userId}")
    public ResponseEntity<List<OrderDto>> getOrderofUser(@PathVariable String userId) {
        List<OrderDto> dtoList = this.orderService.getOrderofUser(userId);
        return new ResponseEntity<List<OrderDto>>(dtoList,HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<PegeableResponse<OrderDto>> getAllOrders() {

        return null;
    }
}
