package com.afkl.cases.df;

import com.afkl.cases.df.client.DefaultAirportClient;
import com.afkl.cases.df.dto.LocationEntity;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;

@Ignore("Integration test used for developing")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ClientConfiguration.class})
public class DefaultAirportClientTest {

    private DefaultAirportClient defaultAirportClient;

    @Autowired
    private OAuth2RestTemplate oAuth2RestTemplate;

    @Before
    public void setup() {
        defaultAirportClient = new DefaultAirportClient(oAuth2RestTemplate, "http://localhost:8080/airports");
    }

    @Test
    public void testFindByTerm() {

        List<LocationEntity> locationEntities = defaultAirportClient.findByTerm("am");
        assertNotNull(locationEntities);
    }
}
