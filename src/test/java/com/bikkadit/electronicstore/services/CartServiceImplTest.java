package com.bikkadit.electronicstore.services;

import com.bikkadit.electronicstore.dto.CartDto;
import com.bikkadit.electronicstore.entity.Cart;
import com.bikkadit.electronicstore.entity.User;
import com.bikkadit.electronicstore.repository.CartRepository;
import com.bikkadit.electronicstore.repository.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class CartServiceImplTest {

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CartService cartService;

    private Cart cart;

    private User user;

    @BeforeEach
    public void init() {
        cart= Cart.builder().cartId("man").build();


    }
    @Test
    public void getCartByUserTest() {


    }
}
