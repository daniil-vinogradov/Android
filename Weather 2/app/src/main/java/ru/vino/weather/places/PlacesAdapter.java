package ru.vino.weather.places;


import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.vino.weather.R;
import ru.vino.weather.model.CurrentConditionModel;
import ru.vino.weather.utils.GuiUtils;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PlaceViewHolder> implements ItemTouchHelperAdapter {

    private Set<Long> loadingPlaces;
    private List<CurrentConditionModel> data;

    private PlaceClickListener placeClickListener;
    private PlaceDeleteListener placeDeleteListener;

    public PlacesAdapter(List<CurrentConditionModel> data, Set<Long> loadingPlaces,
                         PlaceClickListener placeClickListener, PlaceDeleteListener placeDeleteListener) {
        this.data = data;
        this.loadingPlaces = loadingPlaces;
        this.placeClickListener = placeClickListener;
        this.placeDeleteListener = placeDeleteListener;
    }

    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new PlaceViewHolder(inflater.inflate(R.layout.item_place, parent, false));
    }

    @Override
    public void onBindViewHolder(PlaceViewHolder holder, int position) {
        CurrentConditionModel currentCondition = data.get(position);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            holder.cardView.setTransitionName("card" + String.valueOf(position));
        holder.cardView.setOnClickListener(view ->
                placeClickListener.onPlaceClickListener(view, currentCondition));

        holder.place.setText(currentCondition.getName());
        holder.area.setText(currentCondition.getArea());

        if (loadingPlaces != null && loadingPlaces.contains(currentCondition.getId())) {
            holder.progressBar.setVisibility(View.VISIBLE);
            return;
        } else holder.progressBar.setVisibility(View.GONE);

        Context context = holder.itemView.getContext();
        holder.temperature.setText(context.getString(R.string.temperature_current,
                currentCondition.getTemperature().getMetric().getValue()));
        holder.time.setText(getTime(currentCondition.getEpochTime()));
        holder.image.setImageDrawable(GuiUtils.getDrawable(currentCondition.getWeatherIcon(),
                holder.itemView.getContext()));

    }

    private String getTime(long timestamp) {
        Date date = new Date(timestamp * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm EEE", Locale.US);
        return sdf.format(date);
    }


    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void addNewPlace() {
        notifyItemInserted(data.size() - 1);
    }

    @Override
    public void onItemDismiss(int position) {
        CurrentConditionModel delete = data.get(position);
        data.remove(position);
        notifyItemRemoved(position);
        placeDeleteListener.onPlaceDeleteListener(delete, position);
    }


    public static class PlaceViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_view)
        FrameLayout cardView;
        @BindView(R.id.progress_bar)
        ProgressBar progressBar;
        @BindView(R.id.place)
        TextView place;
        @BindView(R.id.area)
        TextView area;
        @BindView(R.id.temperature)
        TextView temperature;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.image)
        ImageView image;

        public PlaceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface PlaceClickListener {
        void onPlaceClickListener(View view, CurrentConditionModel currentCondition);
    }

    public interface PlaceDeleteListener {
        void onPlaceDeleteListener(CurrentConditionModel currentCondition, int position);
    }

}
