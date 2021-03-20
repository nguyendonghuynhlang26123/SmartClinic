package com.team13.patientclient;

import androidx.fragment.app.Fragment;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class HospitalMapFragment extends SupportMapFragment implements OnMapReadyCallback {

    GoogleMap googleMap;

    public HospitalMapFragment() {
        // Required empty public constructor
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng position = new LatLng(10.864671,106.745629);
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        this.googleMap.addMarker(new MarkerOptions().position(position).title("Marker at hospital"));
        this.googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, this.googleMap.getMaxZoomLevel()-5));
    }
}