package com.bikkadit.electronicstore.serviceimpl;

import com.bikkadit.electronicstore.dto.CartDto;
import com.bikkadit.electronicstore.payload.AddItemToCartRequest;
import com.bikkadit.electronicstore.repository.CartItemRepository;
import com.bikkadit.electronicstore.repository.CartRepository;
import com.bikkadit.electronicstore.repository.ProductRepository;
import com.bikkadit.electronicstore.repository.UserRepo;
import com.bikkadit.electronicstore.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {
        return null;
    }

    @Override
    public void removeItemFromCart(String userId, Integer cartItem) {

    }

    @Override
    public void clearCart(String userId) {

    }

    @Override
    public CartDto getCartByUser(String userId) {
        return null;
    }
}
