package com.kt.repository.order;

import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kt.common.exception.CustomException;
import com.kt.common.exception.ErrorCode;
import com.kt.domain.order.Order;

public interface OrderRepository extends OrderRepositoryCustom, JpaRepository<Order, Long> {
	// 1. 네이티브 쿼리 작성
	// 2. JPQL 작성
	// 3. 쿼리 메서드 작성
	// 4. 조회할 때는 동적 쿼리를 작성하게 해줄 수 있는 QueryDSL 사용

	default Order findByIdOrThrow(Long id){
		return findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ORDER));
	}

	@Query("""
		select distinct o
		from Order o
		join fetch o.orderProducts op
		join fetch op.product p
		where o.id = :id
		""")
	Optional<Order> findOrderDetail(Long id);

	// String[] attributePaths() default {}
	@NotNull
	@EntityGraph(attributePaths = {"orderProducts", "orderProducts.product"})
	// @EntityGraph(attributePaths = "orderProducts")
	List<Order> findAllByUserId(Long userId);
}
