package com.kt.domain.orderProduct;

import com.kt.common.BaseEntity;
import com.kt.domain.order.Order;
import com.kt.domain.product.Product;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Getter
@Entity
public class OrderProduct extends BaseEntity {
	private Long quantity;
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
}
