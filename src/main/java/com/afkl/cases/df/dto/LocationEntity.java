package com.afkl.cases.df.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationEntity {

    public String code;
    private String name;
    private String description;
    private Coordinates coordinates;
    private LocationEntity parent;
    private Set<LocationEntity> children;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public LocationEntity getParent() {
        return parent;
    }

    public void setParent(LocationEntity parent) {
        this.parent = parent;
    }

    public Set<LocationEntity> getChildren() {
        return children;
    }

    public void setChildren(Set<LocationEntity> children) {
        this.children = children;
    }

}
