package com.philippe.snapanonym.presentation.activity.Loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Base64;

import com.philippe.snapanonym.R;
import com.philippe.snapanonym.model.Picture;
import com.philippe.snapanonym.model.Snap;
import com.philippe.snapanonym.infrastructure.NetworkUtils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SnapsLoader extends AsyncTaskLoader<List<Snap>> {

    Context mContext;

    public SnapsLoader(@NonNull Context context) {
        super(context);
    }


    @Override
    protected void onStartLoading() {
        onForceLoad();
        super.onStartLoading();
    }

    @Nullable
    @Override


    public List<Snap> loadInBackground() {
        List<Snap> snaps = new ArrayList<>();
        Snap snap = new Snap();
        Picture picture = new Picture();

        for (int i = 0; i < 10; i++) {
            snap.setDistance(i);

            Drawable drawable = this.getContext().getResources().getDrawable(i % 2 == 0 ? R.drawable.analog_camera_camera_device : R.drawable.bg_snap_anonym, null);
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            String pictureContent = Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);
            picture.setContent(pictureContent);
            snap.setPicture(picture);
            snaps.add(snap);
        }
        //return snaps;
        return NetworkUtils.getSnaps(nCurrentLocation, nScope);
    }
}
