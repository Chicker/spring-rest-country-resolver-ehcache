package ru.chicker.ehcache.helpers;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public final class RestApiTestHelpers {
    private RestApiTestHelpers() {
    }

    public static void simpleRequestToGetCountryApi(MockMvc mockMvc, String testCountryCode,
                                              String testIpAddress)
    throws Exception {
        mockMvc.perform(
            post("/api/v1/country/getCountryInfo")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(String.format("{\"%s\":\"%s\"}", "ipAddress", testIpAddress)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.countryCode").value(testCountryCode));
    }
}
