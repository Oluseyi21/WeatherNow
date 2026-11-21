package com.example.weathernow.repository;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import com.example.weathernow.BuildConfig;
import com.example.weathernow.model.Weather;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherRepository {
    private final OkHttpClient client;
    private final Handler mainHandler;
    public interface WeatherCallback {
        void onSuccess(Weather weather);
        void onError(String message);
    }
    public WeatherRepository() {
        client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build();
        mainHandler = new Handler(Looper.getMainLooper());
    }
    public Call getWeather(String city, WeatherCallback callback) {
        String apiKey = BuildConfig.WEATHER_API_KEY;
        if (apiKey == null || apiKey.trim().isEmpty()) {
            callback.onError(
                    "Weather API key is missing. Please check your configuration.");
            return null;
        }
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api.weatherapi.com")
                .addPathSegment("v1")
                .addPathSegment("current.json")
                .addQueryParameter("key", apiKey)
                .addQueryParameter("q", city)
                .addQueryParameter("aqi", "no")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(
                    @NonNull Call call,
                    @NonNull IOException e) {
                if (!call.isCanceled()) {
                    mainHandler.post(() ->
                            callback.onError(
                                    "Unable to connect. Please check your internet connection."));
                }
            }
            @Override
            public void onResponse(
                    @NonNull Call call,
                    @NonNull Response response) throws IOException {
                try (Response responseBody = response) {
                    if (!responseBody.isSuccessful()) {
                        mainHandler.post(() ->
                                callback.onError(
                                        "Unable to retrieve weather information. Please try again."));
                        return;
                    }
                    if (responseBody.body() == null) {
                        mainHandler.post(() ->
                                callback.onError(
                                        "Empty response from weather service."));
                        return;
                    }
                    String jsonData = responseBody.body().string();
                    JSONObject jsonObject = new JSONObject(jsonData);
                    JSONObject location =
                            jsonObject.getJSONObject("location");
                    JSONObject current =
                            jsonObject.getJSONObject("current");
                    JSONObject condition =
                            current.getJSONObject("condition");
                    Weather weather = new Weather(
                            location.getString("name"),
                            location.getString("region"),
                            location.getString("country"),
                            condition.getString("text"),
                            current.getString("temp_c") + "°C",
                            current.getString("temp_f") + "°F",
                            current.getString("feelslike_c") + "°C",
                            current.getString("humidity") + "%",
                            current.getString("wind_kph") + " km/h"
                    );
                    if (!call.isCanceled()) {
                        mainHandler.post(() ->
                                callback.onSuccess(weather));
                    }
                } catch (JSONException e) {
                    if (!call.isCanceled()) {
                        mainHandler.post(() ->
                                callback.onError(
                                        "Unable to read weather information."));
                    }
                }
            }
        });
        return call;
    }
}
