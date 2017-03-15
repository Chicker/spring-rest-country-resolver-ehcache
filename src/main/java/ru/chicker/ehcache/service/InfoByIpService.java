package ru.chicker.ehcache.service;

import java.util.Optional;

public interface InfoByIpService {
    String getCountryCode(Optional<String> ipAddress);
}
