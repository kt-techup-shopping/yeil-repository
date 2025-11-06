package com.kt.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.kt.domain.User;
import com.kt.dto.CustomPage;
import com.kt.dto.UserCreateRequest;
import com.kt.repository.UserJDBCRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserJDBCRepository userJDBCRepository;

	public void create(UserCreateRequest request) {
		System.out.println(request.toString());
		var newUser = new User(
			userJDBCRepository.selectMaxId() + 1,
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
		userJDBCRepository.save(newUser);
	}

	public boolean isDuplicateLoginId(String loginId) {
		return userJDBCRepository.existsByLoginId(loginId);
	}

	public void changePassword(long id, String oldPassword, String password) {
		var user = userJDBCRepository.selectById(id)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

		if (!user.getPassword().equals(oldPassword)) {
			throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
		}

		if (oldPassword.equals(password)) {
			throw new IllegalArgumentException("기존 비밀번호와 동일한 비밀번호로 변경할 수 없습니다.");
		}
		userJDBCRepository.updatePassword(id, password);
	}

	public CustomPage search(int page, int size, String keyword) {
		var pair = userJDBCRepository.selectAll(page - 1, size, keyword);
		var pages = (int)Math.ceil((double)pair.getSecond() / size);
		return new CustomPage(pair.getFirst(), page, size, pages, pair.getSecond());
	}

	public User detail(Long id){
		return userJDBCRepository.selectById(id)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
	}

	public void update(Long id, String name, String email, String mobile) {
		userJDBCRepository.selectById(id)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
		userJDBCRepository.updateById(id, name, email, mobile);
	}

	public void delete(Long id) {
		userJDBCRepository.selectById(id)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
		userJDBCRepository.deleteById(id);
	}
}
