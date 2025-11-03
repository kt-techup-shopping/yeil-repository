package com.kt.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kt.domain.User;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepository {

	private final JdbcTemplate jdbcTemplate;

	public void save(User user){
		// 서비스에서 dto를 도메인으로 바꾼 다음에 전달
		var sql = """
			INSERT INTO MEMBER (
	                    id, 
		                loginId, 
		                password, 
		                name, 
		                birthday, 
		                mobile, 
		                email,
		                gender, 
		                createdAt,
		                updatedAt)
		    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
		""";

		jdbcTemplate.update(sql,
			user.getId(),
			user.getLoginId(),
			user.getPassword(),
			user.getName(),
			user.getBirthday(),
			user.getMobile(),
			user.getEmail(),
			user.getGender(),
			user.getCreatedAt(),
			user.getUpdatedAt()
		);
		System.out.println("Saved User: "+user.toString());
	}

	public Long selectMaxId() {
		var sql = "SELECT MAX(id) FROM MEMBER";

		var maxId = jdbcTemplate.queryForObject(sql, Long.class);
		return maxId == null ? 0L : maxId;
	}

	/* 	아이디 중복 체크 방법 - 3가지
		1. count 해서 0보다 큰지 체크 -> 모든 데이터를 살펴봐야 함 (Full scan)
		2. unique 제약 조건 걸어서 예외 처리 -> 유니크 키 예외 처리 어려움
		3. exists 존재 여부 체크 -> boolean 값 존재 여부를 바로 알 수 있음
	 */
	public boolean existsByLoginId(String loginId){
		var sql = "SELECT EXISTS (SELECT id FROM MEMBER WHERE loginId = ?)";
		return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, loginId));
	}

	public void updatePassword(Integer id, String password){
		// UPDATE {table} SET {column} = {value} WHERE {condition}
		var sql = "UPDATE member SET password = ? WHERE id = ?";
		jdbcTemplate.update(sql, password, id);
	}
}
