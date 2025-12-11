package com.kt.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kt.common.profile.AppProfile;
import com.kt.common.profile.DevProfile;
import com.kt.common.profile.LocalProfile;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RedisConfiguration {

	private final RedisProperties redisProperties;

	// redisson 분산락 동작은
	// DB에 접근하기 전에 락을 획득하고, redis <key, value>에 저장
	// 작업 수행 후
	// 끝나면 락 해제 및 redis 값 삭제
	// redis 내에서 만료 시간을 설정할 수 있음

	@Bean
	@DevProfile
	@AppProfile
	public RedissonClient redissonClient() {
		var config = new Config();
		var host = redisProperties.getCluster().getNodes().getFirst();
		var uri = String.format("rediss://%s", host);

		config
			.useClusterServers()
			.addNodeAddress(uri);

		return Redisson.create(config);
	}

	@Bean
	@LocalProfile
	public RedissonClient localRedissonClient() {
		var config = new Config();
		var host = redisProperties.getCluster().getNodes().getFirst();
		var uri = String.format("redis://%s", host);

		config
			.useSingleServer().setAddress(uri);
		return Redisson.create(config);
	}

}
