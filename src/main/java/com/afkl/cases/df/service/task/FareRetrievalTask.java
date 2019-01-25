package com.afkl.cases.df.service.task;

import com.afkl.cases.df.dto.Fare;
import com.afkl.cases.df.service.FareService;

import java.util.concurrent.Callable;

public class FareRetrievalTask implements Callable<Object> {

    private String origin;
    private String destination;
    private FareService fareService;

    public FareRetrievalTask(String origin,
                             String destination,
                             FareService fareService) {
        this.origin = origin;
        this.destination = destination;
        this.fareService = fareService;
    }

    @Override
    public Fare call() {
        return fareService.getFareForOriginDestinationPair(origin, destination);
    }
}
