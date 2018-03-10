package com.example.software02.newearthquake.Helper;

import com.example.software02.newearthquake.Interface.EarthQuakeService;
import com.example.software02.newearthquake.Retrofit.RetrofitClient;

/**
 * Created by SOFTWARE02 on 2/8/2018.
 */

public class Common {

    private static  final String BASE_URL = "https://earthquake-report.com/feeds/recent-eq?json";

    public static EarthQuakeService getNewsService()
    {
        return RetrofitClient.getClient(BASE_URL).create(EarthQuakeService.class);
    }

}
