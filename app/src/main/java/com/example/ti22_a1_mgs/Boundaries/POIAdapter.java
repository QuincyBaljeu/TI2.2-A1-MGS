package com.example.ti22_a1_mgs.Boundaries;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ti22_a1_mgs.PointOfInterestTestData;
import com.example.ti22_a1_mgs.R;

import java.util.ArrayList;

public class POIAdapter extends RecyclerView.Adapter<POIAdapter.MyViewHolder> {

    private ArrayList<PointOfInterestTestData> dataset;

    public POIAdapter(ArrayList<PointOfInterestTestData> dataset) {
        this.dataset = dataset;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_poi_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PointOfInterestTestData poi = dataset.get(position);
        holder.id.setText(poi.getTitle());
        holder.imageView.setBackgroundColor(poi.getRandomNumber());


    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView id;
        public ImageView imageView;
        public View layout;

        // Dit is een rij in de recycler view
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.layout = itemView;
            id = (TextView) itemView.findViewById(R.id.PointOfViewTitle);
            imageView = (ImageView) itemView.findViewById(R.id.PointOfViewImage);

        }
    }
}
