package com.kt.service;

import java.time.LocalDateTime;

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
	public void create(ProductCreateRequest request){
		var newProduct = new Product(
			request.name(),
			request.price(),
			request.stock(),
			ProductStatus.ACTIVE,
			LocalDateTime.now(),
			LocalDateTime.now()
		);
		productRepository.save(newProduct);
	}

}
