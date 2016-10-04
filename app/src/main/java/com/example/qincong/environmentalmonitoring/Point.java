package com.example.qincong.environmentalmonitoring;

/**
 * Created by qincong on 2016/10/2.
 */
public class Point {
    public String timestamp;

    public float value;

    public void setTimestamp(String timestamp){
        this.timestamp = timestamp;
    }
    public String getTimestamp(){
        return this.timestamp;
    }
    public void setValue(int value){
        this.value = value;
    }
    public float getValue(){
        return this.value;
    }

}
