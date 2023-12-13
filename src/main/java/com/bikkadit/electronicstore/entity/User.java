package com.bikkadit.electronicstore.entity;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "User_Details")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    private String userId;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_password")
    private String password;

    @Column(name = "user_gender")
    private String gender;

    @Column(name = "about_user")
    private String about;

    @Column(name = "user_image_Name")
    private String imageName;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<CartItem> items =new ArrayList<>();

//   @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
//   private List<Order> orders=new ArrayList<>();
}
