package ru.vino.weather;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.vino.weather.places.PlacesFragment;
import ru.vino.weather.placedetail.PlaceDetailFragment;
import ru.vino.weather.model.CurrentConditionModel;
import ru.vino.weather.model.responsemodels.AutocompleteResponseModel;
import ru.vino.weather.search.SearchFragment;
import ru.vino.weather.widgets.PlaceDetailTransition;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    @BindView(R.id.container_list)
    CoordinatorLayout listContainer;
    @Nullable
    @BindView(R.id.container_detail)
    CoordinatorLayout detailContainer;

    boolean dualPanel = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (detailContainer != null)
            dualPanel = true;

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container_list, new PlacesFragment(), "cities")
                    .commit();
        }

    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getFragmentManager().findFragmentById(R.id.container_list);
        if (fragment instanceof OnBackPressedListener) {
            ((OnBackPressedListener) fragment).onBackPressed();
        } else
            super.onBackPressed();
    }

    @Override
    public void back() {
        super.onBackPressed();
        showFab();
    }

    @Override
    public void showFab() {
        Fragment fragment = getFragmentManager().findFragmentById(R.id.container_list);
        if (fragment instanceof PlacesFragment)
            ((PlacesFragment) fragment).showFab();
    }

    @Override
    public void loadNewPlace(AutocompleteResponseModel place) {
        onBackPressed();
        Fragment fragment = getFragmentManager().findFragmentByTag("cities");
        if (fragment instanceof PlacesFragment)
            ((PlacesFragment) fragment).loadNewPlace(place);

    }

    @Override
    public void openPlaceDetail(View card, CurrentConditionModel currentCondition) {
        PlaceDetailFragment placeDetailFragment;

        String transitionName = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            transitionName = card.getTransitionName();
        placeDetailFragment = PlaceDetailFragment
                .newInstance(transitionName, currentCondition);


        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && detailContainer == null) {
            Transition transition = new PlaceDetailTransition();
            //for work listener in detail fragment
            transition.addListener(new EmptyTransitionListener());
            placeDetailFragment.setSharedElementEnterTransition(transition);
            placeDetailFragment.setSharedElementReturnTransition(transition);
            fragmentTransaction.addSharedElement(card, card.getTransitionName());
        }

        int containerId;
        if (dualPanel)
            containerId = R.id.container_detail;
        else {
            fragmentTransaction.addToBackStack(null);
            containerId = R.id.container_list;
        }

        fragmentTransaction
                .replace(containerId, placeDetailFragment)
                .commit();
    }

    @Override
    public void openSearch(int cx, int cy, int startRadius) {
        getFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .add(R.id.container_list, SearchFragment.newInstance(cx, cy, startRadius))
                .commit();
    }

}
