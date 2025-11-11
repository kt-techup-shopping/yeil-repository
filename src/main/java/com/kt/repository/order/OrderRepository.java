package com.kt.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kt.domain.order.Order;

public interface OrderRepository extends OrderRepositoryCustom, JpaRepository<Order, Long> {
	// 1. 네이티브 쿼리 작성
	// 2. JPQL 작성
	// 3. 쿼리 메서드 작성
	// 4. 조회할 때는 동적 쿼리를 작성하게 해줄 수 있는 QueryDSL 사용
}
