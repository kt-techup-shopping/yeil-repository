package com.kt.dto.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public interface OrderRequest {
	record Create(
		@NotNull
		Long userId,
		@NotNull
		Long productId,
		@NotNull
		@Min(1)
		Long quantity,
		@NotBlank
		String name,
		@NotBlank
		String address,
		@NotBlank
		String mobile
	){
	}

}
