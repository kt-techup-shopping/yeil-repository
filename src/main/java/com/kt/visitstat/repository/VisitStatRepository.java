package com.kt.visitstat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kt.visitstat.domain.VisitStat;

public interface VisitStatRepository extends JpaRepository<VisitStat, Long> {
}
