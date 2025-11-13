package com.kt.dto.product;

import com.kt.domain.product.ProductStatus;
import com.querydsl.core.annotations.QueryProjection;

import io.swagger.v3.oas.annotations.media.Schema;

public interface ProductResponse {

	@Schema(name = "ProductResponse.Search")
	record Search(
		Long id,
		String name,
		Long price,
		Long stock,
		ProductStatus status
	) {
		@QueryProjection
		public Search {
		}
	}

	@Schema(name = "ProductResponse.Search")
	record Detail(
		Long id,
		String name,
		Long price,
		Long stock,
		ProductStatus status
	) {
	}
}