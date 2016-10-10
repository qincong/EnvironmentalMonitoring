package com.example.qincong.environmentalmonitoring.retrofit;

import java.lang.reflect.Field;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by qincong on 2016/10/10.
 */
public class RetrofitUtil {
  public static <S> S createService(Class<S> serviceClass) {
    String baseUrl="";
    try {
      Field field=serviceClass.getField("BASE_URL");
      baseUrl=(String) field.get(null);
    }
    catch (Exception e) {}
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();
    return retrofit.create(serviceClass);
  }
}
