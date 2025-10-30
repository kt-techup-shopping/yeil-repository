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
}
