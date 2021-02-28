package iti.intake41.myapplication.models.Place.model;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API_Interface {

    @GET("autosuggest")
    Observable<PlaceResult> getItems(@Query("at") String location, @Query("q") String searchText,
                                     @Query("apiKey") String apiKey);
}
