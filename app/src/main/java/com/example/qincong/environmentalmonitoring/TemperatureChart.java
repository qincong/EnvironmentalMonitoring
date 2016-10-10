package com.example.qincong.environmentalmonitoring;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.example.qincong.environmentalmonitoring.retrofit.RetrofitUtil;
import com.example.qincong.environmentalmonitoring.retrofit.Yeelink;
import com.example.qincong.environmentalmonitoring.retrofit.Yeelink_anqi;
import com.example.qincong.environmentalmonitoring.retrofit.Yeelink_co2;
import com.example.qincong.environmentalmonitoring.retrofit.Yeelink_guangzhao;
import com.example.qincong.environmentalmonitoring.retrofit.Yeelink_liuhuaqing;
import com.example.qincong.environmentalmonitoring.retrofit.Yeelink_shidu;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Observable;
import java.util.Observer;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by qincong on 2016/10/2.
 */
public class TemperatureChart extends AppCompatActivity {

  private boolean hasAxes = true;
  private boolean hasAxesNames = true;
  private boolean hasLines = true;
  private boolean hasPoints = true;
  private ValueShape shape = ValueShape.CIRCLE;
  private boolean isFilled = false;
  private boolean hasLabels = false;
  private boolean isCubic = true;
  private boolean hasLabelForSelected = false;
  private boolean pointsHaveDifferentColor;

