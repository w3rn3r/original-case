package com.afkl.cases.df.service;

import com.afkl.cases.df.client.FareClient;
import com.afkl.cases.df.dto.Fare;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Scope("prototype")
@Service
public class DefaultFareService implements FareService {

    private final FareClient fareClient;

    @Autowired
    public DefaultFareService(FareClient fareClient) {
        this.fareClient = fareClient;
    }

    @Override
    public Fare getFareForOriginDestinationPair(String origin, String destination) {
        return fareClient.retrieveFareForFlightConnection(origin, destination);
    }
}
