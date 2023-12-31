package com.bikkadit.electronicstore.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Product {
    @Id
    private String productId;

    @Column(name = "product_title")
    private String title;

    @Column(name = "product_description")
    private String description;

    @Column(name = "product_price")
    private Integer price;

    @Column(name="discount_price")
    private Integer discountPrice;

    @Column(name = "product_quantity")
    private Integer quantity;

    @Column(name = "added_date")
    private Date addedDate;

    @Column(name = "live")
    private boolean live;

    private boolean stock;

    private  String productImageName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

}
