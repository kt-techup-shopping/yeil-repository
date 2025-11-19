package com.kt.security;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtService {
	private final JwtProperties jwtProperties;

	// 2가지의 토큰으로 웹에서 제어
	// 1. access token -> 짤은 유효기간 (5분) -> 리프레시 토큰으로 새로운 액세스 토큰 발급
	// 2. refresh token -> 긴 유효기간 (12시간) -> 만료되면 다시 로그인 해야 함

	// Key -> 어떤 임의의 값을 설정해 key 생성
	public String issue(Long id, Date expiration) {
		return Jwts.builder()
			.subject("kt-cloud-shopping")
			.issuer("yeil")
			.issuedAt(new Date())
			.id(id.toString())
			.expiration(expiration)
			// key 구현해서 넣어줘야 함
			.signWith(jwtProperties.getSecret())
			.compact();
	}

	public Date getAccessTokenExpireDate() {
		return jwtProperties.getAccessTokenExpiration();
	}

	public Date getRefreshTokenExpireDate() {
		return jwtProperties.getRefreshTokenExpiration();
	}

	public boolean validate(String token) {
		return Jwts.parser()
			.verifyWith(jwtProperties.getSecret())
			.build()
			.isSigned(token);
	}

	public Long parseId(String token) {
		var claims = Jwts.parser()
			.verifyWith(jwtProperties.getSecret())
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.getId();

		return Long.valueOf(claims);
	}

}
