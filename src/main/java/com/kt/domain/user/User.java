package com.kt.domain.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.kt.domain.order.Order;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String loginId;
	private String password;
	private String name;
	private String email;
	private String mobile;
	@Enumerated(EnumType.STRING)
	private Gender gender;
	private LocalDate birthday;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	@OneToMany(mappedBy = "user")
	private List<Order> orders = new ArrayList<>();
}
