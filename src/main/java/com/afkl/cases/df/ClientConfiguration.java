package com.afkl.cases.df;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import java.util.Collections;
import java.util.List;

@EnableOAuth2Client
@Configuration
public class ClientConfiguration {

    @Value("${simple.api.client.id}")
    private String clientId;

    @Value("${simple.api.client.secret}")
    private String clientSecret;

    @Value("${simple.api.access.token.uri}")
    private String accessTokenUri;

    @Value("#{'${simple.api.scopes}'.split(',')}")
    private List<String> scopes;

    @Bean
    public OAuth2RestTemplate getOAuth2RestTemplate() {

        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jackson2HalModule());

        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
        converter.setObjectMapper(mapper);

        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(getResourceDetails());
        oAuth2RestTemplate.setMessageConverters(Collections.singletonList(converter));

        return oAuth2RestTemplate;
    }

    @Bean
    public OAuth2ProtectedResourceDetails getResourceDetails() {
        ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
        resourceDetails.setClientId(clientId);
        resourceDetails.setClientSecret(clientSecret);
        resourceDetails.setAccessTokenUri(accessTokenUri);
        resourceDetails.setScope(scopes);
        return resourceDetails;
    }
}
