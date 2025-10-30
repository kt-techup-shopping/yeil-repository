package com.kt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kt.dto.UserCreateRequest;
import com.kt.service.UserService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "유저", description = "유저 관련 API")
public class UserController {

	private final UserService userService;

	/* API 문서화 크게 2가지 방식 존제
	1. Swagger
	장점: UI 이쁨, 어노테이션 기반으로 작성이 쉬움
	단점: 프로덕션 코드에 Swagger 관련 어노테이션이 존재함 -> SRP 위분 (Controller), 코드가 복잡해지고 유지보수가 힘듦
	2. RestDocs
	장점: 프로덕션 코드에 침법이 없음, 신뢰성 있음
	단점: UI 이쁘지 않음. 테스트 기반이라 문서 작성에 시간이 걸림
	*/

	@ApiResponses(value = {
		@ApiResponse(responseCode = "400", description = "유효성 검사 실패"),
		@ApiResponse(responseCode = "500", description = "서버 에러 - 백엔드 문의 바랍니다.")
	})
	@PostMapping("/users")
	@ResponseStatus(HttpStatus.CREATED)
	// json 형태의 body에 담겨서 post 요청으로 /users로 들어오면
	// @RequestBody를 보고 jacksonObjectMapper가 동작해서 json to dto로 변환
	public void create(@Valid @RequestBody UserCreateRequest request) {
		// Jackson object mapper -> json to dto 맵핑
		System.out.println(request.toString());
		userService.create(request);
	}


}
