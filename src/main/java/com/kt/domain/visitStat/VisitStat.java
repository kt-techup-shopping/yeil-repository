package com.kt.domain.visitStat;

import java.time.LocalDateTime;

import com.kt.common.support.BaseEntity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class VisitStat extends BaseEntity {
	private String ip;
	private String userAgent;
	private Long userId = null;
	private LocalDateTime visitedAt;

	public VisitStat(Long userId, String ip, String userAgent) {
		this.userId = userId;
		this.ip = ip;
		this.userAgent = userAgent;
		this.visitedAt = LocalDateTime.now();
	}

}
