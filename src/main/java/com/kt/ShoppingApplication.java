package com.kt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;

import com.kt.common.MessageEvent;

import lombok.RequiredArgsConstructor;

@EnableAsync
@ConfigurationPropertiesScan
@SpringBootApplication
@RequiredArgsConstructor
public class ShoppingApplication {
	private final ApplicationEventPublisher applicationEventPublisher;
	// private final SlackApi slackApi;

	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady() {
		// slackApi.notify("Shopping Application Started");
		applicationEventPublisher.publishEvent(new MessageEvent("Shopping Application Started"));
	}

	public static void main(String[] args) {
		SpringApplication.run(ShoppingApplication.class, args);
	}

}
