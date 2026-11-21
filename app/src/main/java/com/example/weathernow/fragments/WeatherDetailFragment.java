package com.example.weathernow.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.weathernow.BookmarkManager;
import com.example.weathernow.MainActivity;
import com.example.weathernow.R;
import com.example.weathernow.model.Weather;
import com.example.weathernow.viewmodel.WeatherViewModel;
import com.example.weathernow.databinding.FragmentWeatherDetailBinding;

public class WeatherDetailFragment extends Fragment {
    private static final String TAG = "WeatherDetailFragment";
    private FragmentWeatherDetailBinding binding;
    private WeatherViewModel weatherViewModel;
    private BookmarkManager bookmarkManager;
    private String selectedCity;
    public static WeatherDetailFragment newInstance(
            String city, String region, String weather) {
        WeatherDetailFragment fragment = new WeatherDetailFragment();
        Bundle args = new Bundle();
        args.putString("city", city);
        args.putString("region", region);
        args.putString("weather", weather);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = FragmentWeatherDetailBinding.inflate(
                inflater, container, false);
        ((MainActivity) requireActivity()).setDetailMode(true);
        binding.weatherContent.setVisibility(View.GONE);
        Bundle args = getArguments();
        if (args != null) {
            selectedCity = args.getString("city", "");
            String region = args.getString("region", "");
            binding.detailCityName.setText(selectedCity);
            binding.detailCityRegion.setText(region);
        }
        bookmarkManager = new BookmarkManager(requireContext());
        updateBookmarkIcon();
        binding.backButton.setOnClickListener(view ->
                getParentFragmentManager().popBackStack());
        binding.bookmarkButton.setOnClickListener(view -> {
            if (selectedCity == null || selectedCity.isEmpty()) {
                return;
            }
            if (bookmarkManager.isBookmarked(selectedCity)) {
                bookmarkManager.removeBookmark(selectedCity);
                Toast.makeText(
                        requireContext(),
                        "Bookmark removed.",
                        Toast.LENGTH_SHORT
                ).show();
            } else {
                bookmarkManager.addBookmark(selectedCity);
                Toast.makeText(
                        requireContext(),
                        "City bookmarked!",
                        Toast.LENGTH_SHORT
                ).show();
            }
            updateBookmarkIcon();
        });
        return binding.getRoot();
    }
    private void updateBookmarkIcon() {
        if (selectedCity != null && bookmarkManager != null
                && bookmarkManager.isBookmarked(selectedCity)) {
            binding.bookmarkButton.setImageResource(
                    R.drawable.ic_bookmark_filled);
        } else {
            binding.bookmarkButton.setImageResource(
                    R.drawable.ic_bookmark_outline);
        }
    }
    private void checkMethodReachability() {
        Log.i(TAG, "method is reachable");
    }
    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkMethodReachability();
        weatherViewModel = new ViewModelProvider(this)
                .get(WeatherViewModel.class);
        weatherViewModel.getLoading().observe(
                getViewLifecycleOwner(), isLoading -> {
                    if (isLoading != null && isLoading) {
                        binding.loadingIndicator.setVisibility(View.VISIBLE);
                        binding.weatherContent.setVisibility(View.GONE);
                        binding.errorMessage.setVisibility(View.GONE);
                    } else {
                        binding.loadingIndicator.setVisibility(View.GONE);
                    }
                });
        weatherViewModel.getWeather().observe(
                getViewLifecycleOwner(), weather -> {
                    if (weather != null) {
                        displayWeather(weather);
                    }
                });
        weatherViewModel.getError().observe(
                getViewLifecycleOwner(), error -> {
                    if (error != null && !error.isEmpty()) {
                        binding.weatherContent.setVisibility(View.GONE);
                        binding.errorMessage.setText(error);
                        binding.errorMessage.setVisibility(View.VISIBLE);
                    }
                });
        Bundle args = getArguments();
        if (args != null) {
            String city = args.getString("city", "");
            if (!city.isEmpty()) {
                weatherViewModel.loadWeather(city);
            }
        }
    }
    private void displayWeather(Weather weather) {
        binding.detailCityName.setText(weather.getCityName());
        String region = weather.getRegion();
        String country = weather.getCountry();
        String location = region;
        if (location == null || location.isEmpty()) {
            location = country;
        } else if (country != null && !country.isEmpty()) {
            location = location + ", " + country;
        }
        binding.detailCityRegion.setText(location);
        binding.detailCondition.setText(weather.getCondition());
        binding.detailTemperatureC.setText(
                weather.getTemperatureC());
        binding.detailTemperatureF.setText(
                weather.getTemperatureF());
        binding.detailFeelsLike.setText(weather.getFeelsLike());
        binding.detailHumidity.setText(weather.getHumidity());
        binding.detailWind.setText(weather.getWindSpeed());
        binding.weatherContent.setVisibility(View.VISIBLE);
        binding.errorMessage.setVisibility(View.GONE);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((MainActivity) requireActivity()).setDetailMode(false);
        binding = null;
    }
}
