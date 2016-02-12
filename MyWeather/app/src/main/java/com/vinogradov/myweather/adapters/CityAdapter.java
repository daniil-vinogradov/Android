package com.vinogradov.myweather.adapters;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vinogradov.myweather.R;
import com.vinogradov.myweather.ui.MainActivity;
import com.vinogradov.myweather.ui.WeatherActivity;
import com.vinogradov.myweather.database.City;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * @author d.vinogradov
 */
public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> implements ItemTouchHelperAdapter {

    Realm realm;
    RealmResults<City> results;
    List<Bitmap> bitmaps;
    MainActivity context;

    public CityAdapter(MainActivity activity, Realm realm) {
        this.context = activity;
        this.realm = realm;
        results = realm.where(City.class).findAll();
        results.sort("name");
        bitmaps = new ArrayList<>();
        for (City city : results) {
            Bitmap image = BitmapFactory.decodeFile(city.getImage());
            bitmaps.add(image);
        }
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_card, parent, false);
        return new CityViewHolder(v, context);
    }

    @Override
    public void onBindViewHolder(final CityViewHolder holder, int position) {
        final City city = results.get(position);
        holder.name.setText(city.getName());
        holder.feelsLike.setText("Feels like: " + city.getFeelsLikeC() + "°");
        holder.temp.setText("Temperature: " + city.getTemp_C() + "°");
        holder.image.setImageBitmap(bitmaps.get(position));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(context, WeatherActivity.class);
                    intent.putExtra("coord", city.getCoord());
                    intent.putExtra("name", city.getName());
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(context, holder.image, "photo");
                    context.startActivity(intent, options.toBundle());


            }
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void swap() {
        bitmaps.clear();
        for (City city : results) {
            bitmaps.add(BitmapFactory.decodeFile(city.getImage()));
        }
        notifyDataSetChanged();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }

    @Override
    public void onItemDismiss(final int position) {
        final Bitmap deletedBitmap = Bitmap.createBitmap(bitmaps.remove(position));
        final City deletedCity = new City( results.get(position));
        realm.beginTransaction();
        results.get(position).removeFromRealm();
        realm.commitTransaction();
        notifyItemRemoved(position);
        Snackbar snackbar = Snackbar
                .make(context.coordinatorLayout, "City is deleted!", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        realm.beginTransaction();
                        realm.copyToRealm(deletedCity);
                        realm.commitTransaction();
                        results.sort("name");
                        bitmaps.add(position, deletedBitmap);
                        notifyItemInserted(position);
                    }
                });
        snackbar.show();
    }

    public static class CityViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {

        final ImageView image;
        TextView name;
        TextView feelsLike;
        TextView temp;
        CardView cardView;

        CityViewHolder(View itemView, final Context context) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.cityImage);
            name = (TextView) itemView.findViewById(R.id.city);
            feelsLike = (TextView) itemView.findViewById(R.id.feelsLike);
            temp = (TextView) itemView.findViewById(R.id.temp);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

}
