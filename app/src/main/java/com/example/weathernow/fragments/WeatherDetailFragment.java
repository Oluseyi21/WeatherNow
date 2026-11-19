package com.example.weathernow.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.weathernow.MainActivity;
import com.example.weathernow.R;
import com.example.weathernow.databinding.FragmentWeatherDetailBinding;
import java.util.Locale;
public class WeatherDetailFragment extends Fragment {
    private FragmentWeatherDetailBinding binding;
    private boolean isBookmarked = false;
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
        // Hide the main toolbar and bottom navigation
        ((MainActivity) requireActivity()).setDetailMode(true);
        Bundle args = getArguments();
        if (args != null) {
            String city = args.getString("city", "");
            String region = args.getString("region", "");
            String weather = args.getString("weather", "Sunny 22°C");
            binding.detailCityName.setText(city);
            binding.detailCityRegion.setText(region);
            // Separate the weather condition and temperature
            int temperatureC = 22;
            String condition = weather;
            int lastSpace = weather.lastIndexOf(' ');
            if (lastSpace > 0) {
                String temperatureText =
                        weather.substring(lastSpace + 1);
                if (temperatureText.endsWith("°C")) {
                    try {
                        temperatureC = Integer.parseInt(
                                temperatureText.replace("°C", "")
                        );

                        condition = weather.substring(0, lastSpace);

                    } catch (NumberFormatException e) {
                        // Keep the default temperature if parsing fails
                    }
                }
            }
            binding.detailCondition.setText(condition);
            // Display Celsius temperature
            binding.detailTemperatureC.setText(
                    temperatureC + "°C"
            );
            // Convert Celsius to Fahrenheit
            double temperatureF =
                    (temperatureC * 9.0 / 5.0) + 32;

            binding.detailTemperatureF.setText(
                    String.format(Locale.US, "%.1f°F", temperatureF)
            );
            // Display a sample feels-like temperature
            binding.detailFeelsLike.setText(
                    (temperatureC + 2) + "°C"
            );
        }
        // Back button
        binding.backButton.setOnClickListener(view -> {

            getParentFragmentManager().popBackStack();
        });
        // Bookmark button
        binding.bookmarkButton.setOnClickListener(view -> {
            isBookmarked = !isBookmarked;
            if (isBookmarked) {
                binding.bookmarkButton.setImageResource(
                        R.drawable.ic_bookmark_filled
                );
                Toast.makeText(
                        requireContext(),
                        "City bookmarked!",
                        Toast.LENGTH_SHORT
                ).show();
            } else {
                binding.bookmarkButton.setImageResource(
                        R.drawable.ic_bookmark_outline
                );
                Toast.makeText(
                        requireContext(),
                        "Bookmark removed.",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
        return binding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Restore the main toolbar and bottom navigation
        ((MainActivity) requireActivity()).setDetailMode(false);
        binding = null;
    }
}
