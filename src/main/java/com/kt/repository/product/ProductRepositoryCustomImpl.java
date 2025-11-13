package com.kt.repository.product;

import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.kt.domain.product.QProduct;
import com.kt.dto.product.ProductResponse;
import com.kt.dto.product.QProductResponse_Search;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom{

	private final JPAQueryFactory jpaQueryFactory;
	private final QProduct product = QProduct.product;

	@Override
	public Page<ProductResponse.Search> search(
		String keyword,
		PageRequest pageable
	) {
		var booleanBuilder = new BooleanBuilder();
		booleanBuilder.and(containsProductName(keyword));

		var content = jpaQueryFactory
			.select(new QProductResponse_Search(
				product.id,
				product.name,
				product.price,
				product.stock,
				product.status
			))
			.from(product)
			.where(booleanBuilder)
			.orderBy(product.id.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		var total = (long) jpaQueryFactory
			.select(product.id)
			.from(product)
			.where(booleanBuilder)
			.fetch().size();

		return new PageImpl<>(content, pageable, total);
	}

	private BooleanExpression containsProductName(String keyword) {
		return Strings.isNotBlank(keyword) ? product.name.containsIgnoreCase(keyword) : null;
	}
}
