package com.kt.dto.product;

import com.kt.domain.product.ProductStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductUpdateRequest(
	@NotBlank
	String name,
	@NotNull
	Long price,
	@NotNull
	Long stock,
	@NotNull
	ProductStatus status) {
}
