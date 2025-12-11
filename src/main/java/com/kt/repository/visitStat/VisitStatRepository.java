package com.kt.repository.visitStat;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kt.domain.visitStat.VisitStat;

public interface VisitStatRepository extends JpaRepository<VisitStat, Long> {
}
