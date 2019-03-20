package com.philippe.snapanonym.presentation.activity.Loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import android.location.Location;
import android.os.Build;
import android.util.Base64;

import com.philippe.snapanonym.R;
import com.philippe.snapanonym.infrastructure.AppUtils;
import com.philippe.snapanonym.model.Picture;
import com.philippe.snapanonym.model.SimpleLocation;
import com.philippe.snapanonym.model.Snap;
import com.philippe.snapanonym.infrastructure.NetworkUtils;
import com.philippe.snapanonym.presentation.activity.activity.SnapListActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.loader.content.AsyncTaskLoader;

public class SnapsLoader extends AsyncTaskLoader<List<Snap>> {

    Location mCurrentLocation;
    Double mScope;

    public SnapsLoader(@NonNull Context context, Location mCurrentLocation, double mScope) {
        super(context);

        this.mCurrentLocation = mCurrentLocation;
        this.mScope = mScope;
    }

    @Override
    protected void onStartLoading() {
        onForceLoad();
        super.onStartLoading();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override


    public List<Snap> loadInBackground() {
        List<Snap> snaps = NetworkUtils.getSnaps(mCurrentLocation, mScope);

        for (int i = 0; i < 10; i++) {
            Snap snap = snaps.get(i);

            SimpleLocation currentSimpleLocation = new SimpleLocation(mCurrentLocation.getLongitude(), mCurrentLocation.getLatitude());

            SimpleLocation snapPostedAtSimplelocation = snap.getPostedAt();

            double distanceBetweenAsDouble = AppUtils.distanceBetweenAsMeters(currentSimpleLocation, snapPostedAtSimplelocation, 0, 0);

            snap.setDistance((int) distanceBetweenAsDouble);
        }
/*
            snap.setDistance(i);

            Drawable drawable = this.getContext().getResources().getDrawable(i % 2 == 0 ? R.drawable.analog_camera_camera_device : R.drawable.bg_snap_anonym, null);
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            String pictureContent = Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);
            picture.setContent(pictureContent);
            snap.setPicture(picture);
            snaps.add(snap);
        } */
        //return snaps;


        return snaps;
    }
}
