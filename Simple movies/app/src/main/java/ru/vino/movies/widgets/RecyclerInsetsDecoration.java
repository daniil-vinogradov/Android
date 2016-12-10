package ru.vino.movies.widgets;


import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import ru.vino.movies.R;

public class RecyclerInsetsDecoration extends RecyclerView.ItemDecoration {

    private int spacing;

    public RecyclerInsetsDecoration(Context context) {
        spacing = context.getResources().getDimensionPixelSize(R.dimen.movie_grid_spacing);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(spacing, spacing, spacing, spacing);
    }
}
