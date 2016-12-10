package ru.vino.movies;


import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ru.vino.movies.model.GenreModel;

public class Utils {

    public static final int BIG_SIZE = 500;

    public static final SimpleDateFormat SOURCE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    public static final SimpleDateFormat VIEW_FORMAT = new SimpleDateFormat("d MMM yyyy", Locale.getDefault());

    public static String getPosterURL(String path) {
        return getPosterURL(path, 185);
    }

    public static String getPosterURL(String path, int size) {
        StringBuilder builder = new StringBuilder();
        builder
                .append("http://image.tmdb.org/t/p/w")
                .append(size)
                .append(path);
        Log.d("QWE", builder.toString());
        return builder.toString();
    }

    public static String getGenres(List<GenreModel> genres) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < genres.size(); i++) {
            if (!(i == genres.size() - 1)) {
                builder.append(genres.get(i).getName()).append(" | ");
            } else builder.append(genres.get(i).getName());
        }
        return builder.toString();
    }

    public static String formatDate(SimpleDateFormat sourcePattern, String source,
                                    SimpleDateFormat resultPattern) {
        Date parsedDate = new Date();
        try {
            parsedDate = sourcePattern.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return resultPattern.format(parsedDate);
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 130);
    }

}
