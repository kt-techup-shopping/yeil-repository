package com.kt.integration.slack;

import org.springframework.stereotype.Component;

import com.kt.common.DevProfile;

@Component
@DevProfile
public class DevNotifyApi implements NotifyApi {
	@Override
	public void notify(String message) {
		// 디스코드로 보내는 어떤 구현체
	}
}