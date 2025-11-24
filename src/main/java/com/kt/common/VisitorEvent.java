package com.kt.common;

public record VisitorEvent (
	Long userId,
	String ip,
	String userAgent
){
}
