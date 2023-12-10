package com.bikkadit.electronicstore.services;

import com.bikkadit.electronicstore.dto.CartDto;
import com.bikkadit.electronicstore.payload.AddItemToCartRequest;

public interface CartService {
    public CartDto addItemToCart(String userId, AddItemToCartRequest request);

    public void removeItemFromCart(String userId, Integer cartItem);

    public void clearCart(String userId);

    public CartDto getCartByUser(String userId);


}
