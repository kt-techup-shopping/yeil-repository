package com.kt.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.kt.domain.User;
import com.kt.dto.UserCreateRequest;
import com.kt.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public void create(UserCreateRequest request) {
		System.out.println(request.toString());
		var newUser = new User(
			userRepository.selectMaxId() + 1,
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

	// TODO: 아이디 중복 검사 만들기
	public boolean isDuplicateLoginId(String loginId) {
		return userRepository.existsByLoginId(loginId);
	}

	public void changePassword(int id, String oldPassword, String password) {
		// TODO: 존재하지 않는 경우 유저를 찾을 수 없다는 예외 처리
		// 서비스 입장에서는 id 값이 외부에서 들어오는 값
		// 실제로 DB에 유저가 존재하는지 검사
		// 존재 O 업데이트
		// 존재 X 예외 처리
		if(!userRepository.existsById(id)){
			throw new IllegalArgumentException("존재하지 않는 회원입니다.");
		}
		if(oldPassword.equals(password)) {
			throw new IllegalArgumentException("기존 비밀번호와 동일한 비밀번호로 변경할 수 없습니다.");
		}
		userRepository.updatePassword(id, password);
	}
}
