package ru.chicker.ehcache.dto;

public class CountryInformationRequestDto {
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
