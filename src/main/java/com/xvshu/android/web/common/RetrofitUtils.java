package com.xvshu.android.web.common;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.wire.WireConverterFactory;

/**
 * Created by xvshu on 2017/8/16.
 */
public class RetrofitUtils {

    private static String baseUrl = "http://172.30.53.58:8080/rest/";
    private static Retrofit  retrofit = null;

    public static Retrofit getRetrofit(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(WireConverterFactory.create())
                    .baseUrl(baseUrl)
                    .build();
        }
        return retrofit;
    }

    public static <T> T genService(Class<T> tClass)throws InstantiationException {
        return getRetrofit().create(tClass);
    }
}
