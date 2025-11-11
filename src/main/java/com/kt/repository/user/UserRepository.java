package com.kt.repository.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kt.common.CustomException;
import com.kt.common.ErrorCode;
import com.kt.domain.user.User;

// <T, ID>
// T: Entity 클래스 => User
// ID: Entity 클래스의 PK 타입 => Long
public interface UserRepository extends JpaRepository<User, Long> {
	// 쿼리 작성
	// JPA에서는 쿼리 작성 방법 3가지 존재
	// 1. 네이티브 쿼리 작성
	// 2. JPQL 작성 -> 네이티브 쿼리랑 같은데 Entity 기반 - 메소드 이름이 긴 경우에 쿼리로 숨김
	// 3. querymethod 작성 -> 메서드 이름을 쿼리 처럼 작성 - 메소드 이름이 길어지면 복잡해 보임
	// 찾기 : findByXX , 존재하는지 existsByXX, 삭제 : deleteByXX 등

	Boolean existsByLoginId(String loginId);

	@Query("""
	SELECT exists (SELECT u FROM User u WHERE u.loginId = ?1)
""")
	Boolean existsByLoginIdJPQL(String loginId);

	Page<User> findAllByNameContaining(String name, Pageable pageable);

	default User findByIdOrThrow(Long id, ErrorCode errorCode) {
		return findById(id).orElseThrow(() -> new CustomException(errorCode));
	}
}
