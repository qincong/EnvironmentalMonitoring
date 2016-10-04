package com.example.qincong.environmentalmonitoring;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by qincong on 2016/10/4.
 */
public interface Yeelink {
    @GET("v1.0/device/351124/sensor/394370.json?start=2016-10-03T00:04:43&end=2016-10-04T16:04:43&interval=30&page=1")
    Call<List<Point>> listPoint();
}
