package ru.vino.movies.movies;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ru.vino.movies.R;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private String[] titles;

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        titles = context.getResources().getStringArray(R.array.movie_titles);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return MoviesListFragment.newInstance(MoviesActivity.POPULAR);
            case 1:
                return MoviesListFragment.newInstance(MoviesActivity.NOW_PLAYING);
            case 2:
                return MoviesListFragment.newInstance(MoviesActivity.UPCOMING);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
