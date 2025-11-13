package com.kt.controller.product;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kt.common.ApiResult;
import com.kt.common.Paging;
import com.kt.dto.product.ProductResponse;
import com.kt.repository.product.ProductRepository;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/orders")
@Tag(name = "주문 관리자", description = "주문 관리자 관련 API")
public class AdminProductController {

	private final ProductRepository productRepository;

	@GetMapping
	public ApiResult<Page<ProductResponse.Search>> search(
		@RequestParam(required = false) String keyword,
		@Parameter(hidden = true) Paging paging
	){
		return ApiResult.ok(productRepository.search(keyword, paging.toPageable()));
	}
}
