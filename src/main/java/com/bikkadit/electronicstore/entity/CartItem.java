package com.bikkadit.electronicstore.entity;

import javax.persistence.*;

@Entity
@Table(name = "Cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartItemId;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;

    private Integer totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

}
