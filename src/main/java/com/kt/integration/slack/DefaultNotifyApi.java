package com.kt.integration.slack;

import java.util.Arrays;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.kt.common.profile.AppProfile;
import com.slack.api.methods.MethodsClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 실제 슬랙으로 알림 보내기
@Component
@AppProfile
@RequiredArgsConstructor
@Slf4j
public class DefaultNotifyApi implements NotifyApi {
	private final MethodsClient methodsClient;
	private final SlackProperties slackProperties;
	private final Environment environment;

	@Override
	public void notify(String message) {
		// 슬랙으로 발송할 때는 dev or prod
		// local -> 로그
		try {
			methodsClient.chatPostMessage(request -> {
				request.username("spring-bot")
					.channel(slackProperties.logChannel())
					.text("```%s - %s```".formatted(message, getActiveProfile()))
					.build();
				return request;
			});
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	private String getActiveProfile() {
		return Arrays.stream(environment.getActiveProfiles()).findFirst().orElse("local");
	}


}
