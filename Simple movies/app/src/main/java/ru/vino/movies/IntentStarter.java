package ru.vino.movies;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

import org.parceler.Parcels;

import java.util.List;

import ru.vino.movies.base.IPerson;
import ru.vino.movies.model.ShortMovieModel;
import ru.vino.movies.movieinfo.MovieInfoActivity;
import ru.vino.movies.movieinfo.PersonsListActivity;

public class IntentStarter {

    public static void openMovieInfoActivity(Context context, View poster,
                                             View card, ShortMovieModel movie) {
        Intent intent = new Intent(context, MovieInfoActivity.class);
        intent.putExtra(MovieInfoActivity.MOVIE, Parcels.wrap(movie));
        Pair<View, String> p1 = Pair.create(poster, "poster");
        //  Pair<View, String> p2 = Pair.create(card, "card");
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation((Activity) context, p1);
        context.startActivity(intent, options.toBundle());
    }

    public static void openPersonsListActivity(Context context, List<? extends IPerson> persons,
                                               int type, String movie) {
        Intent intent = new Intent(context, PersonsListActivity.class);
        intent.putExtra(PersonsListActivity.EXTRA_PERSONS, Parcels.wrap(persons));
        intent.putExtra(PersonsListActivity.EXTRA_TYPE, type);
        intent.putExtra(PersonsListActivity.EXTRA_MOVIE, movie);
        context.startActivity(intent);
    }

}
