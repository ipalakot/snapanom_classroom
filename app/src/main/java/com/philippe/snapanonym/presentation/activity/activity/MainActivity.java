package com.philippe.snapanonym.presentation.activity.activity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;

import com.philippe.snapanonym.R;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onExploreSnaps(View view) {
        Intent intent = new Intent(this, SnapListActivity.class);
        startActivity(intent);
    }

    public void onPublishSnap(View view) {
    }
}
