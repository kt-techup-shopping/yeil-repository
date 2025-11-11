package com.kt.repository.orderProduct;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kt.domain.orderProduct.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
