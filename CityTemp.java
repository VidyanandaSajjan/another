package com.example.administrator.weathercity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.weather;



public class CityTemp extends AppCompatActivity

{
    int from_Where_I_Am_Coming = 0;
    private DBHelper mydb ;
    String city ;
    int id_To_Update = 0;
    TextView tcity;
    TextView ttemp;
    TextView tdate;
    TextView tdescr;
    ImageView imgvw;

    model.weather weather=new weather();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_temp);

        tcity=(TextView)findViewById(R.id.city_textView);
        ttemp=(TextView)findViewById(R.id.temp_textView);
        tdate=(TextView)findViewById(R.id.date_textView);
        tdescr=(TextView)findViewById(R.id.desc_textView);
        imgvw =(ImageView)findViewById(R.id.imageView);

        city=getIntent().getStringExtra("keyName");

    if(city!=null)
      {
    String URL = "http://api.openweathermap.org/data/2.5/weather?q=simla,in&appid=0f3357f1766089f012e21ea025feb45e";
    URL = URL.replaceAll("simla", String.valueOf(city));
    new ExecuteTask().execute(URL);

        }




        Bundle extras = getIntent().getExtras();


//database connection
        mydb = new DBHelper(this);

        if(extras !=null)
        {
            int Value = extras.getInt("id");

            if(Value>0)
            {
                //means this is the view part not the add contact part.

                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();


                String cty = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_CITY));
                String tmp = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_TEMP));
                String dt = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_DATE));
                String dsr = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_DESCRIPTION));


                if (!rs.isClosed())  {
                    rs.close();
                }
                Button b = (Button)findViewById(R.id.savedata);
                b.setVisibility(View.INVISIBLE);

                tcity.setText( cty);
                tcity.setFocusable(false);
                tcity.setClickable(false);

                ttemp.setText(tmp);
                ttemp.setFocusable(false);
                ttemp.setClickable(false);

                tdate.setText(dt);
                tdate.setFocusable(false);
                tdate.setClickable(false);

                tdescr.setText(dsr);
                tdescr.setFocusable(false);
                tdescr.setClickable(false);


            }
        }
    }

//end database connection


    class ExecuteTask extends AsyncTask<String,Integer, weather>
    {

        @Override
        protected weather doInBackground(String... params)
        {

            StringBuffer res=new HTTPClass().getWeatherData(params[0]);

            String d = res.toString();

            weather= JSONClass.getWeather(d);

            return weather;
        }

        protected void onPostExecute(weather w)
        {

            super.onPostExecute(w);

            tcity.setText(city);

            int i = w.main.getTemp();
            int temp = (i-273);
            ttemp.setText(Integer.toString(temp)+(char) 0x00B0+"C");


            int seconds=w.main.getDate();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formatter.format(new Date(seconds * 1000L));
            tdate.setText(dateString);

            tdescr.setText(w.coord.getDescription());

            String imageview=w.coord.getDescription();


            if(imageview.equals("clear sky"))
            {
                imgvw.setImageResource(R.drawable.hot);
            }
            else if(imageview.equals("few clouds"))
            {
                imgvw.setImageResource(R.drawable.normal);
            }
            else if(imageview.equals("smoke"))
            {
                imgvw.setImageResource(R.drawable.humid);
            }
            else if(imageview.equals("haze"))
            {
                imgvw.setImageResource(R.drawable.rain);
            }
            else if(imageview.equals("few clouds"))
            {
                imgvw.setImageResource(R.drawable.humid);
            }
            else
            {
                imgvw.setImageResource(R.drawable.normal);
            }

        }

    }



    //store database button

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void run(View view)
    {
        Bundle extras = getIntent().getExtras();

        if(extras !=null)
        {
            int Value = extras.getInt("id");

            if(Value>0)
            {
                if(mydb.updateContact(id_To_Update,tcity.getText().toString(), ttemp.getText().toString(), tdate.getText().toString(), tdescr.getText().toString()))
                {
                    Toast.makeText(CityTemp.this, "Updated", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(CityTemp.this,MainActivity.class);

                    startActivity(intent);
                }

                else
                {
                    Toast.makeText(CityTemp.this, "not Updated", Toast.LENGTH_SHORT).show();
                }

            }
            else
            {
                if(mydb.insertContact(tcity.getText().toString(), ttemp.getText().toString(), tdate.getText().toString(), tdescr.getText().toString()))
                {
                    Toast.makeText(CityTemp.this, "done",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(CityTemp.this, "not done",Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(CityTemp.this,MainActivity.class);
                startActivity(intent);
            }
        }

        String body=city.toString().trim();
        String des=tdescr.getText().toString().trim();

        NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify=new Notification.Builder(getApplicationContext()).setContentTitle("weather").setContentText(des).
                setContentTitle(body).setSmallIcon(R.drawable.img).build();
        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        notif.notify(0, notify);
    }

}


