package com.kt.domain.orderProduct;

import com.kt.common.BaseEntity;
import com.kt.domain.order.Order;
import com.kt.domain.product.Product;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;

@Getter
@Entity
public class OrderProduct extends BaseEntity {
	private Long quantity;
	@OneToOne
	private Order order;
	@OneToOne
	private Product product;
}
