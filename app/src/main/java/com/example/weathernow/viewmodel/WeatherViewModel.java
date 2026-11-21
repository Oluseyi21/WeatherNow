package com.example.weathernow.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.weathernow.model.Weather;
import com.example.weathernow.repository.WeatherRepository;
import okhttp3.Call;

public class WeatherViewModel extends ViewModel {
    private final WeatherRepository repository;
    private final MutableLiveData<Weather> weather =
            new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading =
            new MutableLiveData<>(false);
    private final MutableLiveData<String> error =
            new MutableLiveData<>();
    private Call currentCall;
    public WeatherViewModel() {
        repository = new WeatherRepository();
    }
    public LiveData<Weather> getWeather() {
        return weather;
    }
    public LiveData<Boolean> getLoading() {
        return loading;
    }
    public LiveData<String> getError() {
        return error;
    }
    public void loadWeather(String city) {
        if (currentCall != null) {
            currentCall.cancel();
        }
        loading.setValue(true);
        error.setValue(null);
        currentCall = repository.getWeather(
                city,
                new WeatherRepository.WeatherCallback() {
                    @Override
                    public void onSuccess(Weather result) {
                        weather.setValue(result);
                        loading.setValue(false);
                        currentCall = null;
                    }
                    @Override
                    public void onError(String message) {
                        error.setValue(message);
                        loading.setValue(false);
                        currentCall = null;
                    }
                }
        );
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        if (currentCall != null) {
            currentCall.cancel();
            currentCall = null;
        }
    }
}
