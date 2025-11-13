package com.kt.controller.product;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kt.common.ApiResult;
import com.kt.common.Paging;
import com.kt.dto.product.ProductResponse;
import com.kt.repository.product.ProductRepository;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/products")
@Tag(name = "주문 관리자", description = "주문 관리자 관련 API")
public class AdminProductController {
	/**
	 * 테스트 코드
	 * 개발자가 개발을 하면 개발자는 테스트를 해야할까? 말아야할까?
	 * 만약 한다면 어디까지 테스트를 해야할까?
	 * 팀바팀으로 협의가 필요한 부분이고, API 테스트(Controller, Swagger, Postman)은 한번은 해야한다.

	 * 테스트 방법
	 * 1. 테스트 코드 작성 - (1순위)
	 * 2. postman 요청
	 * 3. Swagger 요청 - (1.5순위)
	 * 4. curl 테스트 (리눅스, 터미널, 명령프롬프트 명령어)
	 * 5. 인텔리제이 ultimate 버전에서는 HTTP 클라이언트 지원

	 * 테스트 범위
	 * 1. 단위 테스트 - 가장 작은 단위의 기능을 테스트하는 것 (메서드, 클래스, 이키텍쳐의 레이어) - 의존성이 없을 때
	 * -> 스프링에서는 불가 SpringBootTest -> 목적에 맞지 않는 빈들을 모두 가져옴 (POJO 객체는 가능)
	 * 2. 통합 테스트 - 여러 단위를 다 모아서 테스트하는 것
	 * 3. 시스템 테스트 (생략?)
	 * 4. 인수  테스트 - 실제 운영 환경과 같은 환경에서 하는 시나리오 테스트 - QA

	 * 테스트 코드 5개 원칙 (FIRST)
	 * F: Fast - 빠르게 실행되어야 한다.
	 * I: Independent, Isolated - 각각의 테스트가 독립적으로 실행되어야 한다.
	 * R: Repeatable - 하나의 테스트를 몇 번을 실행할 수 있어야 하고, 동일한 결과가 나와야 한다.
	 * S: Self-validating - 테스트가 스스로 검증할 수 있어야 한다.
	 * T: Timely - 적절한 시점에 작성되어야 한다. (바로 테스트 코드 작성해야 함)
	 *
	 * 테스트 { a(), b(), c() }
	 * 테스트 { b(), c(), a() }
	 * 태수투 { c(), a(), b() }
	 * 유저 저장 테스트 -> 유저가 DB에 저장됨 -> 유저 조회해서 -> 존재하는지 확인
	 * 실제로 DB에 저장되어 있는지 확인까지 해야 Self-validating
	 * 이를 도와주는 java 객체: assertThat(JUnit Core)
	 *
	 * 통합 테스트에서 좀 빠르게 하기 위해 하는 방법 - Mocking(가짜 객체)
	 * 서비스 레이어를 테스트할 때 repository Mocking 해서 테스트를 빠르게 처리 - repository 호출하면 이런 결과가 나올거라 가정
	 * mocking 방식도 결국 개발자가 하는 거라서 실수가 나오게 되면, 나중에 실수를 찾기가 정말 어려움 - 비선호
	 *
	 * 도메인 테스트, 시버스 테스트, 레포지토리 테스트, 컨트롤러 테스트
	 * 테스트 코드는 연습만이 살길이다
	 *
	 * 맥: cmd + shift + t
	 * 윈도우: ctrl + shift + t
	 */

	private final ProductRepository productRepository;

	@GetMapping
	public ApiResult<Page<ProductResponse.Search>> search(
		@RequestParam(required = false) String keyword,
		@Parameter(hidden = true) Paging paging
	){
		return ApiResult.ok(productRepository.search(keyword, paging.toPageable()));
	}
}
