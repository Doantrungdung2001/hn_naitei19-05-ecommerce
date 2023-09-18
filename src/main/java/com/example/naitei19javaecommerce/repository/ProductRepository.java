package com.example.naitei19javaecommerce.repository;

import com.example.naitei19javaecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product ,Long> {
}
