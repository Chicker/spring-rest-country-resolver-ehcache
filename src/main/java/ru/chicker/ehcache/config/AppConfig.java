package ru.chicker.ehcache.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import ru.chicker.ehcache.service.InfoByIpService;
import ru.chicker.ehcache.service.InfoByIpServiceImpl;
import ru.chicker.ehcache.service.internal.InfoByIpFreeGeoIpProvider;
import ru.chicker.ehcache.service.internal.InfoByIpIpApiProvider;

@SpringBootConfiguration
public class AppConfig {
    
    @Bean
    public InfoByIpFreeGeoIpProvider getInfoByIpFreeGeoIpProvider() {
        return new InfoByIpFreeGeoIpProvider();
    }
    
    @Bean
    public InfoByIpIpApiProvider getInfoByIpIpApiProvider() {
        return new InfoByIpIpApiProvider();
    }
    
    @Bean
    public InfoByIpService getInfoByIpService() {
        return new InfoByIpServiceImpl(getInfoByIpFreeGeoIpProvider(), getInfoByIpIpApiProvider());
    }
}
