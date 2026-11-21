package com.example.weathernow.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.weathernow.model.City;
import com.example.weathernow.adapter.CityAdapter;
import com.example.weathernow.R;
import com.example.weathernow.databinding.FragmentSearchBinding;
import java.util.ArrayList;
import java.util.Locale;

public class SearchFragment extends Fragment {
    private static final String TAG = "SearchFragment";
    private FragmentSearchBinding binding;
    private ArrayList<City> cities;
    private ArrayList<City> filteredCities;
    private CityAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        binding = FragmentSearchBinding.inflate(
                inflater, container, false);
        initSearchDiagnostics();
        cities = new ArrayList<>();
        cities.add(new City(
                "Toronto",
                "Ontario 🇨🇦 Canada",
                "Cloudy 18°C",
                43.6532,
                -79.3832
        ));
        cities.add(new City(
                "Montreal",
                "Quebec 🇨🇦 Canada",
                "Partly Cloudy 17°C",
                45.5019,
                -73.5674
        ));
        cities.add(new City(
                "London",
                "City of London, Greater London 🇬🇧 United Kingdom",
                "Sunny 22°C",
                51.5072,
                -0.1276
        ));
        cities.add(new City(
                "Tokyo",
                "Tokyo-to 🇯🇵 Japan",
                "Clear 26°C",
                35.6762,
                139.6503
        ));
        cities.add(new City(
                "Sydney",
                "New South Wales 🇦🇺 Australia",
                "Partly Cloudy 21°C",
                -33.8688,
                151.2093
        ));
        cities.add(new City(
                "New York",
                "New York State 🇺🇸 United States",
                "Rainy 16°C",
                40.7128,
                -74.0060
        ));
        filteredCities = new ArrayList<>(cities);
        adapter = new CityAdapter(filteredCities, city -> {

            WeatherDetailFragment detailFragment =
                    WeatherDetailFragment.newInstance(
                            city.getName(),
                            city.getRegion(),
                            city.getWeather()
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
        binding.cityRecyclerView.setLayoutManager(
                new LinearLayoutManager(requireContext()));
        binding.cityRecyclerView.setAdapter(adapter);
        binding.searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {

                filterCities(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        return binding.getRoot();
    }
    private void initSearchDiagnostics() {
        Log.i(TAG, "search diagnostics enabled");
    }
    private void filterCities(String searchText) {
        filteredCities.clear();
        String query = searchText.trim().toLowerCase(Locale.ROOT);
        for (City city : cities) {
            String cityName = city.getName().toLowerCase(Locale.ROOT);
            if (cityName.contains(query)) {
                filteredCities.add(city);
            }
        }
        adapter.updateList(new ArrayList<>(filteredCities));
        if (filteredCities.isEmpty()) {
            binding.noResultsText.setVisibility(View.VISIBLE);
        } else {
            binding.noResultsText.setVisibility(View.GONE);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}