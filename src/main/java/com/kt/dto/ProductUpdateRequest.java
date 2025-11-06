package com.kt.dto;

import com.kt.domain.product.ProductStatus;

import jakarta.validation.constraints.NotBlank;

public record ProductUpdateRequest(
	@NotBlank
	String name,
	@NotBlank
	Long price,
	@NotBlank
	Long stock,
	@NotBlank
	ProductStatus status) {
}
