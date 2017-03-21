package ru.chicker.ehcache.service;

import io.reactivex.Observable;
import org.springframework.cache.annotation.Cacheable;
import ru.chicker.ehcache.service.internal.InfoByIpFreeGeoIpProvider;
import ru.chicker.ehcache.service.internal.InfoByIpIpApiProvider;
import ru.chicker.ehcache.service.internal.InfoByIpProvider;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class InfoByIpServiceImpl implements ru.chicker.ehcache.service.InfoByIpService {
    private static final String FALLBACK_COUNTRY_CODE = "lv";

    private final InfoByIpFreeGeoIpProvider freeGeoIpProvider;
    private final InfoByIpIpApiProvider infoByIpIpApiProvider;

    public InfoByIpServiceImpl(InfoByIpFreeGeoIpProvider freeGeoIpProvider,
                               InfoByIpIpApiProvider infoByIpIpApiProvider) {
        this.freeGeoIpProvider = freeGeoIpProvider;
        this.infoByIpIpApiProvider = infoByIpIpApiProvider;
    }

    @Override
    @Cacheable(cacheNames = "countryCodes",
        key = "#ipAddress.get()",
        condition = "#ipAddress.isPresent()")
    public String getCountryCode(Optional<String> ipAddress) {
        if (!ipAddress.isPresent()) return FALLBACK_COUNTRY_CODE;

        List<InfoByIpProvider> ipServiceList = Arrays.asList(freeGeoIpProvider, infoByIpIpApiProvider);

        // We are sending the requests to all available services simultaneously.
        // When the service is complete successfully then the result will be passed to  
        // observer. If an error has occurred then no data will be passed to observer
        Observable<String> countryCodeObservable = Observable.create(observer -> {
            for (InfoByIpProvider infoByIpProvider : ipServiceList) {
                CompletableFuture
                    .supplyAsync(() ->
                        infoByIpProvider.getCountryCode(ipAddress.get()))
                    .thenAccept(countryCodeTry -> {
                        countryCodeTry.onSuccess(observer::onNext);
                    });
            }
        });

        // If a timeout has occurred we return the fallback country code
        // and after that we will get the result from the first response
        String countryCode = countryCodeObservable
            .timeout(3, TimeUnit.SECONDS)
            .onErrorReturn(this::falbackValueIfError)
            .first(FALLBACK_COUNTRY_CODE).blockingGet();
        String result = countryCode.toLowerCase();
        
        return result;
    }

    private String falbackValueIfError(Throwable throwable) {
        if (throwable instanceof TimeoutException) {
            // log about no accessibility of all services
            return FALLBACK_COUNTRY_CODE;
        } else {
            // log about unknown error
            return FALLBACK_COUNTRY_CODE;
        }
    }
}
