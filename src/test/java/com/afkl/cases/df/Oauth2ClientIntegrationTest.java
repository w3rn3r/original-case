package com.afkl.cases.df;

import com.afkl.cases.df.dto.LocationEntity;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Ignore("Integration test used for developing")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ClientConfiguration.class})
public class Oauth2ClientIntegrationTest {

    @Autowired
    private OAuth2RestTemplate restTemplate;

    @Test
    public void testAirportRetrievalSuccess() {

        ResponseEntity<PagedResources<Resource<LocationEntity>>> airportResponse =
                restTemplate.exchange("http://localhost:8080/airports",
                        HttpMethod.GET, null, new ParameterizedTypeReference<PagedResources<Resource<LocationEntity>>>() {
                });

        PagedResources<Resource<LocationEntity>> pagedResources = airportResponse.getBody();
        assertNotNull(pagedResources);
        List<LocationEntity> list = new ArrayList<>();
        pagedResources.forEach(r -> {
            list.add(r.getContent());
        });
        long size = pagedResources.getMetadata().getSize();
        assertEquals(size, list.size());
    }
}
