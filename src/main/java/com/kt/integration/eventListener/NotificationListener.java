package com.kt.integration.eventListener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.kt.common.MessageEvent;
import com.kt.integration.slack.NotifyApi;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationListener {
	private final NotifyApi notifyApi;

	@EventListener(MessageEvent.class)
	public void handleMessageEvent(MessageEvent event) {
		notifyApi.notify(event.message());
	}
}
