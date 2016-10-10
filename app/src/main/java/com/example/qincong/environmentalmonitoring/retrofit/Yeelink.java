package com.example.qincong.environmentalmonitoring.retrofit;

import com.example.qincong.environmentalmonitoring.Point;
import java.util.List;

import java.util.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by qincong on 2016/10/4.
 */
public interface Yeelink {
    String BASE_URL="http://api.yeelink.net/";

    @GET("v1.0/device/351124/sensor/394370.json?start=2016-10-03T00:04:43&end=2016-10-04T16:04:43&interval=30&page=1")
    rx.Observable<List<Point>> listPoint();
}
