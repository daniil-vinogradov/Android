package ru.vinogradov.example.cities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.vinogradov.example.R;
import ru.vinogradov.example.data.City;


public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.CityViewHolder> {

    List<City> cities = new ArrayList<>();

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false);
        return new CityViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        holder.name.setText(cities.get(position).getName());
        holder.country.setText(cities.get(position).getCountry());
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public void setData(List<City> cities) {
        this.cities = cities;
        notifyDataSetChanged();
    }

    class CityViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView country;

        public CityViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(android.R.id.text1);
            country = (TextView) itemView.findViewById(android.R.id.text2);

        }
    }

}
