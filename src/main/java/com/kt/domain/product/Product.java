package com.kt.domain.product;

import java.util.ArrayList;
import java.util.List;

import com.kt.common.BaseEntity;
import com.kt.domain.order.Order;
import com.kt.domain.orderProduct.OrderProduct;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.Getter;

@Entity
@Getter
public class Product extends BaseEntity {
	private String name;
	private Long price;
	private Long stock;
	@Enumerated(EnumType.STRING)
	private ProductStatus status;

	// @OneToMany
	// private List<Order> orders = new ArrayList<>();

	// 생성
	// 수정
	// 삭제
	// 조회 (리스트, 단건)
	// 상태 변경
	// 재고 수량 감소
	// 재고 수량 증가

}
