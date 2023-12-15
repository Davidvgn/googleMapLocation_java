package com.davidvignon.googlemaplocationjava.ui.main;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.davidvignon.googlemaplocationjava.data.LocationRepository;
import com.davidvignon.googlemaplocationjava.data.PermissionRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {

    @NonNull
    private final PermissionRepository permissionRepository;
    @NonNull
    private final LocationRepository locationRepository;

    @Inject
    public MainViewModel(@NonNull PermissionRepository permissionRepository,
                         @NonNull LocationRepository locationRepository
    ) {
        this.permissionRepository = permissionRepository;
        this.locationRepository = locationRepository;

    }

    @SuppressLint("MissingPermission")
    public void refresh() {
        if (permissionRepository.isLocationPermissionGranted()) {
            locationRepository.startLocationRequest();
        } else {
            locationRepository.stopLocationRequest();
        }
    }
}