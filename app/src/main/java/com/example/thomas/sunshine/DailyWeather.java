/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.thomas.sunshine;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Thomas
 */
public class DailyWeather
{
    private Date date;
    private String Weather;
    private double max;
    private double min;
    private String wind;
    private int humidity;
    private double pressure;

    public static boolean imperial = false;

    DailyWeather(Date date, String Weather, double max, double min)
    {
        setDate(date);
        setWeather(Weather);
        setMax(max);
        setMin(min);
    }

    DailyWeather(Date date, String Weather, double max, double min, int humidity, double pressure, String wind)
    {
        setDate(date);
        setWeather(Weather);
        setMax(max);
        setMin(min);
        setHumidity(humidity);
        setPressure(pressure);
        setWind(wind);
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getWeather() {
        return Weather;
    }

    public void setWeather(String Weather) {
        this.Weather = Weather;
    }

    public double getMax() {
        return max;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    private double convertToImperial(double temp)
    {
        return temp*1.8+32;
    }

    public void setMax(double max) {
        if(imperial)
        {
            max = convertToImperial(max);
        }
//            Log.v("Metric", imperial+"");
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        if(imperial)
            min = convertToImperial(min);
        this.min = min;
    }
    
    @Override
    public String toString()
    {
        DecimalFormat df = new DecimalFormat("#.00");
//        SimpleDateFormat sdf = new SimpleDateFormat("MMMM d',' yyyy");//October 6, 2015
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM d");//October 6

        char u;
        if(imperial)
            u='F';
        else
            u = 'C';
        return  sdf.format(date)+ ": " + Weather + " " + df.format(max) + u + "/" + df.format(min)+ u;
    }

    public String toExtendedString()
    {
        return toString() + "\nPressure: " + pressure + "psi\nHumidity "+ humidity+ "%\nWind "+ wind+"mph";
    }
}
