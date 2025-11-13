package com.kt.domain.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.kt.common.BaseEntity;
import com.kt.domain.order.Order;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseEntity {
	private String loginId;
	private String password;
	private String name;
	private String email;
	private String mobile;
	// ordinal: enum의 순서를 DB에 저장 -> 절대 사용 X
	// string: enum의 이름을 DB에 저장
	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Enumerated(EnumType.STRING)
	 private Role role;

	private LocalDate birthday;

	@OneToMany(mappedBy = "user")
	private List<Order> orders = new ArrayList<>();

	public User(String loginId, String password, String name, String email, String mobile, Gender gender,
		LocalDate birthday, Role role) {
		this.loginId = loginId;
		this.password = password;
		this.name = name;
		this.email = email;
		this.mobile = mobile;
		this.gender = gender;
		this.birthday = birthday;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
		this.role = role;
	}

	// 정적 팩토리 메서드 방식
	public static User normalUser(String loginId, String password, String name, String email, String mobile, Gender gender,
		LocalDate birthday){
		return new User(loginId, password, name, email, mobile, gender, birthday, Role.USER);
	}

	public static User adminUser(String loginId, String password, String name, String email, String mobile, Gender gender,
		LocalDate birthday){
		return new User(loginId, password, name, email, mobile, gender, birthday, Role.ADMIN);
	}

	public void changePassword(String password) {
		this.password = password;
	}

	public void update(String name, String email, String mobile) {
		this.name = name;
		this.email = email;
		this.mobile = mobile;
	}
}
