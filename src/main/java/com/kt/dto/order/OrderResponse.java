package com.kt.dto.order;

import java.time.LocalDateTime;

import com.kt.domain.order.OrderStatus;
import com.querydsl.core.annotations.QueryProjection;

public interface OrderResponse {
	// 3가지 방법으로 QueryDsl 결과를 dto 매핑
	// 1. 클래스 프로젝션 (Search 클래스가 Q클래스로 만들어지면 new로)
	// 2. 아노테이션 프로젝션 (@QueryProjection)
	// 3. POJO 직접 배핑

	record Search(
		Long id,
		String receiverName,
		String productName,
		Long quantity,
		Long totalPrice,
		OrderStatus status,
		LocalDateTime createdAt
	){
		@QueryProjection
		public Search{
		}
	}

	record Detail (
		Long id,
		String receiverName,
		String receiverAddress,
		String receiverMobile,
		String productName,
		Long quantity,
		Long totalPrice,
		OrderStatus status,
		LocalDateTime deliveredAt,
		LocalDateTime createdAt
	){
	}
}
