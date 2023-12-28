package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.constants.AppConstants;
import com.bikkadit.electronicstore.constants.MessageConstants;
import com.bikkadit.electronicstore.dto.OrderDto;
import com.bikkadit.electronicstore.helper.PegeableResponse;
import com.bikkadit.electronicstore.payload.ApiResponse;
import com.bikkadit.electronicstore.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apiOr")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/order/{userId}/{cartId}")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto, @PathVariable String userId, @PathVariable String cartId) {
        OrderDto order = this.orderService.createOrder(orderDto, userId, cartId);
        return new ResponseEntity<OrderDto>(order, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse> removeOrder(@PathVariable String orderId) {
        this.orderService.removeOrder(orderId);
        ApiResponse response = ApiResponse.builder().status(HttpStatus.OK).Success(true).message(MessageConstants.ORDER_DELETE).build();
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    @GetMapping("/order/{userId}")
    public ResponseEntity<List<OrderDto>> getOrderofUser(@PathVariable String userId) {
        List<OrderDto> dtoList = this.orderService.getOrderofUser(userId);
        return new ResponseEntity<List<OrderDto>>(dtoList, HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<PegeableResponse<OrderDto>> getAllOrders
            (@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
             @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
             @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
             @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
        PegeableResponse<OrderDto> allOrders = this.orderService.getAllOrders(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<PegeableResponse<OrderDto>>(allOrders, HttpStatus.OK);
    }
}
