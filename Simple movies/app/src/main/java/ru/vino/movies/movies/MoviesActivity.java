package ru.vino.movies.movies;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.vino.movies.MoviesApplication;
import ru.vino.movies.R;
import ru.vino.movies.base.PresenterProvider;
import ru.vino.movies.base.SaverActivity;
import ru.vino.movies.di.components.MoviesActivityComponent;

public class MoviesActivity extends SaverActivity<MoviesActivityComponent> implements
        PresenterProvider<MoviesContract.AbstractMoviesPresenter> {

    public static final String POPULAR = "popular";
    public static final String NOW_PLAYING = "now_playing";
    public static final String UPCOMING = "upcoming";

    @Inject
    Map<String, MoviesContract.AbstractMoviesPresenter> presenters;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movies);
        ButterKnife.bind(this);

        toolbar.setTitle(getString(R.string.title_movies));

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public MoviesActivityComponent setComponent() {
        return MoviesApplication.from(this).getComponent().createMoviesActivityComponent();
    }

    @Override
    public void onInject(MoviesActivityComponent component) {
        component.inject(this);
    }

    @Override
    public MoviesContract.AbstractMoviesPresenter getPresenter(String TAG) {
        return presenters.get(TAG);
    }

}
