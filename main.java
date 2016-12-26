package model;

/**
 * Created by administrator on 7/12/16.
 */

public class main
{
    private int temp;
    private float humidity;
    private  int date;

    public int getDate()
    {
        return date;
    }

    public void setDate(int date)
    {
        this.date = date;
    }



    public int getPressure()
    {
        return pressure;
    }

    public void setPressure(int pressure)
    {
        this.pressure = pressure;
    }

    public int getTemp()
    {
        return temp;
    }

    public void setTemp(int temp)
    {
        this.temp = temp;
    }

    public float getHumidity()
    {
        return humidity;
    }

    public void setHumidity(float humidity)
    {
        this.humidity = humidity;
    }

    private int pressure;
}
