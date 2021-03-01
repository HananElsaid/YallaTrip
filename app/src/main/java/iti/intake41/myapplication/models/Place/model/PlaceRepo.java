package iti.intake41.myapplication.models.Place.model;

import java.util.List;

import io.reactivex.Observable;

public class PlaceRepo implements PlaceRepoInterface {
    private static PlaceRepo instance = null;
    private String API_KEY = "iCD089zOJiBu7AScjTclji-TEKUO9NkuhKvGdh-JezA";

    public static PlaceRepo getInstance() {
        if (instance == null) {
            synchronized (PlaceRepo.class) {
                if (instance == null) {
                    instance = new PlaceRepo();
                }
            }
        }
        return instance;
    }


    //MARK: - API Calls Methods
    @Override
    public Observable<List<Place>> getItemsList(String text){
        return API_Client.getInstance()
                .api.getItems("30.06,31.25",text,API_KEY)
                .map((result) -> result.getResults())
                .flatMap(places -> { return Observable.fromIterable(places); })
                .filter(place -> { return place.getResultType().equals("place") || place.getResultType().equals("address"); })
                .toList()
                .toObservable();
    }

}
