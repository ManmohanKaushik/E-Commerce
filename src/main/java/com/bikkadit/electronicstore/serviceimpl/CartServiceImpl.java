package com.bikkadit.electronicstore.serviceimpl;

import com.bikkadit.electronicstore.constants.MessageConstants;
import com.bikkadit.electronicstore.dto.CartDto;
import com.bikkadit.electronicstore.entity.Cart;
import com.bikkadit.electronicstore.entity.CartItem;
import com.bikkadit.electronicstore.entity.Product;
import com.bikkadit.electronicstore.entity.User;
import com.bikkadit.electronicstore.exception.BadRequestException;
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

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

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
        log.info("Request is sending in DAO layer for add Item to Cart userId:{} ", userId);
        Integer quantity = request.getQuantity();
        String productId = request.getProductId();
        if (quantity<=0){
            throw new BadRequestException(MessageConstants.QUANTITY_NOTVALID);
        }
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(MessageConstants.PRODUCTID_NOTFOUND));
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(MessageConstants.RESOURCENOTFOUND));
        Cart cart = null;
        try {
            cart = cartRepository.findByUser(user).get();
        } catch (NoSuchElementException e) {
            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCreatedAt(new Date());
        }
        AtomicReference<Boolean> updated = new AtomicReference<>(false);

        List<CartItem> items = cart.getItems();

        List<CartItem> updatedItems = items.stream().map(item -> {
            if (item.getProduct().getProductId().equals(productId)) {
                item.setQuantity(quantity);
                item.setTotalPrice(quantity * product.getDiscountPrice());
                updated.set(true);

            }
            return item;
        }).collect(Collectors.toList());
        if (!updated.get()) {
            CartItem cartItem = CartItem.builder()
                    .quantity(quantity)
                    .totalPrice(quantity * product.getDiscountPrice())
                    .cart(cart)
                    .product(product)
                    .build();
            cart.getItems().add(cartItem);
        }
        cart.setUser(user);
        Cart updatedCart = cartRepository.save(cart);
        log.info("Response has received from DAO layer for add Item to Cart userId:{} ", userId);
        return mapper.map(updatedCart, CartDto.class);
    }

    @Override
    public void removeItemFromCart(String userId, Integer cartItem) {
        log.info("Request is sending in DAO layer for remove Item from Cart with userId:{} ", userId, "and cartIem:{}", cartItem);
        CartItem cartItem1 = cartItemRepository.findById(cartItem).orElseThrow(() -> new ResourceNotFoundException(MessageConstants.CART_ITEM));
        cartItemRepository.delete(cartItem1);
        log.info("Response has received from DAO layer for remove Item from Cart with userId:{} ", userId, "and cartIem:{}", cartItem);
    }

    @Override
    public void clearCart(String userId) {
        log.info("Request is sending in DAO layer for clearCart userId:{} ", userId);
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(MessageConstants.RESOURCENOTFOUND));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException(MessageConstants.USER_NOTFOUND));
        cart.getItems().clear();
        cartRepository.save(cart);
        log.info("Response has received from DAO layer for clearCart userId:{} ", userId);
    }

    @Override
    public CartDto getCartByUser(String userId) {
        log.info("Request is sending in DAO layer for get Cart By User with userId:{} ", userId);
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(MessageConstants.RESOURCENOTFOUND));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException(MessageConstants.USER_NOTFOUND));
        log.info("Response has received from  DAO layer for get Cart By User with userId:{} ", userId);
        return mapper.map(cart, CartDto.class);
    }
}
