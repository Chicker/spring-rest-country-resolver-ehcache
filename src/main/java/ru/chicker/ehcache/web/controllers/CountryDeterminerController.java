package ru.chicker.ehcache.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.chicker.ehcache.dto.CountryInformationDto;
import ru.chicker.ehcache.dto.CountryInformationRequestDto;
import ru.chicker.ehcache.service.InfoByIpService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1")
public class CountryDeterminerController {

    private InfoByIpService infoByIpService;

    @Autowired
    public CountryDeterminerController(InfoByIpService infoByIpService) {
        this.infoByIpService = infoByIpService;
    }

    @RequestMapping(path = "/country/getCountryInfo",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public CountryInformationDto getCountryInformation(
        @Valid @RequestBody CountryInformationRequestDto request) {

        String countryCode = infoByIpService.getCountryCode(Optional.of(request.getIpAddress()));

        return new CountryInformationDto(countryCode);
    }
}
