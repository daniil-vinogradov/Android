package ru.vino.weather.search;


import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.vino.weather.R;
import ru.vino.weather.model.responsemodels.AutocompleteResponseModel;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ResultViewHolder> {

    List<AutocompleteResponseModel> data;

    OnPlaceClickListener listener;

    public ResultsAdapter(OnPlaceClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_search, parent, false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultViewHolder holder, int position) {
        AutocompleteResponseModel result = data.get(position);
        holder.place.setText(result.getLocalizedName() + ", " + result.getAdministrativeArea().getLocalizedName() + ", "
                + result.getCountry().getLocalizedName());

        holder.itemView.setOnClickListener(v -> listener.onPlaceClick(result));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<AutocompleteResponseModel> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public static class ResultViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.place)
        TextView place;

        public ResultViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public interface OnPlaceClickListener {
        void onPlaceClick(AutocompleteResponseModel place);
    }

}
