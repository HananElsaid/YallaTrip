package iti.intake41.myapplication.models.Place.model;

import java.util.List;

public class PlaceResult {
    private List<Place> results;

    public void setResults(List<Place> results){
        this.results = results;
    }
    public List<Place> getResults(){
        return this.results;
    }
}
