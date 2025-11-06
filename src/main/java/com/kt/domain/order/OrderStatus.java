package com.kt.domain.order;

public enum OrderStatus {
	PENDING("결제대기"),
	COMPLETED("결제완료"),
	SHIPPED("배송중"),
	DELIVERED("배송완료"),
	CANCELLED("주문취소"),
	CONFIRMED("구매확정");

	private final String description;

	OrderStatus(String description) {
		this.description = description;
	}
}
