package com.example.mypc.frider.ui;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ahmadrosid.lib.drawroutemap.DrawMarker;
import com.ahmadrosid.lib.drawroutemap.DrawRouteMaps;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mypc.frider.R;
import com.example.mypc.frider.util.GlobalPreference;
import com.example.mypc.frider.util.ReverseGeocoder;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.Hashtable;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double slat;
    double slng;
    double dlng;
    double dlat;
    ReverseGeocoder reverseGeocoder;
    Button joinride;
    GlobalPreference globalPreference;
    String name,src,des,date,time;
    String ip="";
    public  static final String TAG=MapsActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        globalPreference= new GlobalPreference(getApplicationContext());
        joinride=(Button)findViewById(R.id.btn_joinride);


        Bundle extra = getIntent().getExtras();

         name = extra.getString("name");
         src = extra.getString("src");
         des = extra.getString("des");
         date = extra.getString("date");
         time = extra.getString("time");

        reverseGeocoder = new ReverseGeocoder(getApplicationContext());

        reverseGeocoder.getLatLongFromPlace(src);

        slat = reverseGeocoder.getLat();
        slng = reverseGeocoder.getLng();


        reverseGeocoder.getLatLongFromPlace(des);


        dlat = reverseGeocoder.getLat();
        dlng = reverseGeocoder.getLng();

       joinride.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               joinRide();
           }
       });
    }



    public void joinRide() {

        ip=globalPreference.RetriveIP();

        final String name1 = globalPreference.getName();


        String url = "http://" + ip + "/insert_join.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            Log.d(TAG, "onResponse:" + s);
                            Toast.makeText(MapsActivity.this, "Joined Ride", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                            Log.d(TAG, "onErrorResponse: " + volleyError);
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new Hashtable<String, String>();
                    //Adding parameters
                    params.put("rider_name",name);
                    params.put("name", name1);
                    params.put("src", src);
                    params.put("des", des);
                    params.put("date",date);
                    params.put("time",time);
                    return params;
                }
            };

            //Creating a Request Queue
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


            //Adding request to the queue
            requestQueue.add(stringRequest);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        mMap = googleMap;
        LatLng origin = new LatLng(slat,slng);
        LatLng destination = new LatLng(dlat,dlng);
        DrawRouteMaps.getInstance(this)

                .draw(origin, destination, mMap);
        DrawMarker.getInstance(this).draw(mMap, origin, R.drawable.marker_a, "Origin Location");
        DrawMarker.getInstance(this).draw(mMap, destination, R.drawable.marker_b, "Destination Location");

        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(origin)
                .include(destination).build();
        Point displaySize = new Point();
        getWindowManager().getDefaultDisplay().getSize(displaySize);
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, displaySize.x, 250, 30));
    }

}
