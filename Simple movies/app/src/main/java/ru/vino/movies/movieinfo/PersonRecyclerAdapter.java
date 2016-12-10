package ru.vino.movies.movieinfo;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import ru.vino.movies.R;
import ru.vino.movies.Utils;
import ru.vino.movies.base.IPerson;

public class PersonRecyclerAdapter extends RecyclerView.Adapter<PersonRecyclerAdapter.PersonViewHolder> {

    List<? extends IPerson> data;

    private boolean smallList;

    public PersonRecyclerAdapter(boolean smallList) {
        this.smallList = smallList;
    }

    public PersonRecyclerAdapter(List<? extends IPerson> data) {
        this.data = data;
        smallList = false;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_person, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {

        Context context = holder.itemView.getContext();

        IPerson person = data.get(position);
        holder.name.setText(person.getName());
        holder.role.setText(person.getRole());

        Glide
                .with(context)
                .load(Utils.getPosterURL(person.getPhoto()))
                .placeholder(new ColorDrawable(context.getResources().getColor(R.color.colorBackground)))
                .dontAnimate()
                .into(holder.photo);

    }

    @Override
    public int getItemCount() {

        if (smallList)
            if (data != null)
                if (data.size() >= 3)
                    return 3;
                else return data.size();

        return data == null ? 0 : data.size();
    }

    public void presentData(List<? extends IPerson> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.photo)
        CircleImageView photo;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.role)
        TextView role;

        public PersonViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
