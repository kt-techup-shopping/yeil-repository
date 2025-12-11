package com.kt.common.support;

public record VisitorEvent (
	Long userId,
	String ip,
	String userAgent
){
}
