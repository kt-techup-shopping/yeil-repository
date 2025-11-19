package com.kt.service.order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kt.domain.product.Product;
import com.kt.domain.user.Gender;
import com.kt.domain.user.Role;
import com.kt.domain.user.User;
import com.kt.repository.order.OrderRepository;
import com.kt.repository.orderProduct.OrderProductRepository;
import com.kt.repository.product.ProductRepository;
import com.kt.repository.user.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @Transactional // 비동기 테스트에서 트랜잭션 충돌
class OrderServiceTest {
	// 동시성 제어 Lock 걸어서 처리해야 함 3가지 방식
	// 1. 비관적 락(Pessimistic Lock) -> DB 지원 Lock
	// SELECT ... FOR UPDATE -> 한 트랜잭션이 락을 걸면 다른 트랜잭션은 대기
	// 화장실 한 명씩 들어가고 앞 사람을 기다려야 함
	// 단점: 시간이 오래 걸리고 데드락 발생할 수 있음

	// 2. 낙관적 락(Optimistic Lock) -> 버전 관리
	// 일단 화장실에 들어가고 나올 때 최신 버전 확인
	// 처음 입장할 때 버전 조회 - 작업 끝나고 - 나갈 대 다시 버전 조회해서 같으면 재고 차감

	// 3. 분산 락 -> 레디스
	// 화장실 -> 한명씩 들어감 -> 오래 걸리면 끌고 나오고 내가 들어감 (타임아웃 존재)

	@BeforeEach
	void setUp() {
		orderProductRepository.deleteAll();
		orderRepository.deleteAll();
		productRepository.deleteAll();
		userRepository.deleteAll();
	}

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderProductRepository orderProductRepository;

	@Test
	void 주문_생성() {
		// given
		var user = userRepository.save(
			new User(
				"test",
				"1234",
				"user",
				"eamil",
				"010-1234-5678",
				Gender.MALE,
				LocalDate.now(),
				Role.USER)
		);

		var product = productRepository.save(
			new Product(
				"테스트 상품",
				100_000L,
				10L
			));
		// when
		orderService.create(
			user.getId(),
			product.getId(),
			"수령인",
			"수령인 주소",
			"010-0000-0000",
			2L
		);

		// then
		var foundedProduct = productRepository.findByIdPessimistic(product.getId());
		var foundedOrder = orderRepository.findAll().stream().findFirst();

		Assertions.assertThat(foundedProduct.getStock()).isEqualTo(8L);
		Assertions.assertThat(foundedProduct.getOrderProducts()).isNotEmpty();
		Assertions.assertThat(foundedOrder).isPresent();
	}

	@Test
	void 동시에_여러명이_주문해도_재고이상_주문은_막힌다() throws InterruptedException {
		// given
		int repeatCount = 100;
		int threadCount = 30;

		var userList = new ArrayList<User>();
		for (int i = 0; i < repeatCount; i++) {
			userList.add(new User(
				"test-" + i,
				"1234",
				"user-" + i,
				"email-" + i,
				"010-1234-5678-" + i,
				Gender.MALE,
				LocalDate.now(),
				Role.USER
			));
		}

		var users = userRepository.saveAll(userList);
		var product = productRepository.save(
			new Product(
				"테스트 상품",
				100_000L,
				10L   // 재고 10개
			)
		);

		var executorService = Executors.newFixedThreadPool(threadCount);
		var startLatch = new CountDownLatch(1);
		var doneLatch = new CountDownLatch(repeatCount);

		AtomicInteger successCount = new AtomicInteger(0);
		AtomicInteger failureCount = new AtomicInteger(0);

		for (int i = 0; i < repeatCount; i++) {
			int finalId = i;
			executorService.submit(() -> {
				try {
					startLatch.await(); // 동시에 출발
					var targetUser = users.get(finalId);

					orderService.create(
						targetUser.getId(),
						product.getId(),
						targetUser.getName(),
						"수령인 주소-" + finalId,
						"010-0000-0000-" + finalId,
						1L
					);

					successCount.incrementAndGet();
				} catch (Exception e) {
					// 예외 로그 보고 싶으면 여기서 찍기
					// e.printStackTrace();
					failureCount.incrementAndGet();
				} finally {
					doneLatch.countDown();
				}
			});
		}

		// 전부 출발
		startLatch.countDown();

		// 최대 10초까지만 기다리고 종료
		doneLatch.await(10, TimeUnit.SECONDS);
		executorService.shutdown();

		// then
		var foundedProduct = productRepository.findByIdOrThrow(product.getId());

		System.out.println("성공한 주문 수: " + successCount.get());
		System.out.println("실패한 주문 수: " + failureCount.get());
		System.out.println("남은 재고 수: " + foundedProduct.getStock());

		// 재고는 10개라 10건만 성공해야 함
		Assertions.assertThat(successCount.get()).isEqualTo(10);
		Assertions.assertThat(foundedProduct.getStock()).isEqualTo(0L);
		Assertions.assertThat(failureCount.get()).isEqualTo(repeatCount - 10);
	}


}