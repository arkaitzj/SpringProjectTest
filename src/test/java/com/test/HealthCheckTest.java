package com.test;

import com.test.health.GeneralHealthService;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Test;

import javax.ws.rs.ApplicationPath;

import static org.mockito.BDDMockito.given;
import static org.testng.AssertJUnit.assertEquals;


@Slf4j
@ContextConfiguration("/applicationContext.xml")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HealthCheckTest extends org.springframework.test.context.testng.AbstractTestNGSpringContextTests {


    @LocalServerPort
    private int port;

    @Autowired
    protected TestRestTemplate restTemplate;

    @MockBean
    GeneralHealthService generalHealthService;


    @Configuration
    @ApplicationPath("/services")
    public static class TestConfig extends ResourceConfig {
        public TestConfig() {
            packages("com.test");
        }
    }

    @Test
    public void testOne() {
        ResponseEntity<String> entity = this.restTemplate.getForEntity("/services/health/", String.class);
        assertEquals(entity.getStatusCode().value(), 200);

        given(generalHealthService.areYouOk()).willReturn(false);
        entity = this.restTemplate.getForEntity("/services/health/", String.class);
        assertEquals(entity.getStatusCode().value(), 503);

    }

}
