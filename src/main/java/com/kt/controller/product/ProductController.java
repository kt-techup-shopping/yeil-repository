package com.kt.controller.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kt.common.ApiResult;
import com.kt.common.SwaggerAssistance;
import com.kt.domain.product.Product;
import com.kt.dto.product.ProductRequest;
import com.kt.service.product.ProductService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@Tag(name = "상품", description = "상품 관련 API")
public class ProductController extends SwaggerAssistance {

	private final ProductService productService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResult<Void> create(@RequestBody @Valid ProductRequest.Create request) {
		productService.create(
			request.getName(),
			request.getPrice(),
			request.getQuantity()
		);
		return ApiResult.ok();
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<Void> update(
		@PathVariable Long id,
		@RequestBody @Valid ProductRequest.Update request
	) {
		productService.update(
			id,
			request.getName(),
			request.getPrice(),
			request.getQuantity()
		);
		return ApiResult.ok();
	}

	@PatchMapping("/{id}/sold-out")
	public ApiResult<Void> soldOut(@PathVariable Long id) {
		productService.soldOut(id);
		return ApiResult.ok();
	}

	@PatchMapping("/{id}/activate")
	public ApiResult<Void> activate(@PathVariable Long id) {
		productService.activate(id);
		return ApiResult.ok();
	}

	@PatchMapping("/{id}/in-activate")
	public ApiResult<Void> inActivate(@PathVariable Long id) {
		productService.inActivate(id);
		return ApiResult.ok();
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<Void> remove(@PathVariable Long id) {
		productService.delete(id);
		return ApiResult.ok();
	}
}
