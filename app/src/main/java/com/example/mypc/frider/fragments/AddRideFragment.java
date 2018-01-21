package com.example.mypc.frider.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mypc.frider.R;
import com.example.mypc.frider.ui.HomeActivity;
import com.example.mypc.frider.util.GlobalPreference;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.util.Calendar;
import java.util.Hashtable;
import java.util.Map;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by MyPc on 11-10-2017.
 */

public class AddRideFragment extends Fragment {

    View view;
    EditText edt_rssrc, edt_des, edt_travel_date, edt_travel_time;
    Button submit;
    DatePickerDialog datePickerDialog;
    TimePickerDialog mTimePicker;
    GlobalPreference globalPreference;
    String ip = "";
    public  static final  String TAG=AddRideFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        view = inflater.inflate(R.layout.fragment_addride, container, false);

        initViews();
        return view;
    }

    public void initViews() {
        edt_rssrc = (EditText) view.findViewById(R.id.edt_rsrc);
        edt_des = (EditText) view.findViewById(R.id.edt_des);
        edt_travel_date = (EditText) view.findViewById(R.id.edt_travel_date);
        edt_travel_time = (EditText) view.findViewById(R.id.edt_traveltime);
        submit = (Button) view.findViewById(R.id.btn_addride);
        globalPreference = new GlobalPreference(getActivity());



        edt_rssrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =
                        null;
                try {
                    intent = new PlaceAutocomplete
                            .IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(getActivity());
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                startActivityForResult(intent,1);

            }
        });

        edt_des.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =
                        null;
                try {
                    intent = new PlaceAutocomplete
                            .IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(getActivity());
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                startActivityForResult(intent, 2);
            }
        });





        edt_travel_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                edt_travel_date.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        edt_travel_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);


                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                edt_travel_time.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRide();
            }
        });


    }


    // A place has been received; use requestCode to track the request.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // retrive the data by using getPlace() method.
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                Log.e("Tag", "Place: " + place.getAddress() + place.getPhoneNumber());
                edt_rssrc.setText(place.getName() + ",\n" +
                        place.getAddress() + "\n" + place.getPhoneNumber());

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                // TODO: Handle the error.
                Log.e("Tag", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                // retrive the data by using getPlace() method.
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                Log.e("Tag", "Place: " + place.getAddress() + place.getPhoneNumber());
                edt_des.setText(place.getName() + ",\n" +
                        place.getAddress() + "\n" + place.getPhoneNumber());

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                // TODO: Handle the error.
                Log.e("Tag", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }

        }
    }



    public void addRide() {

        ip=globalPreference.RetriveIP();

        final String name = globalPreference.getName();


        String url = "http://" + ip + "/insert_ride.php";

        if(edt_rssrc.getText().toString().equals("")||edt_travel_time.getText().toString().equals("")||edt_des.getText().toString().equals("")){
            Toast.makeText(getActivity(), "Please fille the fields", Toast.LENGTH_SHORT).show();

        }
        else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            Log.d(TAG, "onResponse:" + s);
                            startActivity(new Intent(getActivity(), HomeActivity.class));
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
                    params.put("name", name);
                    params.put("src", edt_rssrc.getText().toString());
                    params.put("des", edt_des.getText().toString());
                    params.put("date", edt_travel_date.getText().toString());
                    params.put("time", edt_travel_time.getText().toString());
                    return params;
                }
            };

            //Creating a Request Queue
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());


            //Adding request to the queue
            requestQueue.add(stringRequest);
        }

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for you

    }

}
