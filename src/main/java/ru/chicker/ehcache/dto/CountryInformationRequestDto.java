package ru.chicker.ehcache.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CountryInformationRequestDto {
    @NotNull
    @Min(7)
    private String ipAddress;

    public CountryInformationRequestDto() {
    }

    public CountryInformationRequestDto(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    @Override
    public String toString() {
        return "CountryInformationRequestDto{" +
            "ipAddress='" + ipAddress + '\'' +
            '}';
    }
}
