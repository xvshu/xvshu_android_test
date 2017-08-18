package com.xvshu.android.web;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by xvshu on 2017/8/16.
 */
public interface UseService {
    @GET("user/android/{userName}/{passWord}")
    Call<String> login(@Path("userName") String userName,@Path("passWord") String passWord);
}
