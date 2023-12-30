package com.bikkadit.electronicstore.services.impl;

import com.bikkadit.electronicstore.constants.MessageConstants;
import com.bikkadit.electronicstore.dto.OrderDto;
import com.bikkadit.electronicstore.entity.*;
import com.bikkadit.electronicstore.exception.BadRequestException;
import com.bikkadit.electronicstore.exception.ResourceNotFoundException;
import com.bikkadit.electronicstore.helper.Helper;
import com.bikkadit.electronicstore.helper.PegeableResponse;
import com.bikkadit.electronicstore.repository.CartRepository;
import com.bikkadit.electronicstore.repository.OrderRepository;
import com.bikkadit.electronicstore.repository.UserRepo;
import com.bikkadit.electronicstore.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public OrderDto createOrder(OrderDto orderDto, String userId, String cartId) {
        log.info("Initiating DAO call for create Order.");
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(MessageConstants.USER_NOTFOUND));

        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException(MessageConstants.CART_ID));
        List<CartItem> cartItems = cart.getItems();
        if (cartItems.size() <= 0) {
            throw new BadRequestException(MessageConstants.CART_ITEM);
        }
        Order order = Order.builder()
                .billingName(orderDto.getBillingName())
                .billingPhone(orderDto.getBillingPhone())
                .billingAddress(orderDto.getBillingAddress())
                .orderedDate(new Date())
                .deliveredDate(orderDto.getDeliveredDate())
                .paymentStatus(orderDto.getPaymentStatus())
                .orderStatus(orderDto.getOrderStatus())
                .orderId(UUID.randomUUID().toString())
                .user(user)
                .build();
        AtomicReference<Integer> orderAmount = new AtomicReference<>(0);
        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
            OrderItem orderItem = OrderItem.builder()
                    .quantity(cartItem.getQuantity())
                    .product(cartItem.getProduct())
                    .totalPrice(cartItem.getQuantity() * cartItem.getProduct().getDiscountPrice())
                    .order(order)
                    .build();
            orderAmount.set(orderAmount.get() * orderItem.getTotalPrice());
            return orderItem;
        }).collect(Collectors.toList());
        order.setOrderItems(orderItems);
        order.setOrderAmount(orderAmount.get());
        cart.getItems().clear();
        cartRepository.save(cart);
        Order save = orderRepository.save(order);
        log.info("Completed  DAO call for create  Order.");
        return modelMapper.map(save, OrderDto.class);
    }

    @Override
    public void removeOrder(String orderId) {
        log.info("Initiating DAO call for delete  order with order:{} ", orderId);
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException(MessageConstants.ORDER_ID));
        orderRepository.delete(order);
        log.info("Completed  DAO call for delete order with orderId:{} ", orderId);
    }

    @Override
    public List<OrderDto> getOrderofUser(String userId) {
        log.info("Initiating DAO call for get Order userId:{} ", userId);
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(MessageConstants.USER_NOTFOUND));
        List<Order> orders = orderRepository.findByUser(user);
        List<OrderDto> orderDtos = orders.stream().map(order -> modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
        log.info("Completed  DAO call for get  Order of userId:{} ", userId);
        return orderDtos;
    }

    @Override
    public PegeableResponse<OrderDto> getAllOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
        log.info("Initiating DAO call for get all Order ");
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Order> page = orderRepository.findAll(pageable);
        log.info("Completed  DAO call for get all order ");
        return Helper.pegeableResponse(page, OrderDto.class);
    }
}
