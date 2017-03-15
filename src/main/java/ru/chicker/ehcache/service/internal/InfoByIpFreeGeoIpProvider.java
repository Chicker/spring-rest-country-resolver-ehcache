package ru.chicker.ehcache.service.internal;

import com.jayway.jsonpath.JsonPath;
import javaslang.control.Try;
import ru.chicker.ehcache.utils.HttpUtils;


public class InfoByIpFreeGeoIpProvider implements InfoByIpProvider {
    private static String serviceUrl = "http://freegeoip.net/json";

    @Override
    public Try<String> getCountryCode(String ipAddress) {
        String link = String.format("%s/%s", serviceUrl, ipAddress);

        return HttpUtils.getHttpResponseAsString(link).map(r -> {
            String countryCode = JsonPath.read(r, "$['country_code']");
            return countryCode;
        });
    }
}
