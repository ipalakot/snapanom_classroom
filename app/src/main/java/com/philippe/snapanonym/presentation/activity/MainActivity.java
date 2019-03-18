package com.philippe.snapanonym.presentation.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.philippe.snapanonym.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void onExploreSnaps(View view) {
        Intent intent = new Intent(this,SnapListActivity.class);
        startActivity(intent);

    }

    public void onPublishSnap(View view) {
    }
}
