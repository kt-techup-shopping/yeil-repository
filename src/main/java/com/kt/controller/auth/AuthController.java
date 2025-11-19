package com.kt.controller.auth;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kt.common.ApiResult;
import com.kt.dto.auth.LoginRequest;
import com.kt.dto.auth.LoginResponse;
import com.kt.service.auth.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	// 인증 관련 컨트롤러 구현
	// 인증 방식 크게 3가지 존재
	// 1. 세션 기반 -> 서버 쪽에 작은 공간에 사용자 정보를 저장 (만료 시간과 함께)
	// -> 서버에서 관리하기에 보안성이 좋음
	// -> A 서버에서 인가 후 세션에 저장하고 있음
	// -> B 서버 세션에는 인가된 정보 없음
	// -> 해결: 세션 클러스터링, 스티키 세션 -> redis 등 해결책 외부 저장소를 통해서 단일 세션

	// 2. 토큰 기반 (JWT) -> 사용자가 토큰을 가지고 있다가 요청할 때마다 같이 줌
	// -> 서버 입장에서는 신뢰성 X,
	// 단점: 매번 검사를 해야 함
	// 장점: 서버에서 관리하지 않아 부하가 적음, 분산 환경에 유리

	// 3. OAuth 2.0 기반
	// 서버에서 하는게 아니라 다른 서버에 맡기는 방식(구글, 카카오, 네이버...)
	// 장점 -> 사용자 편하라고 만드는 게 아니라 서버 개발자들 편하려고 쓰는 것
	// 이유: 개인 정보를 취급하지 않아도 되고 인가 작업을 하지 않아도 되어서

	private final AuthService authService;

	@PostMapping("/login")
	public ApiResult<LoginResponse> login(@RequestBody @Valid LoginRequest request){
		var pair = authService.login(request);
		return ApiResult.ok(new LoginResponse(pair.getFirst(), pair.getSecond()));
	}

}
