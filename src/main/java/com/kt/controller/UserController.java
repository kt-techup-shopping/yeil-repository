package com.kt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kt.dto.UserCreateRequest;
import com.kt.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;


	@PostMapping("/users")
	@ResponseStatus(HttpStatus.CREATED)
	// json 형태의 body에 담겨서 post 요청으로 /users로 들어오면
	// @RequestBody를 보고 jacksonObjectMapper가 동작해서 json to dto로 변환
	public void create(@RequestBody UserCreateRequest request) {
		// Jackson object mapper -> json to dto 맵핑
		System.out.println(request.toString());
		userService.create(request);
	}


}
