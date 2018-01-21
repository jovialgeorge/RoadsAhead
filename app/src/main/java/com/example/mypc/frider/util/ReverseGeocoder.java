package com.example.mypc.frider.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.util.List;

/**
 * Created by MyPc on 27-09-2017.
 */

public class ReverseGeocoder {

    Context context;
    double lat;
    double lng;

    public ReverseGeocoder(Context context) {
        this.context = context;

    }


    public void getLatLongFromPlace(String place) {
        try {
            Geocoder selected_place_geocoder = new Geocoder(context);
            List<Address> address;

            address = selected_place_geocoder.getFromLocationName(place, 5);

            if (address == null) {

            } else {
                Address location = address.get(0);
                lat = location.getLatitude();
                lng = location.getLongitude();



            }

        } catch (Exception e) {
            e.printStackTrace();


        }

    }


    public double getLat(){

        return  lat;
    }

    public  double getLng(){

        return  lng;
    }

}
