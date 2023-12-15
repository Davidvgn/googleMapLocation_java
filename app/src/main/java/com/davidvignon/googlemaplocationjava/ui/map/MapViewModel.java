package com.davidvignon.googlemaplocationjava.ui.map;

import android.location.Location;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.davidvignon.googlemaplocationjava.data.LocationRepository;
import com.davidvignon.googlemaplocationjava.data.PermissionRepository;
import com.davidvignon.googlemaplocationjava.utils.SingleLiveEvent;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MapViewModel extends ViewModel {

    @NonNull
    private final PermissionRepository permissionRepository;
    private final SingleLiveEvent<LatLng> cameraUpdateSingleLiveEvent = new SingleLiveEvent<>();

    @Inject
    public MapViewModel(
            @NonNull PermissionRepository permissionRepository,

            @NonNull LocationRepository locationRepository
    ) {
        this.permissionRepository = permissionRepository;


        LiveData<Location> locationLiveData = locationRepository.getLocationLiveData();

        cameraUpdateSingleLiveEvent.addSource(locationLiveData, location -> {
            if (location != null) {
                cameraUpdateSingleLiveEvent.removeSource(locationLiveData);
                cameraUpdateSingleLiveEvent.setValue(new LatLng(location.getLatitude(), location.getLongitude()));
            }
        });

    }

    @NonNull
    public SingleLiveEvent<LatLng> getFocusOnUser() {
        return cameraUpdateSingleLiveEvent;
    }

    public LiveData<Boolean> isLocationGrantedLiveData() {
        return permissionRepository.isUserLocationGrantedLiveData();
    }
}