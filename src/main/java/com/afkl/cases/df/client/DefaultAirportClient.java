package com.afkl.cases.df.client;

import com.afkl.cases.df.dto.LocationEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DefaultAirportClient implements AirportClient {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final OAuth2RestTemplate restTemplate;
    private final String airportServiceEndpointUri;


    @Autowired
    public DefaultAirportClient(OAuth2RestTemplate restTemplate,
                                @Value("${airport.service.endpoint.uri}") String airportServiceEndpointUri) {
        this.restTemplate = restTemplate;
        this.airportServiceEndpointUri = airportServiceEndpointUri;
    }

    public List<LocationEntity> findByTerm(String term) {

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(airportServiceEndpointUri)
                .queryParam("term", term);

        String uri = builder.toUriString();

        try {
            ResponseEntity<PagedResources<Resource<LocationEntity>>> airportResponse =
                    restTemplate.exchange(
                            uri,
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<PagedResources<Resource<LocationEntity>>>() {
                            }
                    );

            if (airportResponse.getStatusCode().is2xxSuccessful()) {
                PagedResources<Resource<LocationEntity>> pagedResources = airportResponse.getBody();
                List<LocationEntity> locationEntityList = new ArrayList<>();
                pagedResources.getContent().forEach(l -> locationEntityList.add(l.getContent()));

                return locationEntityList;
            } else {
                logger.trace("Unable to retrieve any results for term {}. Server response was {} {}",
                        term,
                        airportResponse.getStatusCodeValue(),
                        airportResponse.getStatusCode().getReasonPhrase());
            }
        } catch (Exception e) {
            logger.error("An error occurred while calling {}", uri, e);
        }
        return Collections.emptyList();
    }

    @Override
    public LocationEntity getDetailsFor(String airportCode) {

        String url = airportServiceEndpointUri + "/{key}";

        Map<String, String> params = new HashMap<>();
        params.put("key", airportCode);

        URI uri = UriComponentsBuilder.fromUriString(url)
                .buildAndExpand(params)
                .toUri();

        String uriString = uri.toString();

        try {
            ResponseEntity<LocationEntity> airportResponse =
                    restTemplate.exchange(
                            uriString,
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<LocationEntity>() {
                            }
                    );

            if (airportResponse.getStatusCode().is2xxSuccessful()) {
                return airportResponse.getBody();
            }
            logger.error("Unable to retrieve airport details for {}", airportCode);

        } catch (Exception e) {
            logger.error("An error occurred while calling {}", uriString, e);
        }
        return null;
    }
}
