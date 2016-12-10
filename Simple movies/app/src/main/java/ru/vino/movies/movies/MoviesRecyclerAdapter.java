package ru.vino.movies.movies;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.vino.movies.R;
import ru.vino.movies.Utils;
import ru.vino.movies.model.ShortMovieModel;


public class MoviesRecyclerAdapter extends RecyclerView.Adapter {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    OnMovieClickListener listener;

    List<ShortMovieModel> movies = new ArrayList<>();


    public MoviesRecyclerAdapter(OnMovieClickListener listener) {
        this.listener = listener;
    }

    public MoviesRecyclerAdapter() {
        setHasStableIds(true);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ITEM:
                return new MovieViewHolder(inflater.inflate(R.layout.item_movie, parent, false));
            case LOADING:
                return new LoadingViewHolder(inflater.inflate(R.layout.item_loading, parent, false));
            default:
                return null;

        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MovieViewHolder) {

            Context context = holder.itemView.getContext();

            MovieViewHolder movieViewHolder = (MovieViewHolder) holder;

            ShortMovieModel movie = movies.get(position);
            movieViewHolder.title.setText(movie.getTitle());
            movieViewHolder.date.setText(
                    Utils.formatDate(Utils.SOURCE_FORMAT, movie.getReleaseDate(), Utils.VIEW_FORMAT));

            StringBuilder url = new StringBuilder();
            url.append("http://image.tmdb.org/t/p/w185").append(movie.getPosterPath());

            Glide
                    .with(context)
                    .load(url.toString())
                    .placeholder(new ColorDrawable(context.getResources().getColor(R.color.colorBackground)))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .dontTransform()
                    .into(movieViewHolder.poster);

            holder.itemView.setOnClickListener(v ->
                    listener.OnMovieClick(movieViewHolder.poster, movieViewHolder.itemView, movie));
        }

    }

    @Override
    public long getItemId(int position) {
        return movies.get(position).getId();
    }

    @Override
    public int getItemViewType(int position) {
        if (movies.get(position) == null)
            return LOADING;
        else return ITEM;
    }

    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size();
    }

    public void addMovies(List<ShortMovieModel> data) {
        int startPos = movies.size();
        if (!movies.isEmpty())
            if (movies.get(movies.size() - 1) == null) {
                movies.remove(movies.size() - 1);
                notifyItemRemoved(movies.size());
                startPos = movies.size();
            }
        movies.addAll(data);
        notifyItemRangeInserted(startPos, data.size() - 1);
    }

    public void setLoading() {
        movies.add(null);
        notifyItemInserted(movies.size() - 1);
    }

    public boolean isLoading() {
        return !(movies == null || movies.get(movies.size() - 1) != null);
    }

    public List<ShortMovieModel> getMovies() {
        return movies;
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.poster)
        ImageView poster;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.date)
        TextView date;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {

        public LoadingViewHolder(View itemView) {
            super(itemView);

        }

    }

    public interface OnMovieClickListener {
        void OnMovieClick(View poster, View card, ShortMovieModel movie);
    }

}
