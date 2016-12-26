package com.example.administrator.weathercity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import model.Util;
import model.weather;

/**
 * Created by administrator on 8/12/16.
 */

public class JSONClass
{
    public  static weather getWeather(String data) {
        weather weather = new weather();

        try {
            JSONObject jsonObject=new JSONObject(data);

            JSONArray jsonArray=jsonObject.getJSONArray("weather");
            JSONObject jsonWeather=jsonArray.getJSONObject(0);
            weather.coord.setDescription(Util.getString("description",jsonWeather));
            weather.coord.setIcon(Util.getString("icon",jsonWeather));


            JSONObject coordObj= Util.getObject("coord",jsonObject);
            weather.coord.setLat(Util.getFloat("lat",coordObj));
            weather.coord.setLon(Util.getFloat("lon",coordObj));


            JSONObject mainObj=Util.getObject("main",jsonObject);
            weather.main.setHumidity(Util.getInt("humidity",mainObj));
            weather.main.setPressure(Util.getInt("pressure",mainObj));
            weather.main.setTemp(Util.getInt("temp",mainObj));

            weather.main.setDate(Util.getInt("dt",jsonObject));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weather;


    }
}
