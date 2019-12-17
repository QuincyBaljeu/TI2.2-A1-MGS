package com.example.ti22_a1_mgs.Controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ti22_a1_mgs.Database.entities.PointOfInterest;
import com.example.ti22_a1_mgs.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.RouteViewHolder> {

    private List<PointOfInterest> dataSet;
    private Context context;

    public RouteAdapter(List<PointOfInterest> dataSet, Context context) {
        this.dataSet = dataSet;
        this.context = context;
    }

    @NonNull
    @Override
    public RouteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RouteViewHolder(LayoutInflater.from(context).inflate(R.layout.route_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RouteViewHolder holder, int position) {
        Picasso.get().load(dataSet.get(position).getImgUrls().get(0)).into(holder.icon);
        //holder.name.setText(dataSet.get(position).);
        holder.id.setText(dataSet.get(position).getId());
    }

    @Override
    public int getItemCount() {
        if(dataSet != null)
        return dataSet.size();
        else return -1;
    }
    public class RouteViewHolder extends RecyclerView.ViewHolder{
        ImageView icon;
        TextView name;
        TextView id;

        public RouteViewHolder(@NonNull View itemView) {
            super(itemView);
            this.icon = itemView.findViewById(R.id.imageView_route_icon);
            this.name = itemView.findViewById(R.id.textView_route_name);
            this.id = itemView.findViewById(R.id.textView_route_id);
        }
    }
}
