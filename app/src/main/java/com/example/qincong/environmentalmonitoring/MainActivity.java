package com.example.qincong.environmentalmonitoring;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View viewOne=(View)findViewById(R.id.one);
        ((TextView)((View)findViewById(R.id.two)).findViewById(R.id.title)).setText("湿度");
        ((TextView)((View)findViewById(R.id.three)).findViewById(R.id.title)).setText("氨气浓度");
        ((TextView)((View)findViewById(R.id.four)).findViewById(R.id.title)).setText("二氧化碳");
        ((TextView)((View)findViewById(R.id.five)).findViewById(R.id.title)).setText("关照强度");
        ((TextView)((View)findViewById(R.id.six)).findViewById(R.id.title)).setText("硫化氢浓度");
        viewOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,TemperatureChart.class);
                intent.putExtra("flag",1);
                startActivity(intent);
            }
        });
        ((View)findViewById(R.id.two)).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,TemperatureChart.class);
                intent.putExtra("flag",2);
                startActivity(intent);
            }
        });
        ((View)findViewById(R.id.three)).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,TemperatureChart.class);
                intent.putExtra("flag",3);
                startActivity(intent);
            }
        });
        ((View)findViewById(R.id.four)).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,TemperatureChart.class);
                intent.putExtra("flag",4);
                startActivity(intent);
            }
        });
        ((View)findViewById(R.id.five)).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,TemperatureChart.class);
                intent.putExtra("flag",5);
                startActivity(intent);
            }
        });
        ((View)findViewById(R.id.six)).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,TemperatureChart.class);
                intent.putExtra("flag",6);
                startActivity(intent);
            }
        });
    }
}
