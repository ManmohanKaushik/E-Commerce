package com.bikkadit.electronicstore.repository;

import com.bikkadit.electronicstore.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository  extends JpaRepository<Cart,String>
{
//    Optional<Cart> findByUser(User user);

//   Option<Cart> findByUser(User user);
}
