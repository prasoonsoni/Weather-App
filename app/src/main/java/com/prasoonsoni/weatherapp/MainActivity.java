package com.prasoonsoni.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    EditText cityName;
    Button findButton;
    TextView setCityName;
    TextView cityTemp;
    TextView feelsLike;
    TextView minTemp;
    TextView maxTemp;
    TextView pressure;
    TextView humidity;
    LinearLayout homeLayout;
    ConstraintLayout weatherLayout;
    Button searchAgainButton;

    public void toFindWeather(View view) {

        //weatherLayout.setVisibility(View.VISIBLE);
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(cityName.getWindowToken(), 0);
        DownloadTask task = new DownloadTask();
        task.execute("https://api.openweathermap.org/data/2.5/weather?q=" + cityName.getText().toString() + "&appid=cabf35d8abfa40931f2f56c92e0e1e14");

    }

    public void searchAgain(View view) {
        cityName.setText("");
        cityTemp.animate().translationXBy(1500f).setDuration(3100);
        setCityName.animate().translationXBy(1500f).setDuration(3000);
        minTemp.animate().translationXBy(1500f).setDuration(3300);
        maxTemp.animate().translationXBy(1500f).setDuration(3400);
        feelsLike.animate().translationXBy(1500f).setDuration(3200);
        pressure.animate().translationXBy(1500f).setDuration(3500);
        humidity.animate().translationXBy(1500f).setDuration(3600);
        searchAgainButton.animate().translationXBy(1500f).setDuration(3700);
        homeLayout.animate().translationYBy(2000f).setDuration(5000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchAgainButton = findViewById(R.id.searchAgainButton);
        cityName = findViewById(R.id.cityName);
        findButton = findViewById(R.id.findButton);
        setCityName = findViewById(R.id.setCityName);
        cityTemp = findViewById(R.id.cityTemp);
        feelsLike = findViewById(R.id.feelsLike);
        minTemp = findViewById(R.id.minTemp);
        maxTemp = findViewById(R.id.maxTemp);
        pressure = findViewById(R.id.pressure);
        humidity = findViewById(R.id.humidity);
        homeLayout = findViewById(R.id.homeLayout);
        weatherLayout = findViewById(R.id.weatherLayout);
        homeLayout.animate().translationYBy(2000f).setDuration(2000);
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                return result;
            } catch (Exception e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                String weatherInfo = jsonObject.getString("main");

                JSONObject jsonPart = new JSONObject(weatherInfo);

                if (jsonPart.getString("temp") != "" && jsonPart.getString("temp_min") != "" && jsonPart.getString("temp_max") != "" && jsonPart.getString("feels_like") != "" && jsonPart.getString("pressure") != "" && jsonPart.getString("humidity") != "") {
                    cityTemp.setText(Double.toString(Double.parseDouble(String.format("%.2f", Double.parseDouble(jsonPart.getString("temp")) - 273.15))) + "°C");
                    setCityName.setText(cityName.getText().toString().toUpperCase());
                    minTemp.setText("MINIMUM TEMPERATURE: " + Double.toString(Double.parseDouble(String.format("%.2f", Double.parseDouble(jsonPart.getString("temp_min")) - 273.15))) + "°C");
                    maxTemp.setText("MAXIMUM TEMPERATURE: " + Double.toString(Double.parseDouble(String.format("%.2f", Double.parseDouble(jsonPart.getString("temp_max")) - 273.15))) + "°C");
                    feelsLike.setText("FEELS LIKE " + Double.toString(Double.parseDouble(String.format("%.2f", Double.parseDouble(jsonPart.getString("feels_like")) - 273.15))) + "°C");
                    pressure.setText("PRESSURE: " + jsonPart.getString("pressure") + "hPa");
                    humidity.setText("HUMIDITY: " + jsonPart.getString("humidity") + "%");
                    cityTemp.animate().translationXBy(-1500f).setDuration(3100);
                    setCityName.animate().translationXBy(-1500f).setDuration(3000);
                    minTemp.animate().translationXBy(-1500f).setDuration(3300);
                    maxTemp.animate().translationXBy(-1500f).setDuration(3400);
                    feelsLike.animate().translationXBy(-1500f).setDuration(3200);
                    pressure.animate().translationXBy(-1500f).setDuration(3500);
                    humidity.animate().translationXBy(-1500f).setDuration(3600);
                    searchAgainButton.animate().translationXBy(-1500f).setDuration(3700);
                    homeLayout.animate().translationYBy(-2000f).setDuration(2000);
                } else {
                    Toast.makeText(getApplicationContext(), "Cannot Find Weather!!", Toast.LENGTH_SHORT).show();
                    //weatherLayout.setVisibility(View.INVISIBLE);

                }


            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Cannot Find Weather!!", Toast.LENGTH_SHORT).show();
                //weatherLayout.setVisibility(View.INVISIBLE);
            }

        }
    }
}