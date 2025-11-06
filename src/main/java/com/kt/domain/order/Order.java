package com.kt.domain.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.kt.common.BaseEntity;
import com.kt.domain.orderProduct.OrderProduct;
import com.kt.domain.product.Product;
import com.kt.domain.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
	private String receiverName;
	private String receiverAddress;
	private String receiverMobile;
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	private LocalDateTime deliveredAt;

	// 연관 관계
	// 주문 - 회원
	// N:1 -> 다대일
	// Many To One
	// FK -> 많은 쪽에 생김
	// 하나의 주문은 여러 명의 회원이 할 수 없다.

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	// @OneToMany
	// private List<Product> products = new ArrayList<>();

	// 주문 생성
	// 주문 상태 변경
	// 주문 생성 완료 재고 차감
	// 배송 받는 사람 정보 수정
	// 주문 취소
}
