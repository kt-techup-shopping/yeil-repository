package com.kt.domain.history;

import com.kt.common.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class History extends BaseEntity {
	@Enumerated(EnumType.STRING)
	private HistoryType historyType;
	private String content;
	private Long userId;

	public History(HistoryType type, Long userId, String detail) {
		this.historyType = type;
		this.userId = userId;
		this.content = detail;
	}
}
