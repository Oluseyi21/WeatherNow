package com.example.weathernow.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.weathernow.databinding.ItemCityCardBinding;
import com.example.weathernow.model.City;
import java.util.ArrayList;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {
    private ArrayList<City> cityList;
    private OnCityClickListener listener;
    public interface OnCityClickListener {
        void onCityClick(City city);
    }
    public CityAdapter(ArrayList<City> cityList, OnCityClickListener listener) {
        this.cityList = cityList;
        this.listener = listener;
    }
    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        ItemCityCardBinding binding = ItemCityCardBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new CityViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(
            @NonNull CityViewHolder holder, int position) {

        City city = cityList.get(position);

        holder.binding.cityName.setText(city.getName());
        holder.binding.cityRegion.setText(city.getRegion());
        holder.binding.cityWeather.setText(city.getWeather());
        holder.binding.cityWeatherIcon.setText("");
        holder.itemView.setOnClickListener(view -> {
            int currentPosition = holder.getBindingAdapterPosition();
            if (currentPosition != RecyclerView.NO_POSITION) {
                listener.onCityClick(cityList.get(currentPosition));
            }
        });
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }
    public static class CityViewHolder extends RecyclerView.ViewHolder {
        ItemCityCardBinding binding;
        public CityViewHolder(ItemCityCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public void updateList(ArrayList<City> filteredList) {
        cityList = filteredList;
        notifyDataSetChanged();
    }
}
