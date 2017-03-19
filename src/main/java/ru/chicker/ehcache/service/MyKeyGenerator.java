package ru.chicker.ehcache.service;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

@Component
public class MyKeyGenerator implements KeyGenerator {
    @Override
    public String generate(Object target, Method method, Object... params) {
        if (target instanceof InfoByIpServiceImpl
            && method.getName().equals("getCountryCode")
            && params.length == 1 && (params[0] instanceof Optional)) {
            Optional<String> ipAddress = (Optional<String>) params[0];
            if (ipAddress.isPresent()) return ipAddress.get();
            else return null;
        } else {
            return null;
        }
//        return ((Optional<String>) params[0]).get();
    }
}
