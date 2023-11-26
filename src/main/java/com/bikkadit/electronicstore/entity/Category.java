package com.bikkadit.electronicstore.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
}
