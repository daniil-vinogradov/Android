package ru.vino.movies.movieinfo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.vino.movies.R;
import ru.vino.movies.base.IPerson;

public class PersonsListActivity extends AppCompatActivity {

    public static final String EXTRA_TYPE = "EXTRA_TYPE";
    public static final String EXTRA_PERSONS = "EXTRA_PERSONS";
    public static final String EXTRA_MOVIE = "EXTRA_MOVIE";

    int type;
    String movie;
    List<IPerson> persons;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persons_list);
        ButterKnife.bind(this);

        type = getIntent().getIntExtra(EXTRA_TYPE, 0);
        movie = getIntent().getStringExtra(EXTRA_MOVIE);
        persons = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_PERSONS));

        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        if (type == IPerson.TYPE_CAST)
            toolbar.setTitle(getString(R.string.persons_cast_title, movie));
        else toolbar.setTitle(getString(R.string.persons_crew_title, movie));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new PersonRecyclerAdapter(persons));

    }


}
