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

import static com.bikkadit.electronicstore.constants.UriConstants.*;

@RestController
@RequestMapping(CART_URI)
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
    @PostMapping(ADD_CART)
    public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId, @RequestBody AddItemToCartRequest request) {
        log.info("Initiated request for add Item To Cart  with userId :{} " ,userId);
        CartDto cartDto = cartService.addItemToCart(userId, request);
        log.info("Completed request for add Item To Cart  with userId :{} " ,userId);
        return new ResponseEntity<>(cartDto,HttpStatus.OK);
    }
    /**
     * @author Manmohan Sharma
     * @apiNote To removeItemFromCart data in database
     * @param userId,itemId
     * @since 1.0v
     */

    @DeleteMapping(REMOVE_CART_ITEM)
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable String userId, @PathVariable Integer itemId) {
        log.info("Initiated request for remove Item form Cart  with userId :{} and itemId:{} " ,userId,itemId);
        cartService.removeItemFromCart(userId, itemId);
        ApiResponse response = ApiResponse.builder()
                .message(MessageConstants.RESOURCEDELETE)
                .Success(true)
                .status(HttpStatus.OK)
                .build();
        log.info("Completed request for remove Item form Cart  with userId :{} and itemId:{} " ,userId,itemId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    /**
     * @author Manmohan Sharma
     * @apiNote To clearCart data in database
     * @param userId
     * @since 1.0v
     */
    @DeleteMapping(CLEAR_CART)
    public ResponseEntity<ApiResponse> clearCart(@PathVariable String userId) {
        log.info("Initiated request for clear Cart with userId :{} " ,userId);
        cartService.clearCart(userId);
        ApiResponse response = ApiResponse.builder()
                .message(MessageConstants.CART_BLANK)
                .Success(true)
                .status(HttpStatus.OK)
                .build();
        log.info("Completed request for clear Cart  with userId :{} " ,userId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    /**
     * @author Manmohan Sharma
     * @apiNote To getCartByUser data in database
     * @param userId
     * @return CartDto
     * @since 1.0v
     */
    @GetMapping(GET_CART_BY_USER)
    public ResponseEntity<CartDto> getCartByUser(@PathVariable String userId) {
        log.info("Initiated request for get Cart with userId :{} " ,userId);
        CartDto cartDto = cartService.getCartByUser(userId);
        log.info("Completed request for get Cart with userId :{} " ,userId);
        return new ResponseEntity<>(cartDto,HttpStatus.OK);
    }
}
