package com.example.ti22_a1_mgs.Controllers;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ti22_a1_mgs.Boundaries.DetailedActivity;
import com.example.ti22_a1_mgs.Database.entities.PointOfInterest;
import com.example.ti22_a1_mgs.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.RouteViewHolder> {

    private List<PointOfInterest> dataSet;
    private Context context;
    private List<String> images;

    public RouteAdapter(List<PointOfInterest> dataSet, Context context) throws IOException {
        this.dataSet = dataSet;
        this.context = context;
        images = Arrays.asList(context.getAssets().list("BWImages"));
    }

    public void setDataSet(List<PointOfInterest> dataSet) {
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public RouteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RouteViewHolder(LayoutInflater.from(context).inflate(R.layout.route_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RouteViewHolder holder, int position) {
        if (!dataSet.isEmpty()) {
            String item = dataSet.get(position).getImgUrls().get(0)
                    .replace("static/", "")
                    .replace(".JPG","")
                    .replace(".PNG","");

            int index = -1;
            for (int i = 0; i < images.size(); i++) {
              String img = images.get(i);
              if (img.contains(item))index = images.indexOf(img);
            }


            if (index <0) Log.d("@d", "onBindViewHolder: " + item);
            InputStream inputstream = null;
            try {
                inputstream = context.getAssets().open("BWImages/"
                        + images.get(index));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Drawable drawable = Drawable.createFromStream(inputstream, null);
            holder.icon.setImageDrawable(drawable);
            holder.name.setText(dataSet.get(position).getAddres());
            holder.id.setText("Waypoint " + (dataSet.get(position).getId() -1));
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class RouteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView icon;
        TextView name;
        TextView id;

        public RouteViewHolder(@NonNull View itemView) {
            super(itemView);
            this.icon = itemView.findViewById(R.id.imageView_route_icon);
            this.name = itemView.findViewById(R.id.textView_route_name);
            this.id = itemView.findViewById(R.id.textView_route_id);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            PointOfInterest selectedPoI = dataSet.get(getAdapterPosition());

            Intent intent = new Intent(context, DetailedActivity.class);
            intent.putExtra("selectedTitle", "Waypoint " + (selectedPoI.getId()-1));
            intent.putExtra("selectedPoI", selectedPoI);
            context.startActivity(intent);

        }


    }
}
