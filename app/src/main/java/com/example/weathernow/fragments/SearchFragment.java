package com.example.weathernow.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.weathernow.R;
import com.example.weathernow.databinding.FragmentSearchBinding;
import com.example.weathernow.databinding.ItemCityCardBinding;
import java.util.Locale;

public class SearchFragment extends Fragment {
    private FragmentSearchBinding binding;
    private final String[][] cities = {
            {"London", "City of London, Greater London 🇬🇧 United Kingdom", "Sunny 22°C"},
            {"Toronto", "Ontario 🇨🇦 Canada", "Cloudy 18°C"},
            {"Tokyo", "Tokyo-to 🇯🇵 Japan", "Clear 26°C"},
            {"Sydney", "New South Wales 🇦🇺 Australia", "Partly Cloudy 21°C"},
            {"New York", "New York State 🇺🇸 United States", "Rainy 16°C"}
    };
    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        binding = FragmentSearchBinding.inflate(
                inflater, container, false);

        displayCityCards(inflater, "");
        binding.searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {

                displayCityCards(inflater, s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        return binding.getRoot();
    }
    private void displayCityCards(
            LayoutInflater inflater, String searchText) {

        binding.cityList.removeAllViews();

        String query = searchText.trim().toLowerCase(Locale.ROOT);

        int matchingCities = 0;

        for (String[] city : cities) {

            String cityName = city[0].toLowerCase(Locale.ROOT);

            if (cityName.contains(query)) {

                matchingCities++;
                ItemCityCardBinding cardBinding =
                        ItemCityCardBinding.inflate(
                                inflater, binding.cityList, false);
                cardBinding.cityName.setText(city[0]);
                cardBinding.cityRegion.setText(city[1]);
                cardBinding.cityWeather.setText(city[2]);
                cardBinding.getRoot().setOnClickListener(view -> {
                    WeatherDetailFragment detailFragment =
                            WeatherDetailFragment.newInstance(
                                    city[0],
                                    city[1],
                                    city[2]
                            );
                    FragmentTransaction transaction =
                            getParentFragmentManager().beginTransaction();

                    transaction.hide(this);

                    transaction.add(
                            R.id.fragment_container,
                            detailFragment,
                            "WEATHER_DETAIL"
                    );

                    transaction.addToBackStack("weather_detail");
                    transaction.commit();
                });
                binding.cityList.addView(cardBinding.getRoot());
            }
        }
        if (matchingCities == 0) {

            TextView noResults = new TextView(requireContext());

            noResults.setText("No cities found.");
            noResults.setTextSize(16);
            noResults.setTextColor(
                    ContextCompat.getColor(
                            requireContext(),
                            R.color.wn_text_secondary
                    )
            );
            noResults.setGravity(android.view.Gravity.CENTER);
            noResults.setPadding(16, 32, 16, 32);

            binding.cityList.addView(noResults);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
