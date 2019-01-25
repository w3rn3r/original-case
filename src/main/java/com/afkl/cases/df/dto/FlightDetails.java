package com.afkl.cases.df.dto;

public class FlightDetails {

    private LocationEntity origin = new LocationEntity();
    private LocationEntity destination = new LocationEntity();
    private Fare fare = new Fare();

    public LocationEntity getOrigin() {
        return origin;
    }

    public void setOrigin(LocationEntity origin) {
        this.origin = origin;
    }

    public LocationEntity getDestination() {
        return destination;
    }

    public void setDestination(LocationEntity destination) {
        this.destination = destination;
    }

    public Fare getFare() {
        return fare;
    }

    public void setFare(Fare fare) {
        this.fare = fare;
    }


}
