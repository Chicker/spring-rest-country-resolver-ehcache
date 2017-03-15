package ru.chicker.ehcache.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import ru.chicker.ehcache.services.InfoByIpService;
import ru.chicker.ehcache.services.InfoByIpServiceImpl;
import ru.chicker.ehcache.services.internal.InfoByIpFreeGeoIpProvider;
import ru.chicker.ehcache.services.internal.InfoByIpIpApiProvider;

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
