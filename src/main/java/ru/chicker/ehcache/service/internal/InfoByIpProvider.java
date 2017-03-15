package ru.chicker.ehcache.service.internal;

import javaslang.control.Try;

public interface InfoByIpProvider {
    Try<String> getCountryCode(String ipAddress);
}
