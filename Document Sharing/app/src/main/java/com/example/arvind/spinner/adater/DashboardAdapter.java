package com.example.arvind.spinner.adater;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.arvind.spinner.Dashboard;
import com.example.arvind.spinner.R;

import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.MyViewHolder> {

    private List<Dashboard> moviesList;
    private Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView semesterName;
        private AppCompatImageView img;

        public MyViewHolder(View view) {
            super(view);
            semesterName = (TextView) view.findViewById(R.id.semname);
            img = (AppCompatImageView) view.findViewById(R.id.img);

        }
    }


    public DashboardAdapter(Context context,List<Dashboard> moviesList) {
        this.moviesList = moviesList;
        this.context=context;
    }

    @Override
    public DashboardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.semester_row, parent, false);

        return new DashboardAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DashboardAdapter.MyViewHolder holder, int position) {
        Dashboard ob = moviesList.get(position);
        holder.semesterName.setText(ob.getSemesterName());

        Glide.with(context)
                .load(ob.getUrl())
                .into(holder.img);

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}




