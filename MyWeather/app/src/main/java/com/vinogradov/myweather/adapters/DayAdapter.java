package com.vinogradov.myweather.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vinogradov.myweather.R;
import com.vinogradov.myweather.database.WeatherForDay;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.realm.RealmList;

/**
 * @author d.vinogradov
 */
public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayViewHolder> {

    RealmList<WeatherForDay> days;
    Context context;

    public DayAdapter(RealmList<WeatherForDay> days) {
        this.days = days;
    }

    @Override
    public DayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_item, parent, false);
        context = parent.getContext();
        return new DayViewHolder(v, parent.getContext());
    }

    @Override
    public void onBindViewHolder(DayViewHolder holder, int position) {
        WeatherForDay weather = days.get(position);
        Calendar cal = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-M-d").parse(weather.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.setTime(date);
        holder.dateDay.setText(new SimpleDateFormat("EEE", Locale.ENGLISH).format(date).toUpperCase());
        holder.date.setText(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)) + "." + String.valueOf(cal.get(Calendar.MONTH)));
        holder.value.setText(weather.getValue().toUpperCase());
        holder.maxTemp.setText(weather.getMaxTempC() + "°");
        holder.minTemp.setText(weather.getMinTempC() + "°");
        holder.sunrise.setText(weather.getSunrise().replace(" ", ""));
        holder.sunset.setText(weather.getSunset().replace(" ", ""));

        int imageResource = context.getResources().getIdentifier("drawable/" + "w" + weather.getWeatherCode(), null, context.getPackageName());
        holder.wetherImg.setImageDrawable(context.getResources().getDrawable(imageResource));

    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public static class DayViewHolder extends RecyclerView.ViewHolder {
        TextView value;
        TextView dateDay;
        TextView date;
        TextView maxTemp;
        TextView minTemp;
        TextView sunrise;
        TextView sunset;
        ImageView wetherImg;

        DayViewHolder(View itemView, final Context context) {
            super(itemView);
            value = (TextView) itemView.findViewById(R.id.value);
            dateDay = (TextView) itemView.findViewById(R.id.dateDay);
            date = (TextView) itemView.findViewById(R.id.date);
            maxTemp = (TextView) itemView.findViewById(R.id.maxTemp);
            minTemp = (TextView) itemView.findViewById(R.id.minTemp);
            sunrise = (TextView) itemView.findViewById(R.id.sunrise);
            sunset = (TextView) itemView.findViewById(R.id.sunset);
            wetherImg = (ImageView) itemView.findViewById(R.id.wetherImg);
        }

    }

}
