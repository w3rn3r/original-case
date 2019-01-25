package com.afkl.cases.df.service;

import com.afkl.cases.df.dto.LocationEntity;

import java.util.List;

public interface AirportService {

    List<LocationEntity> searchByKeyword(String keyword);

    LocationEntity findAirportByCode(String airportCode);
}
