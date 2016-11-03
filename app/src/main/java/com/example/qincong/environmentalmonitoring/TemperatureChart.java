package com.example.qincong.environmentalmonitoring;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import com.example.qincong.environmentalmonitoring.retrofit.RetrofitUtil;
import com.example.qincong.environmentalmonitoring.retrofit.Yeelink_wendu;
import com.example.qincong.environmentalmonitoring.retrofit.Yeelink_anqi;
import com.example.qincong.environmentalmonitoring.retrofit.Yeelink_co2;
import com.example.qincong.environmentalmonitoring.retrofit.Yeelink_guangzhao;
import com.example.qincong.environmentalmonitoring.retrofit.Yeelink_liuhuaqing;
import com.example.qincong.environmentalmonitoring.retrofit.Yeelink_shidu;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.google.gson.Gson;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.LineChartView;
import okhttp3.OkHttpClient;
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
    final Toolbar mToolbar = (Toolbar) findViewById(R.id.mToolbar);
    setSupportActionBar(mToolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    lineChartView = (LineChartView) findViewById(R.id.chart);
    final int flag = getIntent().getIntExtra("flag", 0);
    Action1<List<Point>> action1 = new Action1<List<Point>>() {
      @Override public void call(List<Point> points) {
        List<PointValue> listPoint = new ArrayList<PointValue>();
        for (Point point : points) {
          listPoint.add(new PointValue(points.indexOf(point), point.value));
        }
        Line line = new Line(listPoint);
        line.setColor(Color.parseColor("#33B5E5"));
        line.setShape(ValueShape.CIRCLE);
        line.setCubic(false);
        line.setFilled(true);
        line.setHasLines(true);
        line.setHasPoints(false);
        List<Line> lines = new ArrayList<Line>();
        lines.add(line);
        data = new LineChartData(lines);
        Axis axisX = new Axis();
        Axis axisY = new Axis().setHasLines(true);
        axisX.setName("时间");
        switch (flag) {
          case 0:
            axisY.setName("温度");
            mToolbar.setTitle("温度");
            break;
          case 1:
            axisY.setName("湿度");
            mToolbar.setTitle("湿度");
            break;
          case 2:
            axisY.setName("氨气浓度");
            mToolbar.setTitle("氨气浓度");
            break;
          case 3:
            axisY.setName("二氧化碳");
            mToolbar.setTitle("二氧化碳");
            break;
          case 4:
            axisY.setName("光照强度");
            mToolbar.setTitle("光照强度");
            break;
          case 5:
            axisY.setName("硫化氢浓度");
            mToolbar.setTitle("硫化氢浓度");
            break;
        }
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);
        lineChartView.setLineChartData(data);
      }
    };
    switch (flag + 1) {
      case 1:
        Log.w("number", "start");
        RetrofitUtil.createService(Yeelink_wendu.class)
            .listPoint()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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
  }
}
