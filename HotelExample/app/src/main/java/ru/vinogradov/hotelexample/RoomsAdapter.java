package ru.vinogradov.hotelexample;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.CityViewHolder> {

    boolean[] opened = new boolean[3];
    private int lastPosition = -1;
    private int width;

    Context context;

    SliderAdapter sliderAdapter;

    public RoomsAdapter(Context context, int width) {
        this.context = context;
        sliderAdapter = new SliderAdapter(context);
        this.width = width;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.room_item, parent, false);
        return new CityViewHolder(v, context);
    }

    @Override
    public void onBindViewHolder(final CityViewHolder holder, final int position) {

        final LayoutInflater inflater = LayoutInflater.from(context);

        holder.slider.setAdapter(sliderAdapter);

        if (position > lastPosition) {
            for (int i = 0; i < 2; i++) {
                View item = inflater.inflate(R.layout.table_item, holder.table, false);
                item.startAnimation(getTranslateAnimation(i * 100));
                holder.table.addView(item);
            }
            lastPosition = position;
        }

        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ToolTip(context).show(v);
            }
        });

        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!opened[position]) {
                    for (int i = 0; i < 4; i++) {
                        View item = inflater.inflate(R.layout.table_item, holder.table, false);
                        item.startAnimation(getTranslateAnimation(i * 100));
                        holder.table.addView(item);
                    }
                    holder.more.setText(R.string.hide);
                    opened[position] = true;
                } else {
                    for (int i = holder.table.getChildCount() - 1; i > 2; i--)
                        holder.table.removeViewAt(i);
                    holder.more.setText(R.string.show_more);
                    opened[position] = false;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    private Animation getTranslateAnimation(int offset) {
        TranslateAnimation translateAnimation = new TranslateAnimation(width, 0, 0, 0);
        translateAnimation.setStartOffset(offset);
        translateAnimation.setInterpolator(new BounceInterpolator());
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }

    public static class CityViewHolder extends RecyclerView.ViewHolder {

        private ViewPager slider;
        private ImageButton icon;
        private Button more;
        private LinearLayout table;
        private CardView cardView;

        CityViewHolder(View itemView, Context context) {
            super(itemView);
            slider = (ViewPager) itemView.findViewById(R.id.slider);
            icon = (ImageButton) itemView.findViewById(R.id.icon);
            more = (Button) itemView.findViewById(R.id.more);
            table = (LinearLayout) itemView.findViewById(R.id.table);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            cardView.setUseCompatPadding(true);
        }
    }

}
