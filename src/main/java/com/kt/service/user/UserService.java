package com.kt.service.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.common.exception.ErrorCode;
import com.kt.common.support.Preconditions;
import com.kt.domain.user.User;
import com.kt.dto.user.UserRequest;
import com.kt.repository.order.OrderRepository;
import com.kt.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final OrderRepository orderRepository;

	// 트랜잭션 처리
	// PSA - Portable Service Abstraction
	// 환경설정을 살잒 바꿔서 일정한 서비스를 제공하는 것
	public void create(UserRequest.Create request) {
		var newUser = User.normalUser(
			request.loginId(),
			// request.password(),
			passwordEncoder.encode(request.password()),
			request.name(),
			request.email(),
			request.mobile(),
			request.gender(),
			request.birthday()
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

	public void getOrders(Long id) {
		var user = userRepository.findByIdOrThrow(id, ErrorCode.NOT_FOUND_USER);
		var orders = orderRepository.findAllByUserId(user.getId());

		var products = orders.stream()
			.flatMap(order -> order.getOrderProducts().stream()
				.map(orderProduct -> orderProduct.getProduct().getName())).toList();

		// var statuses = orders.stream()
		// 	.flatMap(order -> order.getOrderProducts().stream()
		// 		.map(orderProduct -> orderProduct.getOrder().getStatus())).toList();

		// N개의 주문이 있는데 N개의 주문엔 상품이 존재하는데 가짓수가 1만개

		// Stream의 연산과정
		// 1. 스트림생성
		// 2. 중간연산 -> 여러번 가능 O
		// 3. 최종연산 -> 여러번 가능 X -> 재사용 불가능

		// List<List<Product>> -> List<Product>

		// N + 1 문제를 해결하는 방법
		// 1. fetch join 사용 -> JPQL전용 -> 딱 1번 사용 2번사용하면 에러남
		// 2. @EntityGraph 사용 -> JPA표준기능 -> 여러번 사용가능
		// 3. batch fetch size 옵션 사용 -> 전역설정 -> paging동작원리와 같아서 성능이슈가 있을 수 있음
		// 4. @BatchSize 어노테이션 사용 -> 특정 엔티티에만 적용 가능
		// 5. native query 사용해서 해결

		// Collection, stream, foreach

		// 연관관계를 아예 끊는다 -> 엔티티자체를 느슨하게 결합해둔다.
		// JPA를 안쓴다.
	}
}
