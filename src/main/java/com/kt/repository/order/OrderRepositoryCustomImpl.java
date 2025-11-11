package com.kt.repository.order;

import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.kt.domain.order.QOrder;
import com.kt.domain.orderProduct.QOrderProduct;
import com.kt.domain.product.QProduct;
import com.kt.dto.order.OrderResponse;
import com.kt.dto.order.QOrderResponse_Search;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	// QClass 임포트해서 쿼리를 작성할 때 사용
	private final QOrder order = QOrder.order;
	private final QProduct product = QProduct.product;
	private QOrderProduct orderProduct = QOrderProduct.orderProduct;


	@Override
	public Page<OrderResponse.Search> search(
		String keyword,
		Pageable pageable
	){
		// 페이징 구현
		// offset, limit

		var booleanBuilder = new BooleanBuilder();
		// booleanBuilder.and()
		// booleanBuilder.or()
		// booleanBuilder 안에다가 booleanExpression 추가
		booleanBuilder.and(containsProductName(keyword));

		var content = jpaQueryFactory
			// dto로 변환하기 위해 order 자리에 QOrderResponse_Search
			.select(new QOrderResponse_Search(
				order.id,
				order.receiver.name,
				product.name,
				orderProduct.quantity,
				product.price.multiply(orderProduct.quantity),
				order.status,
				order.createdAt
			))
			.from(order)
			.join(orderProduct).on(orderProduct.order.id.eq(order.id))
			.join(product).on(orderProduct.product.id.eq(product.id))
			.where(booleanBuilder)
			.orderBy(order.id.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		// 총 개수
		var total = (long) jpaQueryFactory
			.select(order.id)
			.from(order)
			.join(orderProduct).on(orderProduct.order.id.eq(order.id))
			.join(product).on(orderProduct.product.id.eq(product.id))
			.where(booleanBuilder)
			.fetch().size();

		return new PageImpl<>(content, pageable, total);
	}

	private BooleanExpression containsProductName(String keyword) {
		// if(keyword != null && !keyword.isBlank()) {
		// 	return product.name.containsIgnoreCase(keyword);
		// } else {
		// 	return null;
		// }

		// Reference 뒤에 s가 붙여지면 해당 타입을 도와주는 서포터 클래스
		return Strings.isNotBlank(keyword) ? product.name.containsIgnoreCase(keyword) : null;
	}

	// inner join => join 대상이 없으면 조회 X (softDelete)
	// left join => join 대상이 없어도 조회 (hardDelete)
	// fetchJoin => 연관된 엔티티를 함께 조회 (N + 1 문제 해결)

}
