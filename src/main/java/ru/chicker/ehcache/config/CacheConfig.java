package ru.chicker.ehcache.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import ru.chicker.ehcache.service.MyKeyGenerator;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import javax.cache.configuration.MutableConfiguration;

@SpringBootConfiguration
public class CacheConfig {
    @Bean("myKeyGenerator")
    KeyGenerator keyGenerator() {
        return new MyKeyGenerator();
    }

    @Bean
    Cache<String, String> countryCodesCache(CacheManager cacheManager) {
        return getCacheOrCreate(cacheManager, "countryCodes", new MutableConfiguration());
    }

    private Cache getCacheOrCreate(CacheManager cacheManager, String cacheName,
                                  Configuration configuration) {
        Cache cache = cacheManager.getCache(cacheName);
        if (null == cache) {
            return cacheManager.createCache(cacheName, configuration);
        } else {
            return cache;
        }
    }
}
