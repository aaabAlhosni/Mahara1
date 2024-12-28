package com.example.mahara1;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

public class GeocodeTask extends AsyncTask<String, Void, LatLng> {

    private final Context context;
    private final GeocodeListener listener;

    public GeocodeTask(Context context, GeocodeListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected LatLng doInBackground(String... addresses) {
        if (addresses.length == 0) return null;

        String address = addresses[0];
        Geocoder geocoder = new Geocoder(context);
        try {
            List<Address> addressList = geocoder.getFromLocationName(address, 1);
            if (addressList != null && !addressList.isEmpty()) {
                Address location = addressList.get(0);
                double lat = location.getLatitude();
                double lng = location.getLongitude();
                return new LatLng(lat, lng);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(LatLng latLng) {
        if (listener != null) {
            listener.onGeocodeCompleted(latLng);
        }
    }

    public interface GeocodeListener {
        void onGeocodeCompleted(LatLng latLng);
    }
}
