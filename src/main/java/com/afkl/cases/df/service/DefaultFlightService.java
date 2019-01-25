package com.afkl.cases.df.service;

import com.afkl.cases.df.dto.Fare;
import com.afkl.cases.df.dto.FlightDetails;
import com.afkl.cases.df.dto.LocationEntity;
import com.afkl.cases.df.service.task.AirportRetrievalTask;
import com.afkl.cases.df.service.task.FareRetrievalTask;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class DefaultFlightService implements FlightService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final AirportService airportService;
    private final FareService fareService;
    private final int threadPoolSize;

    @Autowired
    public DefaultFlightService(AirportService airportService,
                                FareService fareService,
                                @Value("${client.thread.pool.size}") int threadPoolSize) {
        this.airportService = airportService;
        this.fareService = fareService;
        this.threadPoolSize = threadPoolSize;
    }

    @Override
    public FlightDetails retrieveFlightDetailsForConnection(String origin, String destination) {

        if (StringUtils.isBlank(origin) || StringUtils.isBlank(destination)) {
            return null;
        }

        ExecutorService WORKER_THREAD_POOL = Executors.newFixedThreadPool(threadPoolSize);
        List<Callable<Object>> tasks = Arrays.asList(
                new FareRetrievalTask(origin, destination, fareService),
                new AirportRetrievalTask(origin, airportService),
                new AirportRetrievalTask(destination, airportService));

        FlightDetails flightDetails = null;

        try {
            List<Future<Object>> futures = WORKER_THREAD_POOL.invokeAll(tasks);
            Fare fare = (Fare)futures.get(0).get();
            LocationEntity locationEntityOrigin = (LocationEntity) futures.get(1).get();
            LocationEntity locationEntityDestination = (LocationEntity) futures.get(2).get();

            flightDetails = new FlightDetails();
            flightDetails.setFare(fare);
            flightDetails.setOrigin(locationEntityOrigin);
            flightDetails.setDestination(locationEntityDestination);

        } catch (InterruptedException | ExecutionException e) {
            logger.error("Worker failed to execute task", e);
        }
        return flightDetails;
    }
}
