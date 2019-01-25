package com.afkl.cases.df.client;

import com.afkl.cases.df.dto.LocationEntity;

import java.util.List;

public interface AirportClient {

    List<LocationEntity> findByTerm(String term);

    LocationEntity getDetailsFor(String airportCode);
}
