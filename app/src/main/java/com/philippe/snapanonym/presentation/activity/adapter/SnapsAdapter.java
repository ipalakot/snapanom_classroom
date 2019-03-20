package com.philippe.snapanonym.presentation.activity.adapter;
import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
//import android.util.Base64;
//import com.bumptech.glide.Glide;
import com.philippe.snapanonym.R;
import com.philippe.snapanonym.model.Snap;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
//import com.philippe.snapanonym.presentation.model.Snap;


    public class SnapsAdapter extends RecyclerView.Adapter<SnapsAdapter.SnapItemViewHolder> {


        private List<Snap> mSnaps;
        private Context mContext;

        public SnapsAdapter(List<Snap> mSnaps, Context mContext) {
            this.mSnaps = mSnaps;
            this.mContext = mContext;
        }


        @NonNull
        @Override
        public SnapItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull SnapItemViewHolder snapItemViewHolder, int posiiton) {
            Snap currentSnap = mSnaps.get(posiiton);
            snapItemViewHolder.snapdistance.setText(String.valueOf(currentSnap.getDistance()) + " " + "m away");

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        public class SnapItemViewHolder extends RecyclerView.ViewHolder{
            private ImageView snapImage;
            private TextView  snapdistance;

            public SnapItemViewHolder(@NonNull View itemView) {
                super(itemView);
                snapdistance = itemView.findViewById(R.id.snap_info);
            }
        }
    }

