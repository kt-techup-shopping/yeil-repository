package com.kt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Configuration
public class QueryDslConfiguration {
	// QueryDSL
	// 컴파일러(QClass) 기반으로 동적 쿼리를 생성해주는 라이브러리
	// QClass == Entity = QProduct
	// BooleanExpression, BooleanBuilder
	/**
	 * select * from product
	 * where if(keyword != null) {
	 * 		name like '%keyword%'
	 * }
	 **/

	// QueryDSL 사용 방법 2가지
	// 1. {domain}RepositoryCustom + {domain}RepositoryImpl
	// 2. {domain}Query => 클래스를 만들어서 사용

	@PersistenceContext
	private EntityManager entityManager;

	@Bean
	public JPAQueryFactory jpaQueryFactory() {
		return new JPAQueryFactory(entityManager);
	}
}
