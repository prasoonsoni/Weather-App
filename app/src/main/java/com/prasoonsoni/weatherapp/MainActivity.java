package com.prasoonsoni.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    ConstraintLayout homeLayout;
    ConstraintLayout weatherLayout;
    Button searchAgainButton;
    ImageView cloudView;
    TextView descriptionTextView;
    ConstraintLayout cloudLayout;
    ImageView imageView;
    TextView whatsTheWeather;
    ImageView mountain;
    public void toFindWeather(View view) {

        //weatherLayout.setVisibility(View.VISIBLE);
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(cityName.getWindowToken(), 0);

        if(cityName.getText().toString().isEmpty()){
            Toast.makeText(this, "City name cannot be empty!!", Toast.LENGTH_SHORT).show();
        } else {
            RequestQueue requestQueue;
            requestQueue = Volley.newRequestQueue(this);
            String url = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName.getText().toString() + "&appid=cabf35d8abfa40931f2f56c92e0e1e14";
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                    url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String weatherInfo = response.getString("main");

                        JSONObject jsonPart = new JSONObject(weatherInfo);
                        String arr = response.getString("weather");
                        JSONArray jArr = new JSONArray(arr);
                        String windInfo = response.getString("wind");
                        JSONObject wind = new JSONObject(windInfo);
                        String cloudImageLink = "";
                        String description = "";
                        for (int i = 0;i<jArr.length();i++){
                            JSONObject forCloud = jArr.getJSONObject(i);
                            String icon = forCloud.getString("icon");
                            description = forCloud.getString("description");
                            cloudImageLink = "https://openweathermap.org/img/wn/"+icon+"@4x.png";
                        }


                        if (jsonPart.getString("temp") != "" && response.getString("visibility") != "" && jsonPart.getString("temp_max") != "" && jsonPart.getString("feels_like") != "" && jsonPart.getString("pressure") != "" && jsonPart.getString("humidity") != "") {
                            cityTemp.setText(Double.toString(Double.parseDouble(String.format("%.1f", Double.parseDouble(jsonPart.getString("temp")) - 273.15))) + "°C");
                            setCityName.setText(cityName.getText().toString().toUpperCase());
                            minTemp.setText("VISIBILITY: " + Double.toString(Double.parseDouble(String.format("%.1f", Double.parseDouble(response.getString("visibility")) / 1000))) + "km");
                            maxTemp.setText("WIND SPEED: " + Double.toString(Double.parseDouble(String.format("%.1f", Double.parseDouble(wind.getString("speed"))))) + "m/s");
                            feelsLike.setText("FEELS LIKE " + Double.toString(Double.parseDouble(String.format("%.1f", Double.parseDouble(jsonPart.getString("feels_like")) - 273.15))) + "°C");
                            pressure.setText("PRESSURE: " + jsonPart.getString("pressure") + "hPa");
                            humidity.setText("HUMIDITY: " + jsonPart.getString("humidity") + "%");
                            descriptionTextView.setText(description.toUpperCase());

                            Picasso.with(getApplicationContext()).load(cloudImageLink).into(cloudView);

                            cityTemp.animate().translationXBy(-1500f).setDuration(2100);
                            setCityName.animate().translationXBy(-1500f).setDuration(2000);
                            minTemp.animate().translationXBy(-1500f).setDuration(2300);
                            maxTemp.animate().translationXBy(-1500f).setDuration(2400);
                            feelsLike.animate().translationXBy(-1500f).setDuration(2200);
                            pressure.animate().translationXBy(-1500f).setDuration(2500);
                            humidity.animate().translationXBy(-1500f).setDuration(2600);
                            searchAgainButton.animate().translationXBy(-1500f).setDuration(2700);
                            cloudLayout.animate().alpha(1f).setDuration(1800);
                            homeLayout.animate().translationYBy(-3000f).setDuration(1400);
                            imageView.animate().translationYBy(-3000f).setDuration(1000);
                            mountain.animate().translationYBy(3000f).setDuration(1000);
                            whatsTheWeather.animate().translationYBy(-3000f).setDuration(1200);

                            findButton.setClickable(false);
                            searchAgainButton.setClickable(true);

                        } else {
                            Toast.makeText(getApplicationContext(), "Cannot Find Weather!!", Toast.LENGTH_SHORT).show();
                            //weatherLayout.setVisibility(View.INVISIBLE);

                        }


                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Cannot Find Weather!!", Toast.LENGTH_SHORT).show();
                        //weatherLayout.setVisibility(View.INVISIBLE);
                    }



                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "Cannot Find Weather!!", Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(jsonObjectRequest);
        }

    }

    public void searchAgain(View view) {
        cityName.setText("");
        cityTemp.animate().translationXBy(1500f).setDuration(2100);
        setCityName.animate().translationXBy(1500f).setDuration(2000);
        minTemp.animate().translationXBy(1500f).setDuration(2300);
        maxTemp.animate().translationXBy(1500f).setDuration(2400);
        feelsLike.animate().translationXBy(1500f).setDuration(2200);
        pressure.animate().translationXBy(1500f).setDuration(2500);
        humidity.animate().translationXBy(1500f).setDuration(2600);
        searchAgainButton.animate().translationXBy(1500f).setDuration(2700);
        homeLayout.animate().translationYBy(3000f).setDuration(1900);
        imageView.animate().translationYBy(3000f).setDuration(2300);
        mountain.animate().translationYBy(-3000f).setDuration(1900);
        whatsTheWeather.animate().translationYBy(3000f).setDuration(2100);
        findButton.setClickable(true);
        searchAgainButton.setClickable(false);
        cloudLayout.animate().alpha(0f).setDuration(1000);
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
        minTemp = findViewById(R.id.visibility);
        maxTemp = findViewById(R.id.windSpeed);
        pressure = findViewById(R.id.pressure);
        humidity = findViewById(R.id.humidity);
        homeLayout = findViewById(R.id.homeLayout);
        weatherLayout = findViewById(R.id.weatherLayout);
        cloudView = findViewById(R.id.cloudView);
        cloudLayout = findViewById(R.id.cloudLayout);
        imageView = findViewById(R.id.imageView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        whatsTheWeather = findViewById(R.id.whatsTheWeather);
        mountain = findViewById(R.id.mountains);
        homeLayout.animate().translationYBy(3000f).setDuration(1000);
        mountain.animate().translationYBy(-3000f).setDuration(1000);
        imageView.animate().translationYBy(3000f).setDuration(1400);
        whatsTheWeather.animate().translationYBy(3000f).setDuration(1200);
    }


}