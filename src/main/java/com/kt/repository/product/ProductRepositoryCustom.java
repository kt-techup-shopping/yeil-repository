package com.kt.repository.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.kt.dto.product.ProductResponse;

public interface ProductRepositoryCustom {
	Page<ProductResponse.Search> search(String keyword, PageRequest pageable);
}
