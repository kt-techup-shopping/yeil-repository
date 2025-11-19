package com.kt.security;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
	// implements Filter   // 필터링 2번 작동
	// GenericFilterBean -> OncePerRequestFilter

	private final JwtService jwtService;
	private static final String TOKEN_PREFIX = "Bearer ";

	// jwt 토큰이 header 안에 authorization에 Bearer {token} 형식으로 넘어오는지 확인
	// 1. request에서 header 정보 가져오기
	// 2. Bearer 붙어있으면 빼고 토큰만 가져오기
	// 3. token 유효한지 검사
	// 4. token 만료되었는지 검사
	// 5. 유효하면 id 값 꺼내서 SecurityContextHolder 인가된 객체로 저장

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (Strings.isBlank(header)) {
			filterChain.doFilter(request, response);
			return;
		}

		var token = header.substring(TOKEN_PREFIX.length());
		if (!jwtService.validate(token)) {
			filterChain.doFilter(request, response);
			return;
		}

		var id = jwtService.parseId(token);
		var techUpToken = new TechUpAuthenticationToken(
			new DefaultCurrentUser(id, "파싱로그인아이디"),
			List.of()
		);
		SecurityContextHolder.getContext().setAuthentication(techUpToken);
		filterChain.doFilter(request, response);
	}
}
