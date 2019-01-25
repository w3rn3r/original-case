package com.afkl.cases.df.service;

import com.afkl.cases.df.dto.Fare;

public interface FareService {

    Fare getFareForOriginDestinationPair(String origin, String destination);
}
