package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.constants.AppConstants;
import com.bikkadit.electronicstore.constants.MessageConstants;
import com.bikkadit.electronicstore.dto.OrderDto;
import com.bikkadit.electronicstore.helper.PegeableResponse;
import com.bikkadit.electronicstore.payload.ApiResponse;
import com.bikkadit.electronicstore.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apiOr")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * @param orderDto
     * @return orderDto
     * @author Manmohan Sharma
     * @apiNote To create data in database
     * @since 1.0v
     */

    @PostMapping("/order/{userId}/{cartId}")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto, @PathVariable String userId, @PathVariable String cartId) {
        log.info("Request is sending in service layer for create Order.");
        OrderDto order = this.orderService.createOrder(orderDto, userId, cartId);
        log.info("Response has received from service layer for create Order");
        return new ResponseEntity<OrderDto>(order, HttpStatus.CREATED);
    }

    /**
     * @param orderId
     * @author Manmohan Sharma
     * @apiNote To delete data in database
     * @since 1.0v
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse> removeOrder(@PathVariable String orderId) {
        log.info("Request is sending in service layer for delete orderId :{}", orderId);
        this.orderService.removeOrder(orderId);
        ApiResponse response = ApiResponse.builder().status(HttpStatus.OK).Success(true).message(MessageConstants.ORDER_DELETE).build();
        log.info("Response has received from service layer for delete orderId :{}", orderId);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    /**
     * @param userId
     * @author Manmohan Sharma
     * @apiNote To get Category by id data in database
     * @since 1.0v
     */
    @GetMapping("/order/{userId}")
    public ResponseEntity<List<OrderDto>> getOrderofUser(@PathVariable String userId) {
        log.info("Request is sending in service layer for get Order of userId :{}", userId);
        List<OrderDto> dtoList = this.orderService.getOrderofUser(userId);
        log.info("Response has received from service layer for get Order of userId :{}", userId);
        return new ResponseEntity<List<OrderDto>>(dtoList, HttpStatus.OK);
    }

    /**
     * @author Manmohan Sharma
     * @apiNote To get all data from database
     * @since 1.0v
     */
    @GetMapping("/orders")
    public ResponseEntity<PegeableResponse<OrderDto>> getAllOrders
    (@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
     @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
     @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
     @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
        log.info("Request is sending in service layer for get  all Order");
        PegeableResponse<OrderDto> allOrders = this.orderService.getAllOrders(pageNumber, pageSize, sortBy, sortDir);
        log.info("Response has received from service layer for get all Order");
        return new ResponseEntity<PegeableResponse<OrderDto>>(allOrders, HttpStatus.OK);
    }
}
