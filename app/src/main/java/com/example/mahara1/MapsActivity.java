package com.example.mahara1;

import static com.example.mahara1.SessionTypeWiseActivity.address;
import static com.example.mahara1.SessionTypeWiseActivity.name;

import androidx.fragment.app.FragmentActivity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import com.example.mahara1.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Log.e("ADDRESS: ",address);
        Log.e("name: ",name);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        new GeocodeTask(this, new GeocodeTask.GeocodeListener() {
            @Override
            public void onGeocodeCompleted(LatLng latLngResult) {
                if (latLngResult != null) {
                    latLng = latLngResult;
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
                    mMap.addMarker(new MarkerOptions().position(latLng).title(name));
                }
            }
        }).execute(address);
    }


    public LatLng getLatLongFromAddress(Context context, String address){
        LatLng latLng=null;
        Geocoder geocoder=new Geocoder(context);
        List<Address> addresses;
        try {
            addresses= geocoder.getFromLocationName(address,2);
            Address location=addresses.get(0);
            Double lat=location.getLatitude();
            Double lang=location.getLongitude();
            latLng= new LatLng(lat,lang);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return latLng;
    }


}