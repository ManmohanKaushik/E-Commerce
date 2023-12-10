package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.constants.MessageConstants;
import com.bikkadit.electronicstore.dto.CartDto;
import com.bikkadit.electronicstore.payload.AddItemToCartRequest;
import com.bikkadit.electronicstore.payload.ApiResponse;
import com.bikkadit.electronicstore.services.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apiCarts")
@Slf4j
public class CartController {
    @Autowired
    private CartService cartService;

    /**
     * @author Manmohan Sharma
     * @apiNote To addItemToCart data in database
     * @param userId
     * @since 1.0v
     * @return CartDto
     */
    @PostMapping("/cart/{userId}")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId, @RequestBody AddItemToCartRequest request) {
        log.info("Request is sending in service layer for add Item To Cart  with userId :{} " ,userId);


        log.info("Response has received from service layer for add Item To Cart  with userId :{} " ,userId);
        return null;
    }
    /**
     * @author Manmohan Sharma
     * @apiNote To removeItemFromCart data in database
     * @param userId,itemId
     * @since 1.0v
     */

    @DeleteMapping("/cart/{userId}/item/{itemId}")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable String userId, @PathVariable Integer itemId) {
        log.info("Request is sending in service layer for remove Item form Cart  with userId :{} and itemId:{} " ,userId,itemId);
        cartService.removeItemFromCart(userId, itemId);
        ApiResponse response = ApiResponse.builder()
                .message(MessageConstants.RESOURCEDELETE)
                .Success(true)
                .status(HttpStatus.OK)
                .build();
        log.info("Response has received from service layer for remove Item form Cart  with userId :{} and itemId:{} " ,userId,itemId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    /**
     * @author Manmohan Sharma
     * @apiNote To clearCart data in database
     * @param userId
     * @since 1.0v
     */
    @DeleteMapping("/cart/{userId}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable String userId) {
        log.info("Request is sending in service layer for clear Cart userId :{} " ,userId);
        cartService.clearCart(userId);
        ApiResponse response = ApiResponse.builder()
                .message(MessageConstants.CART_BLANK)
                .Success(true)
                .status(HttpStatus.OK)
                .build();
        log.info("Response has received from service layer for clear Cart userId :{} " ,userId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    /**
     * @author Manmohan Sharma
     * @apiNote To getCartByUser data in database
     * @param userId
     * @return CartDto
     * @since 1.0v
     */
    @GetMapping("/cart/{userId}")
    public ResponseEntity<CartDto> getCartByUser(@PathVariable String userId) {
        log.info("Request is sending in service layer for get Cart by userId :{} " ,userId);
        CartDto cartDto = cartService.getCartByUser(userId);
        log.info("Response has received from service layer for get Cart by userId :{} " ,userId);
        return new ResponseEntity<>(cartDto,HttpStatus.OK);
    }
}
