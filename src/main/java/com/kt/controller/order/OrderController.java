package com.kt.controller.order;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kt.common.ApiResult;
import com.kt.dto.order.OrderRequest;
import com.kt.security.CurrentUser;
import com.kt.service.order.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

	private final OrderService orderService;

	// 주문 생성
	@PostMapping
	public ApiResult<Void> create(
		@AuthenticationPrincipal CurrentUser currentUser,
		@RequestBody @Valid OrderRequest.Create request) {
		orderService.create(
			currentUser.getId(), request.productId(), request.name(),
			request.address(), request.mobile(), request.quantity()
		);
		return ApiResult.ok();
	}
}
