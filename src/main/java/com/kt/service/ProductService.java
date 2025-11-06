package com.kt.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.domain.product.Product;
import com.kt.domain.product.ProductStatus;
import com.kt.dto.ProductCreateRequest;
import com.kt.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

	private final ProductRepository productRepository;

	// 상품 생성
	public void create(ProductCreateRequest request) {
		var newProduct = new Product(
			request.name(),
			request.price(),
			request.stock(),
			ProductStatus.ACTIVE
		);
		productRepository.save(newProduct);
	}

	// 상품 수정
	public void update(Long id, String name, Long price, Long stock, ProductStatus status) {
		var product = productRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
		product.update(name, price, stock, status);
	}

	// 상품 삭제
	public void delete(Long id) {
		var product = productRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
		productRepository.delete(product);
	}

	// 상품 리스트 조회
	public Page<Product> list(Pageable pageable){
		return productRepository.findAll(pageable);
	}


}
