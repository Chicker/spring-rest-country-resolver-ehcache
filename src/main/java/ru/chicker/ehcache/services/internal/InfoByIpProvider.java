package ru.chicker.ehcache.services.internal;

import javaslang.control.Try;

public interface InfoByIpProvider {
    Try<String> getCountryCode(String ipAddress);
}
