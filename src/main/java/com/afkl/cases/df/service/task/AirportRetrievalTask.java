package com.afkl.cases.df.service.task;

import com.afkl.cases.df.service.AirportService;

import java.util.concurrent.Callable;

public class AirportRetrievalTask implements Callable<Object> {

    private String airportCode;

    private AirportService airportService;

    public AirportRetrievalTask(String airportCode,
                                AirportService airportService) {
        this.airportCode = airportCode;
        this.airportService = airportService;
    }

    @Override
    public Object call() {
        return airportService.findAirportByCode(airportCode);
    }
}
