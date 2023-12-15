package com.davidvignon.googlemaplocationjava.ui.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;


import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MapFragment extends SupportMapFragment {

    @NonNull
    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MapViewModel viewModel = new ViewModelProvider(this).get(MapViewModel.class);


        //noinspection deprecation
        requestPermissions(
                new String[]{
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                },
                0
        );


        getMapAsync(googleMap -> {
            // Permet d'afficher le bouton qui recentre la map
            viewModel.isLocationGrantedLiveData().observe(MapFragment.this.getViewLifecycleOwner(),
                    new Observer<Boolean>() {
                        @SuppressLint("MissingPermission")
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            googleMap.setMyLocationEnabled(aBoolean);
                        }
                    });

            // Permet d'effectuer le zoom sur la location
            viewModel.getFocusOnUser().observe(getViewLifecycleOwner(), latLng -> googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15)));
        });
    }
}
