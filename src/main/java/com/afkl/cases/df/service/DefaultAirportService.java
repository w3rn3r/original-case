package com.afkl.cases.df.service;

import com.afkl.cases.df.client.AirportClient;
import com.afkl.cases.df.dto.LocationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Scope("prototype")
@Service
public class DefaultAirportService implements AirportService {

    private final AirportClient airportClient;

    @Autowired
    public DefaultAirportService(AirportClient airportClient) {
        this.airportClient = airportClient;
    }


    @Override
    public List<LocationEntity> searchByKeyword(String keyword) {
        return airportClient.findByTerm(keyword);
    }

    @Override
    public LocationEntity findAirportByCode(String airportCode) {
        return airportClient.getDetailsFor(airportCode);
    }
}