  LineChartData data;
  LineChartView lineChartView;
  String newBody = "";
  String oldBody = "";
  private final OkHttpClient client = new OkHttpClient();
  Gson gson = new Gson();

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.temperature_chart);
    lineChartView = (LineChartView) findViewById(R.id.chart);
    lineChartView.setOnValueTouchListener(new LineChartOnValueSelectListener() {
      @Override
      public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
        Toast.makeText(TemperatureChart.this,
            "时间：" + new String(value.getLabelAsChars()) + " 温度：" + value.getY(), Toast.LENGTH_SHORT)
            .show();
      }

      @Override
      public void onValueDeselected() {
      }
    });
    //Retrofit retrofit = new Retrofit.Builder()
    //    .baseUrl("http://api.yeelink.net/")
    //    .addConverterFactory(GsonConverterFactory.create())
    //    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
    //    .build();
    final int flag = getIntent().getIntExtra("flag", 0);
    Action1<List<Point>> action1 = new Action1<List<Point>>() {
      @Override public void call(List<Point> points) {
        List<PointValue> listPoint = new ArrayList<PointValue>();
        for(Point point : points) {
          listPoint.add(new PointValue(points.indexOf(point),point.getValue()));
        }
        Line line=new Line(listPoint);
        line.setColor(ChartUtils.COLOR_BLUE);
        line.setShape(ValueShape.CIRCLE);
        line.setCubic(false);
        line.setFilled(true);
        line.setHasLines(true);
        line.setHasPoints(false);
        List<Line> lines=new ArrayList<Line>();
        lines.add(line);
        data=new LineChartData(lines);
        Axis axisX=new Axis();
        Axis axisY=new Axis().setHasLines(true);
        axisX.setName("时间");
        switch (flag) {
          case 0:
            axisY.setName("温度");
            break;
          case 1:
            axisY.setName("湿度");
            break;
          case 2:
            axisY.setName("氨气浓度");
            break;
          case 3:
            axisY.setName("二氧化碳");
            break;
          case 4:
            axisY.setName("光照强度");
            break;
          case 5:
            axisY.setName("硫化氢浓度");
            break;
        }
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);
        lineChartView.setLineChartData(data);
      }
    };
    switch (flag) {
      case 1:
        Log.w("number","start");
        RetrofitUtil.createService(Yeelink.class)
            .listPoint()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(action1);
        break;
      case 2:
        RetrofitUtil.createService(Yeelink_shidu.class)
            .listPoint()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(action1);
        break;
      case 3:
        RetrofitUtil.createService(Yeelink_anqi.class)
            .listPoint()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(action1);
        break;
      case 4:
        RetrofitUtil.createService(Yeelink_co2.class)
            .listPoint()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(action1);
        break;
      case 5:
        RetrofitUtil.createService(Yeelink_guangzhao.class)
            .listPoint()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(action1);
      case 6:
        RetrofitUtil.createService(Yeelink_liuhuaqing.class)
            .listPoint()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(action1);
    }
    //Viewport viewport = lineChartView.getCurrentViewport();
    //int n = data.getLines().get(0).getValues().size();
    //viewport.left = n - 15;
    //viewport.right = n - 1;
    //viewport.bottom = (float) 24.6;
    //viewport.top = (float) 26.8;
    //lineChartView.setCurrentViewport(viewport);
    //
    //viewport = lineChartView.getMaximumViewport();
    //n = data.getLines().get(0).getValues().size();
    //viewport.left = 0;
    //viewport.right = n - 1;
    //viewport.bottom = (float) 0;
    //viewport.top = (float) 50;
    //
    //lineChartView.setMaximumViewport(viewport);

    //thread.start();
    //        List<Line> lines = new ArrayList<Line>();
    //        List<PointValue> values = new ArrayList<PointValue>();
    //        for (int i = 0; i < 10; i++) {
    //            values.add(new PointValue(i, (float) Math.random() * 100f));
    //        }
    //        Line line = new Line(values);
    //        lines.add(line);
    //        data = new LineChartData(lines);
    //        lineChartView.setLineChartData(data);
  }

  Handler handler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      Bundle bundle = msg.getData();
      boolean hasChanged = bundle.getBoolean("hasChanged");
      if (hasChanged) {
        lineChartView.setLineChartData(data);
      }

    }
  };
  //Thread thread = new Thread(new Runnable() {
  //  @Override
  //  public void run() {
  //    Response response = null;
  //    List<Point> pointList = null;
  //    if (true) {
  //      //                Request request = new Request.Builder()
  //      //                        .addHeader("U-ApiKey", "f94d4aac27210bdaef3a022a909a8a60")
  //      //                        .url("http://api.yeelink.net/v1.0/device/351124/sensor/394370.json?start=2016-10-03T00:04:43&end=2016-10-04T16:04:43&interval=30&page=1")
  //      //                        .build();
  //      //                try {
  //      //                    response = client.newCall(request).execute();
  //      //                    newBody = response.body().string();
  //      //                    Log.w("body", newBody);
  //      //                } catch (IOException e) {
  //      //                    e.printStackTrace();
  //      //                }
  //
  //
  ////      try {
  ////        pointList = repos.execute().body();
  ////      } catch (IOException e) {
  ////      }
  ////      if (true || !newBody.equals(oldBody)) {
  ////        oldBody = newBody;
  ////        //                    List<Point> pointList = gson.fromJson(newBody, new TypeToken<List<Point>>() {
  ////        //                    }.getType());
  ////        List<PointValue> pointValueList = new ArrayList<PointValue>();
  ////        for (int i = 0; i < pointList.size(); i++) {
  ////          pointValueList.add(new PointValue(i, pointList.get(i).getValue()).setLabel(
  ////              pointList.get(i).getTimestamp()));
  ////          Log.w("number", pointList.get(i).getValue() + "");
  ////        }
  ////        Line line = new Line(pointValueList);
  ////        line.setColor(lecho.lib.hellocharts.util.ChartUtils.COLORS[0]);
  ////        line.setShape(shape);
  ////        line.setCubic(isCubic);
  ////        line.setFilled(isFilled);
  ////        line.setHasLabels(hasLabels);
  ////        line.setHasLabelsOnlyForSelected(hasLabelForSelected);
  ////        line.setHasLines(hasLines);
  ////        line.setHasPoints(false);
  ////
  ////        List<Line> lines = new ArrayList<Line>();
  ////        lines.add(line);
  ////
  ////        data = new LineChartData(lines);
  ////        List<Float> axisValues = new ArrayList<Float>();
  ////        List<String> axisValuesLabels = new ArrayList<String>();
  ////        int i = 0;
  ////        for (PointValue pointValue : pointValueList) {
  ////          axisValues.add(pointValue.getX());
  ////          i++;
  ////          if (i % 5 == 0) {
  ////            axisValuesLabels.add(new String(pointValue.getLabel()));
  ////          } else {
  ////            axisValuesLabels.add("");
  ////          }
  ////        }
  ////        Axis axisX = Axis.generateAxisFromCollection(axisValues, axisValuesLabels);
  ////        Axis axisY = new Axis().setHasLines(true);
  ////        if (hasAxesNames) {
  ////          axisX.setName("Axis X");
  ////          axisY.setName("Axis Y");
  ////        }
  ////        data.setAxisXBottom(axisX);
  ////        data.setAxisYLeft(axisY);
  ////
  ////        Bundle bundle = new Bundle();
  ////        bundle.putBoolean("hasChanged", true);
  ////        Message msg = new Message();
  ////        msg.setData(bundle);
  ////        handler.handleMessage(msg);
  ////      }
  ////      try {
  ////        Thread.sleep(3000);
  ////      } catch (InterruptedException e) {
  ////        e.printStackTrace();
  ////      }
  ////    }
  ////  }
  //});
}
