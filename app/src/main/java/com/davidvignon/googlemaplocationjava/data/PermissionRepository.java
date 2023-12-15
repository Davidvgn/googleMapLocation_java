package com.davidvignon.googlemaplocationjava.data;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PermissionRepository {

    @NonNull
    private final Application application;

    @Inject
    public PermissionRepository(@NonNull Application application) {
        this.application = application;
    }

    /**
     * Called in MainViewModel to check if location permission is granted
     * If so, startLocationRequest() in LocationRepository is called
     */
    public boolean isLocationPermissionGranted() {
        return ContextCompat.checkSelfPermission(application, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED;
    }

    /**
     * Called in MapViewModel to recenter map onClick of location button
     */
    public LiveData<Boolean> isUserLocationGrantedLiveData() {
        MutableLiveData<Boolean> isGrantedMutableLiveData = new MutableLiveData<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(
                    application, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(application, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                isGrantedMutableLiveData.setValue(true);
            } else {
                isGrantedMutableLiveData.setValue(false);
            }
        } else {
            isGrantedMutableLiveData.setValue(true);
        }
        return isGrantedMutableLiveData;
    }
}
