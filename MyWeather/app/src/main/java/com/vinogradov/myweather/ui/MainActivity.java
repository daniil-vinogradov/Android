package com.vinogradov.myweather.ui;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.DialogInterface;
import android.content.Loader;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.vinogradov.myweather.R;
import com.vinogradov.myweather.adapters.CityAdapter;
import com.vinogradov.myweather.adapters.SimpleItemTouchHelperCallback;
import com.vinogradov.myweather.loaders.NewCityLoader;
import com.vinogradov.myweather.loaders.RefreshCitiesLoader;
import com.vinogradov.myweather.rest.response.BaseResponse;

import io.realm.Realm;

/**
 * @author d.vinogradov
 */
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<BaseResponse> {

    RecyclerView cities;
    CityAdapter cityAdapter;
    RelativeLayout progressBar;
    ItemTouchHelper itemTouchHelper;
    SwipeRefreshLayout swipeRefreshLayout;
    public CoordinatorLayout coordinatorLayout;
    final public static int NEW_CITY_LOADER = 1;
    final public static int REFRESH_CITIES_LOADER = 2;

    Realm realm;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getInstance(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = (RelativeLayout) findViewById(R.id.progressBar);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        cities = (RecyclerView) findViewById(R.id.cities);
        cityAdapter = new CityAdapter(this, realm);
        cities.setLayoutManager(new LinearLayoutManager(this));
        cities.setAdapter(cityAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(cityAdapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(cities);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLoaderManager().restartLoader(REFRESH_CITIES_LOADER, Bundle.EMPTY, MainActivity.this);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
                b.setTitle("New city");
                final EditText input = new EditText(MainActivity.this);
                b.setView(input);
                b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {

                        Bundle bundle = new Bundle();
                        bundle.putString("city", input.getText().toString());
                        getLoaderManager().restartLoader(NEW_CITY_LOADER, bundle, MainActivity.this);

                    }
                });
                b.setNegativeButton("CANCEL", null);
                b.create().show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<BaseResponse> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case NEW_CITY_LOADER:
                progressBar.setVisibility(View.VISIBLE);
                return new NewCityLoader(this, args.getString("city"));
            case REFRESH_CITIES_LOADER:
                return new RefreshCitiesLoader(this);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<BaseResponse> loader, BaseResponse data) {
        switch (loader.getId()) {
            case NEW_CITY_LOADER:
                cityAdapter.swap();
                progressBar.setVisibility(View.INVISIBLE);
                getLoaderManager().destroyLoader(loader.getId());
                break;
            case REFRESH_CITIES_LOADER:
                cityAdapter.swap();
                swipeRefreshLayout.setRefreshing(false);
                getLoaderManager().destroyLoader(loader.getId());
        }
    }

    @Override
    public void onLoaderReset(Loader<BaseResponse> loader) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

}
