package iti.intake41.myapplication.models.Place.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

class API_Client {

    //MARK: - Properties
    private static API_Client client;
    final String BASE_URL = "https://places.ls.hereapi.com/places/v1/";
    public API_Interface api;

    //MARK: - Methods
    private API_Client() {
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        api = retrofit.create(API_Interface.class);
    }

    public static API_Client getInstance() {
        if (client == null) {
            synchronized (API_Client.class) {
                if (client == null) {
                    client = new API_Client();
                }
            }
        }
        return client;
    }

}

