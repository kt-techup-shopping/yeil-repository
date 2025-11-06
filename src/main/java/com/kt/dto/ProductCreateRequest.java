package com.kt.dto;

import jakarta.validation.constraints.NotBlank;

public record ProductCreateRequest(
	@NotBlank
	String name,
	@NotBlank
	Long price,
	@NotBlank
	Long stock) {

}
