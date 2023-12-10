package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.dto.CartDto;
import com.bikkadit.electronicstore.payload.AddItemToCartRequest;
import com.bikkadit.electronicstore.payload.ApiResponse;
import com.bikkadit.electronicstore.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apiCarts")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/cart/{userId}")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId, @RequestBody AddItemToCartRequest request) {

        return null;
    }

    @DeleteMapping("/cart/{userId}/item/{itemId}")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable String userId,@PathVariable Integer itemId) {

        return null;
    }

    @DeleteMapping("/cart/{userId}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable String userId){

        return null;
    }

    @GetMapping("/cart/{userId}")
    public ResponseEntity<CartDto> getCartByUser(@PathVariable String userId){

        return null;
    }
}
