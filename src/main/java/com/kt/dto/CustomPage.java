package com.kt.dto;

import java.util.List;

import com.kt.domain.User;

// Paging 구조
// 백엔드 입장에서 필요한 것
// 한 화면에 몇 개 보여줄 것인가 -> limit
// 몇 번째 페이지를 보고있나 -> offset
// 보고 있는 페이지 - 1 * limit

// 프론트엔드에서 페이징을 구현할 때 필요한 정보
// 데이터
// 한 화면에 몇 개 보여줄 것인가
// 몇 번재 페이지를 보고있나
// 총 몇 개의 페이지가 생기는지
// 총 몇 개의 데이터가 있는지
// 10개씩 보여주는데 데이터는 21개 -> 3페이지
// 총 몇 개의 데이터가 있는지 (필수) -> count
public record CustomPage(
	List<User> users,
	int size,
	int page,
	int pages,
	long totalElements) {
}
