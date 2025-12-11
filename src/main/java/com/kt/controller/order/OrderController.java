package com.kt.controller.order;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kt.common.response.ApiResult;
import com.kt.common.support.SwaggerAssistance;
import com.kt.common.support.TechUpLogger;
import com.kt.domain.history.HistoryType;
import com.kt.dto.order.OrderRequest;
import com.kt.security.CurrentUser;
import com.kt.service.order.OrderService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@Tag(name = "주문", description = "주문 관련 API")
public class OrderController extends SwaggerAssistance {

	private final OrderService orderService;

	// 주문 생성
	@TechUpLogger(type = HistoryType.ORDER_CREATE, content = "사용자 주문 생성")
	@PostMapping
	public ApiResult<Void> create(
		@AuthenticationPrincipal CurrentUser currentUser,
		@RequestBody @Valid OrderRequest.Create request) {
		orderService.create(
			// request.userId()
			currentUser.getId(), request.productId(), request.name(),
			request.address(), request.mobile(), request.quantity()
		);
		return ApiResult.ok();
	}
}
