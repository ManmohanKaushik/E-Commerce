package com.bikkadit.electronicstore.serviceimpl;

import com.bikkadit.electronicstore.constants.MessageConstants;
import com.bikkadit.electronicstore.dto.CartDto;
import com.bikkadit.electronicstore.entity.Cart;
import com.bikkadit.electronicstore.entity.CartItem;
import com.bikkadit.electronicstore.entity.User;
import com.bikkadit.electronicstore.exception.ResourceNotFoundException;
import com.bikkadit.electronicstore.payload.AddItemToCartRequest;
import com.bikkadit.electronicstore.repository.CartItemRepository;
import com.bikkadit.electronicstore.repository.CartRepository;
import com.bikkadit.electronicstore.repository.ProductRepository;
import com.bikkadit.electronicstore.repository.UserRepo;
import com.bikkadit.electronicstore.services.CartService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger log = LoggerFactory.getLogger(CartServiceImpl.class);

    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {
        return null;
    }

    @Override
    public void removeItemFromCart(String userId, Integer cartItem) {
log.info("Request is sending in DAO layer for remove Item from Cart with userId:{} ",userId,"and cartIem:{}",cartItem);
        CartItem cartItem1 = cartItemRepository.findById(cartItem).orElseThrow(() -> new ResourceNotFoundException(MessageConstants.CART_ITEM));
        cartItemRepository.delete(cartItem1);
log.info("Response has received from DAO layer for remove Item from Cart with userId:{} ",userId,"and cartIem:{}",cartItem);
    }

    @Override
    public void clearCart(String userId) {
      log.info("Request is sending in DAO layer for clearCart userId:{} ",userId);
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(MessageConstants.RESOURCENOTFOUND));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException(MessageConstants.USER_NOTFOUND));
        cart.getItems().clear();
        cartRepository.save(cart);
        log.info("Response has received from DAO layer for clearCart userId:{} ",userId);
    }

    @Override
    public CartDto getCartByUser(String userId) {
       log.info("Request is sending in DAO layer for get Cart By User with userId:{} ",userId);
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(MessageConstants.RESOURCENOTFOUND));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException(MessageConstants.USER_NOTFOUND));
        log.info("Response has received from  DAO layer for get Cart By User with userId:{} ",userId);
        return mapper.map(cart,CartDto.class);
    }
}
