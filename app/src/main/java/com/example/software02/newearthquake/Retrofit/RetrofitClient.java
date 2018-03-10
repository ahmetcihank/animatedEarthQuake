package com.example.software02.newearthquake.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by SOFTWARE02 on 2/8/2018.
 */

public class RetrofitClient
{
    private static Retrofit retrofit = null;
    public static  Retrofit getClient(String baseUrl){
        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder().baseUrl(baseUrl).
                    addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofit;
    }

}
