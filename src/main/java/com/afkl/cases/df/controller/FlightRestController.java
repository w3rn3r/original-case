package com.afkl.cases.df.controller;

import com.afkl.cases.df.dto.LocationEntity;
import com.afkl.cases.df.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/flight-search")
public class FlightRestController {

    private final AirportService airportService;

    @Autowired
    public FlightRestController(AirportService airportService) {
        this.airportService = airportService;
    }

    @RequestMapping(value = "/findBy", method = RequestMethod.GET)
    public @ResponseBody List<LocationEntity> fuzzySearch(
            @RequestParam(value = "keyword") String keyword) {

        return airportService.searchByKeyword(keyword);
    }
}
