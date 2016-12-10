package ru.vino.movies.movieinfo;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.AutoTransition;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.parceler.Parcels;

import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.vino.movies.IntentStarter;
import ru.vino.movies.MoviesApplication;
import ru.vino.movies.R;
import ru.vino.movies.Utils;
import ru.vino.movies.base.IPerson;
import ru.vino.movies.base.SaverActivity;
import ru.vino.movies.base.TransitionListenerAdapter;
import ru.vino.movies.di.components.MovieInfoActivityComponent;
import ru.vino.movies.model.CreditsModel;
import ru.vino.movies.model.FullMovieModel;
import ru.vino.movies.model.ShortMovieModel;

public class MovieInfoActivity extends SaverActivity<MovieInfoActivityComponent>
        implements MovieInfoContract.IMovieInfoView {

    public static final String MOVIE = "ru.vino.movies.movieinfo.MOVIE";
    ShortMovieModel movie;

    @Inject
    MovieInfoContract.AbstractMovieInfoPresenter presenter;

    PersonRecyclerAdapter castAdapter;
    PersonRecyclerAdapter crewAdapter;

    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.poster)
    ImageView poster;
    @BindView(R.id.story)
    TextView story;
    @BindView(R.id.header_image)
    ImageView headerImage;
    @BindView(R.id.popularity)
    TextView popularity;
    @BindView(R.id.votes)
    TextView votes;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.transition_container)
    ViewGroup transitionContainer;
    @BindView(R.id.movie_full_info)
    ViewGroup movieFullInfo;
    @BindView(R.id.genre)
    TextView genre;
    @BindView(R.id.runtime)
    TextView runtime;
    @BindView(R.id.release)
    TextView release;
    @BindView(R.id.budget)
    TextView budget;
    @BindView(R.id.language)
    TextView language;
    @BindView(R.id.recycler_view_cast)
    RecyclerView castRecyclerView;
    @BindView(R.id.recycler_view_crew)
    RecyclerView crewRecyclerView;
    @BindView(R.id.show_cast)
    Button showCast;
    @BindView(R.id.show_crew)
    Button showCrew;

    int maxZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);
        ButterKnife.bind(this);

        movie = Parcels.unwrap(getIntent().getParcelableExtra(MOVIE));

        ActivityCompat.postponeEnterTransition(this);
        loadImage(Utils.getPosterURL(movie.getPosterPath()))
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        ActivityCompat.startPostponedEnterTransition(MovieInfoActivity.this);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        ActivityCompat.startPostponedEnterTransition(MovieInfoActivity.this);
                        return false;
                    }
                })
                .into(poster);
        loadImage(Utils.getPosterURL(movie.getBackdropPath(), Utils.BIG_SIZE))
                .crossFade()
                .into(headerImage);

        toolbar.setTitle(movie.getTitle());
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        story.setText(movie.getOverview());
        popularity.setText(getString(R.string.movie_popularity, movie.getVoteAverage()).replace(",", "."));
        votes.setText(getString(R.string.movie_votes, movie.getVoteCount()));

        maxZ = getResources().getDimensionPixelSize(R.dimen.posterZ);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            appBarLayout.addOnOffsetChangedListener((appBar, verticalOffset) -> {
                float scrollRange = appBarLayout.getTotalScrollRange();
                float percent = (scrollRange + verticalOffset) / scrollRange;
                Log.d("DELTA", String.valueOf(percent));
                animatePosterZ(percent);
            });

        story.setOnClickListener(v -> cycleTextViewExpansion());

        castAdapter = new PersonRecyclerAdapter(true);
        castRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        castRecyclerView.setAdapter(castAdapter);
        castRecyclerView.setNestedScrollingEnabled(false);

        crewAdapter = new PersonRecyclerAdapter(true);
        crewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        crewRecyclerView.setAdapter(crewAdapter);
        crewRecyclerView.setNestedScrollingEnabled(false);

        presenter.attachView(this);

        if (savedInstanceState == null &&
                android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Transition sharedElementEnterTransition = getWindow().getSharedElementEnterTransition();
            sharedElementEnterTransition.addListener(new TransitionListenerAdapter() {
                @Override
                public void onTransitionEnd(Transition transition) {
                    super.onTransitionEnd(transition);
                    presenter.getMovieInfo(movie.getId());
                }
            });
        } else presenter.getMovieInfo(movie.getId());

    }

    @Override
    public MovieInfoActivityComponent setComponent() {
        return MoviesApplication.from(this).getComponent().createMovieInfoActivityComponent();
    }

    @Override
    public void onInject(MovieInfoActivityComponent component) {
        component.inject(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void animatePosterZ(float percent) {
        poster.animate()
                .z(maxZ * percent)
                .setDuration(0)
                .start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    private void cycleTextViewExpansion() {
        Log.d("QWE", String.valueOf(story.getLineCount()));
        int collapsedMaxLines = 5;
        int currLines = story.getLineCount();
        if (currLines == collapsedMaxLines)
            story.setMaxLines(8);
        else story.setMaxLines(collapsedMaxLines);
//        story.setEllipsize(null);
//        ObjectAnimator animation = ObjectAnimator.ofInt(story, "maxLines",
//                Integer.MAX_VALUE);
//        animation.setDuration(400).start();
    }

    private DrawableRequestBuilder<String> loadImage(String url) {
        return Glide
                .with(this)
                .load(url)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontTransform()
                .dontAnimate();
    }

    @Override
    public void presentMovieInfo(FullMovieModel movie) {

        progressBar.setVisibility(View.GONE);
        movieFullInfo.setVisibility(View.INVISIBLE);

        genre.setText(Utils.getGenres(movie.getGenres()));
        runtime.setText(getString(R.string.movie_runtime, movie.getRuntime()));
        release.setText(
                Utils.formatDate(Utils.SOURCE_FORMAT, movie.getReleaseDate(), Utils.VIEW_FORMAT));
        budget.setText(String.format(Locale.getDefault(), "$ %,d", movie.getBudget()).replace(",", " "));
        language.setText(new Locale(movie.getOriginalLanguage()).getDisplayName());

        CreditsModel credits = movie.getCredits();

        castAdapter.presentData(credits.getCast());
        crewAdapter.presentData(credits.getCrew());

        showCast.setOnClickListener(v -> IntentStarter.openPersonsListActivity(this,
                credits.getCast(), IPerson.TYPE_CAST, movie.getOriginalTitle()));
        showCrew.setOnClickListener(v -> IntentStarter.openPersonsListActivity(this,
                credits.getCrew(), IPerson.TYPE_CREW, movie.getOriginalTitle()));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(transitionContainer, new AutoTransition());
        }
        movieFullInfo.setVisibility(View.VISIBLE);

    }

    @Override
    public void showProgress() {
        movieFullInfo.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {

    }
}
