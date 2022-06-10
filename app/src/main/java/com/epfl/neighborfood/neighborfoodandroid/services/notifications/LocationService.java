package com.epfl.neighborfood.neighborfoodandroid.services.notifications;

import android.app.Activity;
import android.app.Service;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.epfl.neighborfood.neighborfoodandroid.NeighborFoodApplication;
import com.epfl.neighborfood.neighborfoodandroid.models.PickupLocation;
import com.google.android.gms.tasks.Task;

public abstract class LocationService extends Service {
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    public static final PickupLocation DEFAULT_LOCATION = new PickupLocation(-33.8523341, 151.2106085);
    protected final MutableLiveData<PickupLocation> pickupLocationMutableLiveData;

    protected LocationService() {
        this.pickupLocationMutableLiveData = new MutableLiveData<>(DEFAULT_LOCATION);
    }

    public boolean getLocationPermissionGranted() {
        return (ContextCompat.checkSelfPermission(NeighborFoodApplication.getAppContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED);
    }
    public void requestLocationPermission(Activity activity){
        ActivityCompat.requestPermissions(activity,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
    }
    public LiveData<PickupLocation> getPickupLocationLiveData(){
        return pickupLocationMutableLiveData;
    }
    public abstract Task<PickupLocation> getDeviceLocation();
}
