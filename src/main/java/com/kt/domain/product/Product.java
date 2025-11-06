package com.kt.domain.product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.kt.common.BaseEntity;
import com.kt.domain.orderProduct.OrderProduct;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Product extends BaseEntity {
	private String name;
	private Long price;
	private Long stock;
	@Enumerated(EnumType.STRING)
	private ProductStatus status;

	@OneToMany(mappedBy = "product")
	private List<OrderProduct> orderProducts = new ArrayList<>();

	// 생성
	public Product(String name, Long price, Long stock, ProductStatus status) {
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.status = status;
		this.createdAt = LocalDateTime.now();;
		this.updatedAt = LocalDateTime.now();;
	}
	// 수정
	public void update(String name, Long price, Long stock, ProductStatus status) {
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.status = status;
		this.updatedAt = LocalDateTime.now();
	}

	// 삭제
	// 조회 (리스트, 단건)
	// 상태 변경
	// 재고 수량 감소
	// 재고 수량 증가

}
