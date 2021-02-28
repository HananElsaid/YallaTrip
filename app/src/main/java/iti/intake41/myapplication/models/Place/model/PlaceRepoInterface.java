package iti.intake41.myapplication.models.Place.model;

import java.util.List;

import io.reactivex.Observable;

public interface PlaceRepoInterface {
    Observable<List<Place>> getItemsList(String text);
}
