package com.example.billy.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherForecast extends Activity {
    private ProgressBar pb;
    protected static final String ACTIVITY_NAME = "LoginActivity";
    private String weatherUrlString = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";
    String windSpeed;
    String minTemp;
    String maxTemp;
    String temperature;
    Bitmap image;

    String iconName;
    String weatherIconUrlString;
    ImageView weatherPicture;

    TextView temperatureView;
    TextView minTemperature;
    TextView maxTemperature;
    TextView windSpeedTxt;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setVisibility(ProgressBar.VISIBLE);

        weatherPicture = (ImageView) findViewById(R.id.weatherImageView);
        temperatureView = (TextView) findViewById(R.id.temperatureView);
        maxTemperature = (TextView) findViewById(R.id.maxTemperature);
        minTemperature = (TextView) findViewById(R.id.minTemperature);
        windSpeedTxt = (TextView) findViewById(R.id.windSpeedView);

        weatherPicture = (ImageView) findViewById(R.id.weatherImageView);
     //   weatherPicture = (ImageView) findViewById(R.id.currentTemperature);

        ForecastQuery qry = new ForecastQuery();
        qry.execute();







    }


    private class ForecastQuery extends AsyncTask<String, Integer, String>{


        protected String doInBackground(String... args){
            try {

                URL weatherUrl = new URL(weatherUrlString);
                HttpURLConnection conn = (HttpURLConnection) weatherUrl.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                InputStream in = (InputStream) conn.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);

                XmlPullParser parser = factory.newPullParser();
                parser.setInput(in, "UTF-8");

                String value;
                while(parser.getEventType() !=XmlPullParser.END_DOCUMENT) {
                   // int currentTag = parser.getEventType();

                    switch (parser.getEventType()) {
                        case XmlPullParser.START_TAG:
                            String name = parser.getName();
                            if(name.equals("temperature")){
                                minTemp = parser.getAttributeValue(null, "min");

                                publishProgress(25);
                                maxTemp = parser.getAttributeValue(null, "max");
                                publishProgress(50);
                                temperature = parser.getAttributeValue(null, "value");
                                publishProgress( 75);

                            }else if(name.equals("speed")){

                                windSpeed = parser.getAttributeValue(null, "value");

                                publishProgress(90);
                            }else if(name.equals("weather")){
                                iconName = parser.getAttributeValue(null, "icon");
                            }
                            Log.i("read XML tag: ", name);
                            break;
                        case XmlPullParser.TEXT:
                            break;
                    }
                parser.next();

                }

                if(fileExistance(iconName + ".png")){
                    FileInputStream fis = null;
                    try {
                        fis = openFileInput(iconName + ".png");
                    }
                    catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    image = BitmapFactory.decodeStream(fis);
                    publishProgress(100);
                    Log.i("WeatherForecast", "Image file taken from local storage.");
                } else {
                    String ImageURL = "http://openweathermap.org/img/w/" + iconName + ".png";

                    HttpUtils HTTPUtils = new HttpUtils();
                    image = HTTPUtils.getImage(ImageURL);
                    FileOutputStream outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                    image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    publishProgress(100);
                    Log.i("WeatherForecast", "Image has been downloaded.");
                }


            }catch (Exception e){
                Log.i("Exception: ", e.getMessage());

            }
            return "";
        }
        public void onProgressUpdate(Integer ... args){
            pb.setProgress(args[0]);
            pb.setVisibility(View.VISIBLE);

        }

        public void onPostExecute(String result) {
            /*
            FileInputStream fis = null;
            if (fileExistance(iconName + ".png")) {
                try {
                    fis = openFileInput(iconName + ".png");

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                Bitmap image = BitmapFactory.decodeStream(fis);


                weatherPicture.setImageBitmap(image);
            }
            */
                weatherPicture.setImageBitmap(image);
                minTemperature.setText("Current minimum temperature: " + minTemp);
                maxTemperature.setText("Current maximum temperature: " + maxTemp);
                temperatureView.setText("Temperature: " + temperature);
                windSpeedTxt.setText("Current wind speed: " + windSpeed);
                pb.setVisibility(View.INVISIBLE);
/*
        TextView currentTemperature = (TextView) findViewById(R.id.currentTemperature);
        currentTemperature.setText(temperature);
 */
        }

    }
    /**
     * @author Terry E-mail: yaoxinghuo at 126 dot com
     * @version create: 2010-10-21 ??01:40:03
     */
    class HttpUtils {
        public Bitmap getImage(URL url) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
        public Bitmap getImage(String urlString) {
            try {
                URL url = new URL(urlString);
                return getImage(url);
            } catch (MalformedURLException e) {
                return null;
            }
        }

    }

    public boolean fileExistance(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }

    public void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    public void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onCreate()");
    }

    public void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    public void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }
    public void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy");
    }

}
