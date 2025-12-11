package com.kt.domain.product;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import com.kt.common.exception.CustomException;

class ProductTest {
	// POJO -> 객체 생성이 잘 되는지 확인
	// 테스트 제목을 작성하는 방법 2가지
	// 1. displayName 어노테이션 활용
	// 2. 메서드명 자체를 한글로 작성 (공백은 _로 대체)

	// private final String PRODUCT_NAME = "테스트 상품";
	// private final Long PRODUCT_PRICE = 100_000L;
	// private final Long PRODUCT_STOCK = 10L;
	// // 경계값 분석 태스트
	// private final Long PRODUCT_NEGATIVE_STOCK = -1L;
	// private final Long PRODUCT_NEGATIVE_PRICE = -1L;
	// private final Long PRODUCT_NULL_PRICE = null;
	// private final Long PRODUCT_NULL_STOCK = null;

	@Test
	@DisplayName("객체 생성 잘 되는지")
	void 객체_생성_성공(){
		var product = new Product(
			"테스트 상품",
			100_000L,
			10L
		);

		// 객체가 잘 생성되었는가
		// product 이름 필드의 값이 "테스트 상품" 인지 확인
		// jupiter.core -> assertThat (jupiter.core.Assertions.*)
		// jupiter.api -> assertEquals
		assertThat(product.getName()).isEqualTo("테스트 상품");
		assertThat(product.getPrice()).isEqualTo(100_000L);
		assertThat(product.getStock()).isEqualTo(10L);
	}

	// 할 거 적고 뒤에 이유
	@Test
	void 상품_생성_실패__상품명_공백(){
		// 상품명이 공백이면 throw Exception
		// 람다식이 단일 실행문이면 중괄호 생략 가능
		assertThrowsExactly(CustomException.class, () ->
			new Product(
				" ",
				100_000L,
				10L
			));
	}

	@Test
	void 상품_생성_실패__상품명_null(){
		assertThrowsExactly(CustomException.class, () ->
			new Product(
				null,
				100_000L,
				10L
			));
	}

	// 위 2가지를 한 번에 작성
	@ParameterizedTest
	@NullAndEmptySource
	void 상품_생성_실패__상품명_null_또는_공백(String name){
		assertThrowsExactly(CustomException.class, () ->
			new Product(
				name,
				100_000L,
				10L
			));
	}

	@Test
	 void 상품_생성_실패__가격이_음수() {
		assertThrowsExactly(CustomException.class, () ->
			new Product(
				"테스트 상품",
				-1L,
				10L
			));
	}

	@Test
	void 상품_생성_실패__가격이_null() {
		assertThrowsExactly(CustomException.class, () ->
			new Product(
				"테스트 상품",
				null,
				10L
			));
	}

	@Test
	void 상품_생성_실패__재고가_음수() {
		assertThrowsExactly(CustomException.class, () ->
			new Product(
				"테스트 상품",
				100_000L,
				-1L
			));
	}

	@Test
	void 상품_생성_성공__재고가_null() {
		assertThrowsExactly(CustomException.class, () ->
			new Product(
				"테스트 상품",
				100_000L,
				null
			));
	}

}