package ru.chicker.ehcache.config;

import org.ehcache.CacheManager;
import org.ehcache.config.Configuration;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.xml.XmlConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class AppConfig {
    @Bean
    CacheManager getCacheManager(@Value("${ehcache.config}")
                                     String pathToEhcacheConfig) {

        Configuration xmlConfig = new XmlConfiguration(getClass().getResource(pathToEhcacheConfig));
        CacheManager myCacheManager = CacheManagerBuilder.newCacheManager(xmlConfig);
        myCacheManager.init();

        return myCacheManager;
    }
}
