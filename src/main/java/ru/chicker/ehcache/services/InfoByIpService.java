package ru.chicker.ehcache.services;

import java.util.Optional;

public interface InfoByIpService {
    String getCountryCode(Optional<String> ipAddress);
}
