package ru.chicker.ehcache.dto;

public class CountryInformationDto {
    private String countryCode;

    protected CountryInformationDto() { }

    public CountryInformationDto(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public String toString() {
        return "CountryInformationDto{" +
            "countryCode='" + countryCode + '\'' +
            '}';
    }
}
