package com.philippe.snapanonym;

import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import com.philippe.snapanonym.model.Snap;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NetworkUtils {

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    // Base URL for snaps API.
    private static final String SNAP_BASE_URL = "http://ec2-54-174-75-230.compute-1.amazonaws.com:9000?";



    // Parameter for coordinate longitude.
    private static final String LONGITUDE = "longitude";
    // Parameter for coordinate latitude.
    private static final String LATITUDE = "latitude";
    // Parameter for distance.
    private static final String DISTANCE = "distance";
    private final ConnectivityManager mConnectivityManager;

    private Context mContext;
    private NetworkInfo networkInfo;

    public NetworkUtils(Context context) {
        this.mContext = context;
        mConnectivityManager = (ConnectivityManager)
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
    }



    public static List<Snap> getSnaps() {
        //TODO Build the request and IMPLEMENT THIS
        List<Snap> snaps = new ArrayList<>();
        HttpURLConnection urlConnection = null;
        try {

            Uri builtUri = Uri.parse(SNAP_BASE_URL).buildUpon()
                    .appendQueryParameter(LONGITUDE, String.valueOf(42.33335254))
                    .appendQueryParameter(LATITUDE, String.valueOf(2.33333))
                    .appendQueryParameter(DISTANCE, String.valueOf(30))
                    .build();
            URL requestUrl = new URL(builtUri.toString());
            urlConnection = (HttpURLConnection) requestUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();

            ObjectMapper mapper = new ObjectMapper();

            snaps = mapper.readValue(inputStream, new TypeReference<List<Snap>>() {
            });

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (urlConnection != null) {
                urlConnection.disconnect();
            }

        }

        return snaps;
    }


}