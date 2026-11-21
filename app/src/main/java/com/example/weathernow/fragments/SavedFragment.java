package com.example.weathernow.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.weathernow.BookmarkManager;
import com.example.weathernow.databinding.FragmentSavedBinding;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SavedFragment extends Fragment {
    private FragmentSavedBinding binding;
    private BookmarkManager bookmarkManager;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> savedCities;
    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = FragmentSavedBinding.inflate(
                inflater, container, false);
        bookmarkManager = new BookmarkManager(requireContext());
        savedCities = new ArrayList<>();
        adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                savedCities
        );
        binding.savedCitiesList.setAdapter(adapter);
        updateSavedCities();
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (binding != null) {
            updateSavedCities();
        }
    }

    private void updateSavedCities() {
        List<String> cities = new ArrayList<>(
                bookmarkManager.getBookmarks());
        Collections.sort(cities);
        savedCities.clear();
        savedCities.addAll(cities);
        adapter.notifyDataSetChanged();
        if (savedCities.isEmpty()) {
            binding.emptySavedLayout.setVisibility(View.VISIBLE);
            binding.savedCitiesList.setVisibility(View.GONE);
        } else {
            binding.emptySavedLayout.setVisibility(View.GONE);
            binding.savedCitiesList.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
