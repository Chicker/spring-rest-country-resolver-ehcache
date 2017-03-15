package ru.chicker.ehcache.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CountryDeterminerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test_getCountryCodeInfo() throws Exception {
        String ipAddressFromRussia = "81.30.212.30";
        String countryCodeOfRussia = "ru";

        mockMvc.perform(
            post("/api/v1/country/getCountryInfo")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(String.format("{\"%s\":\"%s\"}", "ipAddress", ipAddressFromRussia)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.countryCode").value(countryCodeOfRussia));
    }
}
