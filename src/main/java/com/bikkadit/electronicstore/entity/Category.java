package com.bikkadit.electronicstore.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category
{
    @Id
    @Column(name="category_id")
    private String categoryId;

    @Column(name="category_title")
    private String title;

    @Column(name ="category_description")
    private String description;

    @Column(name = "cover_image")
    private String coverImage;
//
//    @OneToMany(mappedBy = "category",cascade= CascadeType.ALL,fetch = FetchType.LAZY)
//    private List<Product> products=new ArrayList<>();
}
