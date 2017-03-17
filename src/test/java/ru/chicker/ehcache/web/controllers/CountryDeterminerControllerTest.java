package ru.chicker.ehcache.web.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.chicker.ehcache.helpers.RestApiTestHelpers;
import ru.chicker.ehcache.service.InfoByIpService;

import static org.mockito.Mockito.*;

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

    @Before
    public void setUp() throws Exception {
        reset(infoByIpService);
    }

    @Test
    public void test_getCountryCodeInfo() throws Exception {
        String testCountryCode = "fr";
        when(infoByIpService.getCountryCode(any())).thenReturn(testCountryCode);

        RestApiTestHelpers.simpleRequestToGetCountryApi(mockMvc, testCountryCode,
            "any ip");

        verify(infoByIpService, times(1)).getCountryCode(any());
    }

}
