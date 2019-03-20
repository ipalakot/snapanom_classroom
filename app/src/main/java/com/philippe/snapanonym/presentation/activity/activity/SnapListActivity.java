package com.philippe.snapanonym.presentation.activity.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.philippe.snapanonym.R;
import com.philippe.snapanonym.infrastructure.AppUtils;
import com.philippe.snapanonym.model.Snap;
import com.philippe.snapanonym.presentation.activity.Loader.SnapsLoader;
import com.philippe.snapanonym.presentation.activity.adapter.SnapsAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.philippe.snapanonym.infrastructure.AppUtils.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;

public class SnapListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Snap>> {

    List<Snap> mSnpas = new ArrayList<>();
    RecyclerView mRecyclerView;
    SnapsAdapter mAdapter;
    RelativeLayout mSpinner;
    Location mLocation;
    double mScope;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location mCurrentLocation;
    private LoaderManager.LoaderCallbacks<? extends Object> mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snap_list);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        mContext = this; // on l'initialise pas dans sa methode car c'est une implémentation d'nterface
        mSpinner = findViewById(R.id.spinner);
        mRecyclerView = findViewById(R.id.snap_list_recyclerview);
        mAdapter = new SnapsAdapter(mSnpas, this);
        mRecyclerView.setAdapter(mAdapter);
        // par défaut le layoutmanager charge de manière  vertical
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        //TODO load snaps unsing an Asyntaskloader

        initData();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void initData() {
        mSpinner.setVisibility(View.VISIBLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            if (!AppUtils.hasPermissions(this, permissions)) {

                // Permission is not granted
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                } else {
                    AppUtils.requestPermissions(this, permissions, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                    // No explanation needed; request the permission
                }
            } else {
                //Permission already granted , get the current location and loadSnaps
                retrieveSnaps();
            }
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Permission granted, we can loadSnaps
                    retrieveSnaps();
                } else {
                    //Permission not granted, go back to the home page
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);

                }
                return;
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void retrieveSnaps() {

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location result) {
                        // Got last known location. In some rare situations this can be null.
                        if (result != null) {
                            // Logic to handle location object
                            mCurrentLocation = result;
                            getSupportLoaderManager().initLoader(0, null, mContext);
                        }
                    }
                });

    }


    @NonNull
    @Override
    public Loader<List<Snap>> onCreateLoader(int id, @Nullable Bundle args) {
        return new SnapsLoader(this, mLocation, mScope);
    }


    @Override
    public void onLoadFinished(@NonNull Loader<List<Snap>> loader, List<Snap> snaps) {


        mSnpas.addAll(snaps);
        mAdapter.notifyDataSetChanged();
        mSpinner.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Snap>> loader) {

    }
}