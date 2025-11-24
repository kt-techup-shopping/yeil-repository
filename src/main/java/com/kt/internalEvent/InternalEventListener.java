package com.kt.internalEvent;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.kt.common.VisitorEvent;
import com.kt.visitstat.service.VisitStatService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InternalEventListener {

	private final VisitStatService visitStatService;

	@Async
	@EventListener(VisitorEvent.class)
	public void onVisitorEvent(VisitorEvent event){
		visitStatService.create(
			event.userId(),
			event.ip(),
			event.userAgent()
		);
	}
}
