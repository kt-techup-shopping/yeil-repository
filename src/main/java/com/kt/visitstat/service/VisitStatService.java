package com.kt.visitstat.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.visitstat.domain.VisitStat;
import com.kt.visitstat.repository.VisitStatRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class VisitStatService {

	private final VisitStatRepository visitStatRepository;

	public void create(Long userId, String ip, String userAgent){
		visitStatRepository.save(new VisitStat(userId, ip, userAgent));
	}
}
