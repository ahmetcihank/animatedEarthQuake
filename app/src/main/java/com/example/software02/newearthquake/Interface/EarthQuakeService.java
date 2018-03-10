package com.example.software02.newearthquake.Interface;

import com.example.software02.newearthquake.Model.ListRetroModel;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by SOFTWARE02 on 2/8/2018.
 */

public interface EarthQuakeService {
    @GET() //"feeds/recent-eq?json"
    Call<ListRetroModel> getSources();
}
