package com.afkl.cases.df.client;

import com.afkl.cases.df.dto.Fare;

public interface FareClient {

    Fare retrieveFareForFlightConnection(String origin, String destination);
}
