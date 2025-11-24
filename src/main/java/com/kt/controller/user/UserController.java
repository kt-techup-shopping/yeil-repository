package com.kt.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kt.common.ApiResult;
import com.kt.common.SwaggerAssistance;
import com.kt.dto.user.UserCreateRequest;
import com.kt.dto.user.UserRequest;
import com.kt.dto.user.UserUpdatePasswordRequest;
import com.kt.security.CurrentUser;
import com.kt.service.user.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "유저", description = "유저 관련 API")
public class UserController extends SwaggerAssistance {

	private final UserService userService;

	/*	API 문서화 크게 2가지 방식 존제
	1. Swagger
	장점: UI 이쁨, 어노테이션 기반으로 작성이 쉬움
	단점: 프로덕션 코드에 Swagger 관련 어노테이션이 존재함 -> SRP 위분 (Controller), 코드가 복잡해지고 유지보수가 힘듦
	2. RestDocs
	장점: 프로덕션 코드에 침법이 없음, 신뢰성 있음
	단점: UI 이쁘지 않음. 테스트 기반이라 문서 작성에 시간이 걸림
	*/

	// json 형태의 body에 담겨서 post 요청으로 /users로 들어오면
	// @RequestBody를 보고 jacksonObjectMapper가 동작해서 json to dto로 변환
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResult<Void> create(@RequestBody @Valid UserRequest.Create request) {
		// Jackson object mapper -> json to dto 맵핑
		userService.create(request);
		return ApiResult.ok();
	}

	// GET 방식에서 쓰는 queryString
	// /users/duplicate-login-id?loginId=abc123
	// @RequestParam 속성 기본값 (required = true)
	@GetMapping("/duplicate-login-id")
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<Boolean> isDuplicateLoginId(@RequestParam String loginId) {
		return ApiResult.ok(userService.isDuplicateLoginId(loginId));
	}

	// URI는 식별이 가능해야 함 -> 어떤 유저인지
	// body -> JSON 전달
	// 식별자 id 값 받는 방법
	// 1. body에 id 값을 같이 받는다
	// 2. URI에 id 값을 넣는다
	// 3. 인증/인가 객체에서 id 값을 꺼낸다
	@PutMapping("/{userId}/update-password")
	public ApiResult<Void> updatePassword(
		@PathVariable(name = "userId") Long id,
		@RequestBody @Valid UserUpdatePasswordRequest request
	) {
		userService.changePassword(id, request.oldPassword(), request.password());
		return ApiResult.ok();
	}

	@GetMapping("/orders")
	@ResponseStatus(HttpStatus.OK)
	public void getOrders(
		@AuthenticationPrincipal CurrentUser currentUser
	) {
		userService.getOrders(currentUser.getId());
	}
}
