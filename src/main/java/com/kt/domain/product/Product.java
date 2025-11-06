package com.kt.domain.product;

import com.kt.common.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Entity
@Getter
public class Product extends BaseEntity {
	private String name;
	private Long price;
	private Long stock;
	@Enumerated(EnumType.STRING)
	private ProductStatus status;

	// 생성
	// 수정
	// 삭제
	// 조회 (리스트, 단건)
	// 상태 변경
	// 재고 수량 감소
	// 재고 수량 증가

}
