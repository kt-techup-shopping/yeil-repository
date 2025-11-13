package com.kt.service.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.domain.product.Product;
import com.kt.dto.product.ProductResponse;
import com.kt.repository.product.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

	private final ProductRepository productRepository;

	// 상품 생성
	public void create(String name, Long price, Long quantity) {
		productRepository.save(
			new Product(
				name,
				price,
				quantity
			));
	}

	// 상품 수정
	public void update(Long id, String name, Long price, Long quantity) {
		var product = productRepository.findByIdOrThrow(id);
		product.update(name, price, quantity);
	}

	// 상품 삭제
	public void delete(Long id) {
		var product = productRepository.findByIdOrThrow(id);
		product.delete();
	}

	public void soldOut(Long id) {
		var product = productRepository.findByIdOrThrow(id);
		product.soldOut();
	}

	public void inActivate(Long id) {
		var product = productRepository.findByIdOrThrow(id);
		product.inActivate();
	}

	public void activate(Long id) {
		var product = productRepository.findByIdOrThrow(id);
		product.activate();
	}

	public void decreaseStock(Long id, Long quantity) {
		var product = productRepository.findByIdOrThrow(id);
		product.decreaseStock(quantity);
	}

	public void increaseStock(Long id, Long quantity) {
		var product = productRepository.findByIdOrThrow(id);
		product.increaseStock(quantity);
	}

	// 상품 단건 조회
	public ProductResponse.Detail detail(Long id) {
		var product = productRepository.findByIdOrThrow(id);
		return new ProductResponse.Detail(
			product.getId(),
			product.getName(),
			product.getPrice(),
			product.getStock(),
			product.getStatus()
		);
	}

}
