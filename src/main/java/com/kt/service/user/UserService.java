package com.kt.service.user;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.common.ErrorCode;
import com.kt.common.Preconditions;
import com.kt.domain.user.User;
import com.kt.dto.user.UserCreateRequest;
import com.kt.dto.user.UserRequest;
import com.kt.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

	private final UserRepository userRepository;

	// 트랜잭션 처리
	// PSA - Portable Service Abstraction
	// 환경설정을 살잒 바꿔서 일정한 서비스를 제공하는 것
	public void create(UserRequest.Create request) {
		var newUser = User.normalUser(
			request.loginId(),
			request.password(),
			request.name(),
			request.email(),
			request.mobile(),
			request.gender(),
			request.birthday(),
			LocalDateTime.now(),
			LocalDateTime.now()
		);
		userRepository.save(newUser);
	}

	public boolean isDuplicateLoginId(String loginId) {
		return userRepository.existsByLoginId(loginId);
	}

	public void changePassword(long loginId, String oldPassword, String password) {
		var user = userRepository.findByIdOrThrow(loginId, ErrorCode.NOT_FOUND_USER);

		// if (!user.getPassword().equals(oldPassword)) {
		// 	throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
		// }
		// if (oldPassword.equals(password)) {
		// 	throw new IllegalArgumentException("기존 비밀번호와 동일한 비밀번호로 변경할 수 없습니다.");
		// }

		// 검증 작업
		// 긍정적인 상황만 생각하자 -> 패스워드가 이전것과 달라야 => 해피한 상황
		// 패스워드가 같으면 안되는데 => 넌 해피하지 않은 상황
		Preconditions.validate(user.getPassword().equals(oldPassword), ErrorCode.DOES_NOT_MATCH_OLD_PASSWORD);
		Preconditions.validate(!oldPassword.equals(password), ErrorCode.CAN_NOT_ALLOWED_SAME_PASSWORD);
		user.changePassword(password);
	}

	// CustomPage -> Pageable 인터페이스
	public Page<User> search(Pageable pageable, String keyword) {
		return userRepository.findAllByNameContaining(keyword, pageable);
	}

	public User detail(Long id) {
		return userRepository.findByIdOrThrow(id, ErrorCode.NOT_FOUND_USER);
	}

	public void update(Long id, String name, String email, String mobile) {
		var user = userRepository.findByIdOrThrow(id, ErrorCode.NOT_FOUND_USER);
		user.update(name, email, mobile);
	}

	public void delete(Long id) {
		// 삭제 2가지 방식
		// 1. Soft Delete (논리 삭제)
		// 2. Hard Delete (물리 삭제)
		userRepository.deleteById(id);
	}
}
