package ru.chicker.ehcache.web.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.chicker.ehcache.service.InfoByIpService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CountryDeterminerControllerTest {
    @TestConfiguration
    public static class TestConfig {
        @Bean
        InfoByIpService getInfoByIpService() {
            return mock(InfoByIpService.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InfoByIpService infoByIpService;

    @Test
    public void test_getCountryCodeInfo() throws Exception {
        String testCountryCode = "fr";
        when(infoByIpService.getCountryCode(any())).thenReturn(testCountryCode);

        mockMvc.perform(
            post("/api/v1/country/getCountryInfo")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(String.format("{\"%s\":\"%s\"}", "ipAddress", "any ip")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.countryCode").value(testCountryCode));

        verify(infoByIpService, times(1)).getCountryCode(any());
    }
}
