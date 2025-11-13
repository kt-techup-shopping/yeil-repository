package com.kt.controller.order;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kt.common.ApiResult;
import com.kt.common.Paging;
import com.kt.common.SwaggerAssistance;
import com.kt.dto.order.OrderResponse;
import com.kt.repository.order.OrderRepository;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/orders")
@Tag(name = "주문 관리자", description = "주문 관리자 관련 API")
public class AdminOrderController extends SwaggerAssistance {
	// 해당 서비스에서 하는게 없음
	// 1. 레포지토리 주입 바로 받아서 사용할 것인지 -> 싱크홀 안티패턴
	// 2. 그래도 서비스를 통해야 한다

	private final OrderRepository orderRepository;

	@GetMapping
	public ApiResult<Page<OrderResponse.Search>> search(
		@RequestParam(required = false) String keyword,
		@ParameterObject Paging paging
	){
		return ApiResult.ok(orderRepository.search(keyword, paging.toPageable()));
	}
}
