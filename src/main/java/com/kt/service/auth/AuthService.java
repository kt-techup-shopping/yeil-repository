package com.kt.service.auth;

import java.util.Date;

import org.springframework.data.util.Pair;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.common.CustomException;
import com.kt.common.ErrorCode;
import com.kt.common.Preconditions;
import com.kt.dto.auth.LoginRequest;
import com.kt.repository.user.UserRepository;
import com.kt.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;

	public Pair<String, String> login(LoginRequest request) {
		var user = userRepository.findByLoginId(request.loginId())
			.orElseThrow(() -> new CustomException(ErrorCode.FAILED_LOGIN));

		Preconditions.validate(passwordEncoder.matches(request.password(), user.getPassword()), ErrorCode.FAILED_LOGIN);
		var accessToken = jwtService.issue(user.getId(), jwtService.getAccessTokenExpireDate());
		var refreshToken = jwtService.issue(user.getId(), jwtService.getRefreshTokenExpireDate());

		return Pair.of(accessToken, refreshToken);
	}
}
