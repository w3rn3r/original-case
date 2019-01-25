package com.afkl.cases.df.client;

import com.afkl.cases.df.dto.Currency;
import com.afkl.cases.df.dto.Fare;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Component
public class DefaultFareClient implements FareClient {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final OAuth2RestTemplate restTemplate;
    private final String fareServiceEndpointUri;
    private final Currency defaultCurrency;

    @Autowired
    public DefaultFareClient(OAuth2RestTemplate restTemplate,
                             @Value("${fare.service.endpoint}") String fareServiceEndpointUri,
                             @Value("${fare.default.currency}") Currency defaultCurrency) {
        this.restTemplate = restTemplate;
        this.fareServiceEndpointUri = fareServiceEndpointUri;
        this.defaultCurrency = defaultCurrency;
    }

    @Override
    public Fare retrieveFareForFlightConnection(String origin, String destination) {

        String url = fareServiceEndpointUri + "/{origin_code}/{destination_code}";

        Map<String, String> params = new HashMap<>();
        params.put("origin_code", origin);
        params.put("destination_code", destination);

        URI uri = UriComponentsBuilder.fromUriString(url)
                .buildAndExpand(params)
                .toUri();

        uri = UriComponentsBuilder
                .fromUri(uri)
                .queryParam("currency", defaultCurrency)
                .build()
                .toUri();

        String uriString = uri.toString();

        try {
            ResponseEntity<Fare> fareResponse =
                    restTemplate.exchange(
                            uriString,
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<Fare>() {
                            }
                    );

            if (fareResponse.getStatusCode().is2xxSuccessful()) {
                return fareResponse.getBody();
            }
            logger.warn("Unable to retrieve fare for flight with origin {} and destination {}", origin, destination);

        } catch (Exception e) {
            logger.error("An error occurred while calling {}", uriString, e);
        }
        return null;
    }
}
