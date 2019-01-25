package com.afkl.cases.df.service;

import com.afkl.cases.df.dto.FlightDetails;

public interface FlightService {

    FlightDetails retrieveFlightDetailsForConnection(String origin, String destination);

}
