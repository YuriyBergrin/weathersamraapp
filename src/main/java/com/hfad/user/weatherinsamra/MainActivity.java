package com.hfad.user.weatherinsamra;


import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;



import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    TextView textViewMainDay;
    TextView textViewMainDaySecond;
    TextView textViewMainTemperature;
    TextView textViewWind;
    TextView textViewPressure;
    TextView textViewRelwet;

    TextView textViewPeriod1;
    TextView textViewPeriod2;
    TextView textViewPeriod3;

    TextView textViewPeriod1t;
    TextView textViewPeriod2t;
    TextView textViewPeriod3t;

    private SwipeRefreshLayout mSwipeRefreshLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewMainDay = (TextView)findViewById(R.id.textViewMainDay);
        textViewMainDaySecond = (TextView)findViewById(R.id.textViewMainDaySecond);
        textViewMainTemperature = (TextView)findViewById(R.id.textViewMainTemperature);
        textViewWind = (TextView)findViewById(R.id.textViewWind);
        textViewPressure = (TextView)findViewById(R.id.textViewPressure);
        textViewRelwet = (TextView)findViewById(R.id.textViewRelwet);

        textViewPeriod1 = (TextView)findViewById(R.id.textViewPeriod1);
        textViewPeriod2 = (TextView)findViewById(R.id.textViewPeriod2);
        textViewPeriod3 = (TextView)findViewById(R.id.textViewPeriod3);

        textViewPeriod1t = (TextView)findViewById(R.id.textViewPeriod1t);
        textViewPeriod2t = (TextView)findViewById(R.id.textViewPeriod2t);
        textViewPeriod3t = (TextView)findViewById(R.id.textViewPeriod3t);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        DownloadData downloadData = new DownloadData();
        downloadData.execute("https://xml.meteoservice.ru/export/gismeteo/point/125.xml");



    }

    private class DownloadData extends AsyncTask<String, Void, String> {
        private static final String TAG = "DownloadData";

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            WeatherXmlParser parser = new WeatherXmlParser();
            if (s != null && parser.parse(s)) {

        textViewMainDay.setText(parser.getWeathers().get(0).getWeekday() + " " + parser.getWeathers().get(0).getDay() + " " + parser.getWeathers().get(0).getMonth());
        textViewMainDaySecond.setText(parser.getWeathers().get(0).getTod());
        textViewMainTemperature.setText(parser.getWeathers().get(0).getTemperatureMin() + "°");
        textViewWind.setText("Ветер: " + parser.getWeathers().get(0).getWindMax() + " м/с");
        textViewPressure.setText("Давление: " + parser.getWeathers().get(0).getPressure() + " мм рт. ст.");
        textViewRelwet.setText("Влажность: " + parser.getWeathers().get(0).getRelwet() + " %");

        textViewPeriod1.setText(parser.getWeathers().get(1).getTod());
        textViewPeriod2.setText(parser.getWeathers().get(2).getTod());
        textViewPeriod3.setText(parser.getWeathers().get(3).getTod());

        textViewPeriod1t.setText(parser.getWeathers().get(1).getTemperatureMax()+"° / " + parser.getWeathers().get(1).getTemperatureMin()+"° ");
        textViewPeriod2t.setText(parser.getWeathers().get(2).getTemperatureMax()+"° / " + parser.getWeathers().get(2).getTemperatureMin()+"° ");
        textViewPeriod3t.setText(parser.getWeathers().get(3).getTemperatureMax()+"° / " + parser.getWeathers().get(3).getTemperatureMin()+"° ");


            }
        }

        @Override
        protected String doInBackground(String... strings) {
            String content = "";
            try{
                content = downloadXML(strings[0]);
            }
            catch (IOException e){
                Log.e(TAG, "downloadXML: IO Exception reading data: " + e.getMessage());
            }
            return content;
        }

        private String downloadXML(String urlPath) throws IOException {
            StringBuilder xmlResult = new StringBuilder();
            BufferedReader reader = null;
            try {
                URL url = new URL(urlPath);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = null;
                while ((line=reader.readLine()) != null) {
                    xmlResult.append(line);
                }
                return xmlResult.toString();
            } catch(MalformedURLException e) {
                Log.e(TAG, "downloadXML: Invalid URL " + e.getMessage());
            } catch(IOException e) {
                Log.e(TAG, "downloadXML: IO Exception reading data: " + e.getMessage());
            } catch(SecurityException e) {
                Log.e(TAG, "downloadXML: Security Exception.  Needs permisson? " + e.getMessage());
            }
            finally {
                if (reader != null) {
                    reader.close();
                }
            }
            return null;
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Отменяем анимацию обновления
                mSwipeRefreshLayout.setRefreshing(false);

                DownloadData downloadData = new DownloadData();
                downloadData.execute("https://xml.meteoservice.ru/export/gismeteo/point/125.xml");


            }
        }, 2000);
    }
}

