package com.kt.repository.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.kt.domain.product.Product;

@SpringBootTest
// @DataJpaTest // QueryDSL 연결 X
@Transactional // AfterEach에서 deleteAll 안 해줘도 됨
class ProductRepositoryTest {

	// 테스트 코드에서는 필드 주입도 가능
	@Autowired
	private ProductRepository productRepository;

	private Product product;
	@BeforeEach
	void setUp(){
		product = productRepository.save(
			new Product(
				"테스트 상품",
				100_000L,
				10L
			)
		);
	}

	// @AfterEach
	// void tearDown(){
	// 	productRepository.deleteAll();
	// }

	@Test
	void 이름으로_상품_검색(){
		// 준비 단계 - Given
		// 먼저 상품을 저장해놔야 검색했을 때 있는지 없는지 알 수 있음

		// var product = productRepository.save(new Product(
		// 	"테스트 상품",
		// 	100_000L,
		// 	10L
		// ));
		// -> BeforeEach에서 처리

		// 실행 단계 - When
		// 검색
		var foundedProduct = productRepository.findByName("테스트 상품");

		// 검증 단계 - Then
		// 존재하는지 true or false
		Assertions.assertThat(foundedProduct.isPresent());
	}

}