package com.kt.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.kt.domain.Gender;
import com.kt.domain.User;
import com.kt.dto.CustomPage;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepository {

	private final JdbcTemplate jdbcTemplate;

	public void save(User user) {
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
		System.out.println("Saved User: " + user.toString());
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
	public boolean existsByLoginId(String loginId) {
		var sql = "SELECT EXISTS (SELECT id FROM MEMBER WHERE loginId = ?)";
		return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, loginId));
	}

	public void updatePassword(long id, String password) {
		// UPDATE {table} SET {column} = {value} WHERE {condition}
		var sql = "UPDATE member SET password = ? WHERE id = ?";
		jdbcTemplate.update(sql, password, id);
	}

	public boolean existsById(long id) {
		var sql = "SELECT EXISTS (SELECT id FROM MEMBER WHERE id = ?)";
		return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, id));
	}

	public Optional<User> selectById(long id) {
		var sql = "SELECT * FROM member WHERE id = ?";
		var list = jdbcTemplate.query(sql, rowMapper(), id);
		return list.stream().findFirst();
	}

	private RowMapper<User> rowMapper() {
		return (rs, rowNum) -> mapToUser(rs);
	}

	private User mapToUser(ResultSet rs) throws SQLException {
		return new User(
			rs.getLong("id"),
			rs.getString("loginId"),
			rs.getString("password"),
			rs.getString("name"),
			rs.getString("email"),
			rs.getString("mobile"),
			Gender.valueOf(rs.getString("gender")),
			rs.getObject("birthday", LocalDate.class),
			rs.getObject("createdAt", LocalDateTime.class),
			rs.getObject("updatedAt", LocalDateTime.class)
		);
	}

	public CustomPage selectAll(int page, int size) {
		var sql = "SELECT * FROM member LIMIT ? OFFSET ?";
		var users = jdbcTemplate.query(sql, rowMapper(), size, size);

		var countSql = "SELECT COUNT(*) FROM member";
		var totalElements = jdbcTemplate.queryForObject(countSql, Long.class);
		var pages = (int) Math.ceil((double) totalElements / size);
		return new CustomPage(users, page, size, pages, totalElements);
	}
}
