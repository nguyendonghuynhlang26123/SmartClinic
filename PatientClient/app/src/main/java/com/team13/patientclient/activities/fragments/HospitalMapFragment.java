package com.team13.patientclient.activities.fragments;

import androidx.fragment.app.Fragment;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.team13.patientclient.Store;
import com.team13.patientclient.models.HospitalModel;
import com.team13.patientclient.repository.OnResponse;
import com.team13.patientclient.repository.services.HospitalService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HospitalMapFragment extends SupportMapFragment implements OnMapReadyCallback {

    GoogleMap googleMap;

    public HospitalMapFragment() {
        // Required empty public constructor
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        HospitalModel hospital = Store.get_instance().getHospital();
        LatLng position = new LatLng(hospital.getCorX(),hospital.getCorY());
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.addMarker(new MarkerOptions().position(position).title("Marker at hospital"));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, googleMap.getMaxZoomLevel()-5));
    }
}