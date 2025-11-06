package com.kt.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
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
}
