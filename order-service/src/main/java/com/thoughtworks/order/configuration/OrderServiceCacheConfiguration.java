package com.thoughtworks.order.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.CaffeineSpec;

@Configuration
@EnableCaching
public class OrderServiceCacheConfiguration {

	@Value("#{'${maximumCacheSize}'.trim()}")
	private int maximumLineCacheSize;

	@Value("#{'${cacheExpireAfterWrite}'.trim()}")
	private String cacheLineExpireAfterWrite;


	@Bean
	public CaffeineCacheManager OrderServiceCacheManager() {

		CaffeineCacheManager manager = new CaffeineCacheManager();
		CaffeineSpec lineSpec = CaffeineSpec
				.parse("maximumSize=" + maximumLineCacheSize + ",expireAfterWrite=" + cacheLineExpireAfterWrite);
		manager.setCaffeineSpec(lineSpec);
		return manager;

	}

}
