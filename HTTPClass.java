package com.example.administrator.weathercity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by administrator on 7/12/16.
 */

public class HTTPClass
{

    public  StringBuffer getWeatherData(String city)
    {
        HttpURLConnection connection=null;
        InputStream inputStream=null;


        try {
            connection=(HttpURLConnection)(new URL(city)).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            StringBuffer stringBuffer=new StringBuffer();
            inputStream=connection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));





            String line=null;

            while ((line=bufferedReader.readLine())!=null)
            {
                stringBuffer.append(line+"\r\n");
            }

            inputStream.close();
            connection.disconnect();



            return stringBuffer;
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
