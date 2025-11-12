package com.kt.dto.user;

import java.time.LocalDateTime;

import com.kt.domain.user.User;

import io.swagger.v3.oas.annotations.media.Schema;

public interface UserResponse {
	@Schema(name = "UserResponse.Create")
	record Search(
		Long id,
		String name,
		LocalDateTime createdAt
	) {
	}

	@Schema(name = "UserResponse.Detail")
	record Detail(
		Long id,
		String name,
		String email
	) {
		public static Detail of(User user) {
			return new Detail(
				user.getId(),
				user.getName(),
				user.getEmail()
			);
		}
	}
}