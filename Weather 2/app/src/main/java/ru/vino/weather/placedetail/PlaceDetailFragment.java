package ru.vino.weather.placedetail;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.transitionseverywhere.Fade;
import com.transitionseverywhere.Recolor;

import com.transitionseverywhere.TransitionManager;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.vino.weather.R;
import ru.vino.weather.WeatherApplication;
import ru.vino.weather.model.CurrentConditionModel;
import ru.vino.weather.model.DailyForecastModel;
import ru.vino.weather.utils.GuiUtils;


public class PlaceDetailFragment extends Fragment implements PlaceDetailContract.IPlaceDetailView {

    @Inject
    PlaceDetailContract.AbstractPlaceDetailPresenter presenter;
    PlaceDetailViewState viewState;

    CurrentConditionModel currentCondition;

    Unbinder unbinder;
    @BindView(R.id.days)
    LinearLayout days;
    @BindView(R.id.photo_container)
    FrameLayout photoContainer;
    @BindView(R.id.photo)
    ImageView photo;
    @BindView(R.id.data_container)
    RelativeLayout dataContainer;
    @BindView(R.id.place)
    TextView place;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.temperature)
    TextView temperature;
    @BindView(R.id.gradient)
    View gradient;
    @BindView(R.id.days_container)
    FrameLayout daysContainer;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.err_msg)
    LinearLayout errMsg;
    @BindView(R.id.retry)
    Button retry;


    public static PlaceDetailFragment newInstance(String transitionName,
                                                  CurrentConditionModel currentCondition) {
        PlaceDetailFragment placeDetailFragment = new PlaceDetailFragment();
        Bundle bundle = new Bundle();
        if (transitionName != null)
            bundle.putString("transition", transitionName);
        bundle.putParcelable("currentCondition", Parcels.wrap(currentCondition));
        placeDetailFragment.setArguments(bundle);
        return placeDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (viewState == null)
            viewState = new PlaceDetailViewState();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_place_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        currentCondition = Parcels.unwrap(getArguments().getParcelable("currentCondition"));
        if (presenter == null)
            WeatherApplication.from(getActivity()).getComponent().inject(this);
        presenter.attachView(this, currentCondition);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            daysContainer.setTransitionName(getArguments().getString("transition"));

        if (savedInstanceState == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            startEnterAnimation();
        else {
            photoContainer.setVisibility(View.VISIBLE);
            place.setVisibility(View.VISIBLE);
            temperature.setVisibility(View.VISIBLE);
            description.setVisibility(View.VISIBLE);
            gradient.setVisibility(View.VISIBLE);
        }

        presenter.getPhoto(getActivity().getApplicationContext());

        Transition transition = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            transition = getSharedElementEnterTransition();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && transition != null && savedInstanceState == null) {
            transition.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {

                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    if (isAdded()) {
                        presenter.getForecast();
                    }
                }

                @Override
                public void onTransitionCancel(Transition transition) {

                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }
            });
        } else
            setUpViewState();

        place.setText(currentCondition.getName());
        description.setText(currentCondition.getWeatherText());
        temperature.setText(getString(R.string.temperature_current, currentCondition.getTemperature().getMetric().getValue()));

        retry.setOnClickListener(v -> {
            presenter.getForecast();
            presenter.getPhoto(getActivity().getApplicationContext());
        });

    }

    private void setUpViewState() {
        switch (viewState.getCurrentViewState()) {
            case PlaceDetailViewState.STATE_SHOW_LOADING:
                showProgressBar();
                break;
            case PlaceDetailViewState.STATE_SHOW_CONTENT:
                presentForecast(viewState.getLoadedData());
                break;
            case PlaceDetailViewState.STATE_ERROR:
                showError();
                break;
            default:
                presenter.getForecast();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

        presenter.detachView();
    }

    private void startEnterAnimation() {
        int startDelay = getResources().getInteger(android.R.integer.config_mediumAnimTime);
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (dataContainer != null) {
                int cx = (photoContainer.getLeft() + photoContainer.getRight()) / 2;
                int cy = photoContainer.getTop();
                TransitionManager.beginDelayedTransition(dataContainer,
                        new Fade()
                                .setDuration(200));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    GuiUtils.animateRevealShow(photoContainer, cx, cy, 0, 180,
                            new GuiUtils.RevealAnimationListener() {
                                @Override
                                public void onAnimationStart() {

                                }

                                @Override
                                public void onAnimationEnd() {
                                    if (daysContainer != null) {
                                        TransitionManager.beginDelayedTransition(dataContainer, new Fade());
                                        place.setVisibility(View.VISIBLE);
                                        temperature.setVisibility(View.VISIBLE);
                                        description.setVisibility(View.VISIBLE);
                                        gradient.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                else photoContainer.setVisibility(View.VISIBLE);
            }
        }, startDelay);
    }

    @Override
    public void presentPhoto(Bitmap bitmap) {

        TransitionManager.beginDelayedTransition(photoContainer,
                new Fade().setDuration(200));
        photo.setImageBitmap(bitmap);
        photo.setVisibility(View.VISIBLE);

        Palette.from(bitmap).generate(p -> {
            TransitionManager.beginDelayedTransition(dataContainer, new Recolor().setStartDelay(250));
            daysContainer.setBackgroundDrawable(new ColorDrawable(p.getMutedColor(getActivity().getResources().getColor(R.color.card_blue))));
        });

    }

    @Override
    public void presentForecast(List<DailyForecastModel> data) {
        errMsg.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        viewState.setStateShowContent(data);
        TransitionManager.beginDelayedTransition(daysContainer, new Fade());

        days.removeAllViewsInLayout();
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        for (int i = 0; i < 4; i++) {
            View view = inflater.inflate(R.layout.item_day, days, false);
            ((TextView) ButterKnife.findById(view, R.id.date))
                    .setText(getDate(data.get(i).getEpochDate() * 1000L));
            ((ImageView) ButterKnife.findById(view, R.id.icon))
                    .setImageDrawable(GuiUtils.getDrawable(data.get(i).getDay().getIcon(), getActivity()));
            ((TextView) ButterKnife.findById(view, R.id.temperature))
                    .setText(getString(R.string.temperature_day,
                            data.get(i).getTemperature().getMinimum().getValue(),
                            data.get(i).getTemperature().getMaximum().getValue()));

            days.addView(view);
        }

        progressBar.setVisibility(View.INVISIBLE);
        days.setVisibility(View.VISIBLE);

    }

    private String getDate(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE", Locale.US);
        return sdf.format(date);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        viewState.setStateShowLoading();
        errMsg.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        progressBar.setVisibility(View.GONE);
        if (viewState.getLoadedData() == null ||
                viewState.getLoadedData().isEmpty()) {
            errMsg.setVisibility(View.VISIBLE);
            viewState.setStateError();
        }
    }
}
