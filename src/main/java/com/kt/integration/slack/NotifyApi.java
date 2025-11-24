package com.kt.integration.slack;


// Dev -> 디스코드
// Prod -> 슬랙
// Local -> 로그
public interface NotifyApi {
	void notify(String message);
}
