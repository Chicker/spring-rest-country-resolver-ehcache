package ru.chicker.ehcache;

import javaslang.control.Try;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.chicker.ehcache.helpers.RestApiTestHelpers;
import ru.chicker.ehcache.service.internal.InfoByIpFreeGeoIpProvider;
import ru.chicker.ehcache.service.internal.InfoByIpIpApiProvider;

import javax.cache.Cache;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CacheTest {
    @TestConfiguration
    public static class TestConfig {
        @Bean
        @Primary
        InfoByIpFreeGeoIpProvider freeGeoIpProvider() {
            return mock(InfoByIpFreeGeoIpProvider.class);
        }

        @Bean
        @Primary
        InfoByIpIpApiProvider infoByIpIpApiProvider() {
            return mock(InfoByIpIpApiProvider.class);
        }
    }

    @Autowired
    private InfoByIpFreeGeoIpProvider freeGeoIpProvider;

    @Autowired
    private InfoByIpIpApiProvider infoByIpIpApiProvider;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    @Qualifier("countryCodesCache")
    private Cache<String, String> countryCodesCache;

    @Before
    public void setUp() throws Exception {
        reset(freeGeoIpProvider, infoByIpIpApiProvider);
        countryCodesCache.clear();
    }

    @Test
    public void when_client_do_same_request_again_should_return_result_from_cache()
    throws Exception {
        String testCountryCode = "ru";
        String testIpAddress = "81.30.212.30";

        when(freeGeoIpProvider.getCountryCode(testIpAddress)).thenReturn(Try.success(testCountryCode));

        RestApiTestHelpers.simpleRequestToGetCountryApi(mockMvc, testCountryCode, testIpAddress);

        // do same request again 
        RestApiTestHelpers.simpleRequestToGetCountryApi(mockMvc, testCountryCode, testIpAddress);

        // at the second time, this service should not be invoked, because the result will be 
        // taken from the cache
        verify(freeGeoIpProvider, times(1)).getCountryCode(any());
    }

    @Test
    public void when_client_has_ip_that_is_in_the_cache_already_should_not_invoke_service()
    throws Exception {
        String testCountryCode = "ru";
        String testIpAddress = "81.30.212.30";

        countryCodesCache.put(testIpAddress, testCountryCode);

        RestApiTestHelpers.simpleRequestToGetCountryApi(mockMvc, testCountryCode, testIpAddress);

        verify(freeGeoIpProvider, never()).getCountryCode(testIpAddress);
    }
}
