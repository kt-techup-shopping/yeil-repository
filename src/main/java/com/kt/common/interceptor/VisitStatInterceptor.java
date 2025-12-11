package com.kt.common.interceptor;

import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kt.common.support.VisitorEvent;
import com.kt.service.visitStat.VisitStatService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VisitStatInterceptor implements HandlerInterceptor {
	private final ApplicationEventPublisher applicationEventPublisher;
	private final VisitStatService visitStatService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		var principal = Optional.of(request.getUserPrincipal());
		var userId = principal.isPresent() ? Long.valueOf(principal.get().getName()) : null;
		// visitStatService.create(userId, request.getRemoteAddr(), request.getHeader("User-Agent"));
		applicationEventPublisher.publishEvent(
			new VisitorEvent(userId, request.getRemoteAddr(), request.getHeader("User-Agent"))
		);

		// return HandlerInterceptor.super.preHandle(request, response, handler);
		return true;
	}
}
