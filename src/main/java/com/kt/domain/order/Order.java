package com.kt.domain.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.kt.common.support.BaseEntity;
import com.kt.domain.orderProduct.OrderProduct;
import com.kt.domain.user.User;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor
public class Order extends BaseEntity {
	@Embedded
	private Receiver receiver;
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

	@OneToMany(mappedBy = "order")
	// @BatchSize(size = 2)
	private List<OrderProduct> orderProducts = new ArrayList<>();

	//하나의 오더는 여러개의 상품을 가질수있음
	// 1:N
	//하나의 상품은 여러개의 오더를 가질수있음

	// 주문 생성
	// 주문 상태 변경
	// 주문 생성 완료 재고 차감
	// 배송 받는 사람 정보 수정
	// 주문 취소

	private Order(Receiver receiver, User user) {
		this.receiver = receiver;
		this.user = user;
		this.deliveredAt = LocalDateTime.now().plusDays(3);
		this.status = OrderStatus.PENDING;
	}

	public static Order create(Receiver receiver, User user) {
		return new Order(receiver, user);
	}

	public void mapToOrderProduct(OrderProduct orderProduct) {
		this.orderProducts.add(orderProduct);
	}
}
