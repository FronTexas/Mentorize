package com.example.fronoman.mentorize;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Fahran on 2/13/2015.
 */
public interface IntellitappService {

    @GET("/app/resources/queryTutors")
    void listTutors(@Query("city") String city,
                    Callback<ArrayList<Tutor>> callback);


}
