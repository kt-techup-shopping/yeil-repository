package com.kt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kt.dto.ProductCreateRequest;
import com.kt.dto.ProductUpdateRequest;
import com.kt.dto.UserCreateRequest;
import com.kt.service.ProductService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@Tag(name = "상품", description = "상품 관련 API")
@ApiResponses(value = {
	@ApiResponse(responseCode = "400", description = "유효성 검사 실패"),
	@ApiResponse(responseCode = "500", description = "서버 에러 - 백엔드 문의 바랍니다.")
})
public class ProductController {

	private final ProductService productService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody @Valid ProductCreateRequest request) {
		productService.create(request);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void update(@PathVariable Long id, @RequestBody @Valid ProductUpdateRequest request) {
		productService.update(id, request.name(), request.price(), request.stock(), request.status());
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable Long id) {
		productService.delete(id);
	}
}
