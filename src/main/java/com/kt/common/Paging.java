package com.kt.common;

import org.springframework.data.domain.PageRequest;

import io.swagger.v3.oas.annotations.media.Schema;

public record Paging(
	@Schema(defaultValue = "1")
	int page,
	@Schema(defaultValue = "10")
	int size
	//todo: 정렬기능도 추가 예정
) {
	public PageRequest toPageable() {
		return PageRequest.of(page - 1, size);
	}
}