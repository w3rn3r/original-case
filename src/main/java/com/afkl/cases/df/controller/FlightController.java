package com.afkl.cases.df.controller;

import com.afkl.cases.df.dto.FlightConnection;
import com.afkl.cases.df.dto.FlightDetails;
import com.afkl.cases.df.service.FlightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;


@Controller
public class FlightController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final FlightService flightService;

    private final static String FLIGHT_DETAILS = "flightDetails";
    private final static String FLIGHT_CONNECTION = "flightConnection";


    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @ModelAttribute("flightDetails")
    public FlightDetails getFlightDetails () {
        return new FlightDetails();
    }

    @GetMapping("/flights")
    public ModelAndView showForm(
            @ModelAttribute(FLIGHT_DETAILS) FlightDetails flightDetails,
            Model model) {

        model.addAttribute(FLIGHT_DETAILS, flightDetails);

        return new ModelAndView("flights", FLIGHT_CONNECTION, new FlightConnection());
    }

    @PostMapping("/flights/fare-search")
    public RedirectView submit(
                               @ModelAttribute(FLIGHT_CONNECTION) FlightConnection flightConnection,
                               RedirectAttributes attributes) {

        String origin = flightConnection.getOrigin();
        String dest = flightConnection.getDestination();

        logger.info("Searching for fare from {} to {}", origin, dest);
        FlightDetails flightDetails = flightService.retrieveFlightDetailsForConnection(origin, dest);
        if (flightDetails == null) {
            logger.warn("Unable to fetch flight details from origin {} to destination {}", origin, dest);
            flightDetails = new FlightDetails();
        }
        attributes.addFlashAttribute(FLIGHT_DETAILS, flightDetails);

        return new RedirectView("/flights");
    }
}
