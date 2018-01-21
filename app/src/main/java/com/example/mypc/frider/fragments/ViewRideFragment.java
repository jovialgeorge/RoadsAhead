package com.example.mypc.frider.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mypc.frider.R;
import com.example.mypc.frider.adapters.RideAdapter;
import com.example.mypc.frider.model.RideObject;
import com.example.mypc.frider.util.GlobalPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by MyPc on 10-10-2017.
 */

public class ViewRideFragment extends Fragment {

    List<RideObject> list;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    RecyclerView.Adapter recyclerViewAdapter;
    public static final String TAG = ViewRideFragment.class.getSimpleName();
    GlobalPreference globalPreference;
    String url="";

    String GET_JSON_DATA_HTTP_URL ="";

    JsonArrayRequest jsonArrayRequest;

    RequestQueue requestQueue;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View view = inflater.inflate(R.layout.fragment_view_ride, container, false);


        globalPreference = new GlobalPreference(getActivity());
        list = new ArrayList<>();


        recyclerView= (RecyclerView)view.findViewById(R.id.ride_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String value = bundle.getString("myride","");

            if(value.equals("my")){
                json_data_web_callMyRide();

            }


        }
       else{
             json_data_web_call();

        }



        return view;

    }



    public void json_data_web_callMyRide() {
      String  ip = globalPreference.RetriveIP();
      final   String name=globalPreference.getName();
        String url="http://"+ip+"/getmyride.php";



        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                    jsonParsing(s);
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
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());


        //Adding request to the queue
        requestQueue.add(stringRequest);



    }


    public  void jsonParsing(String response){


        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                RideObject rideObject =new  RideObject();
                JSONObject json = null;
                json = jsonArray.getJSONObject(i);

                rideObject.setName(json.getString("name"));
                rideObject.setSrc(json.getString("src"));
                rideObject.setDes(json.getString("des"));
                rideObject.setDate(json.getString("date"));
                rideObject.setTime(json.getString("time"));


                list.add(rideObject);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        recyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerViewAdapter = new RideAdapter(getActivity(), list);

        recyclerView.setAdapter(recyclerViewAdapter);




    }




    public void json_data_web_call() {
        url = globalPreference.RetriveIP();

        GET_JSON_DATA_HTTP_URL="http://"+url+"/getallride.php";

        Log.d(TAG, "json_data_web_call: "+GET_JSON_DATA_HTTP_URL);
        jsonArrayRequest = new JsonArrayRequest(GET_JSON_DATA_HTTP_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d(TAG, "onResponse: "+response);
                JSON_PARSE_DATA_AFTER_WEBCALL(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "error" + error, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onErrorResponse: "+error);
            }
        });

        requestQueue = Volley.newRequestQueue(getActivity());

        requestQueue.add(jsonArrayRequest);
    }


    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray jsonArray) {

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                RideObject rideObject =new  RideObject();
                JSONObject json = null;
                json = jsonArray.getJSONObject(i);

                rideObject.setName(json.getString("name"));
                rideObject.setSrc(json.getString("src"));
                rideObject.setDes(json.getString("des"));
                rideObject.setDate(json.getString("date"));
                rideObject.setTime(json.getString("time"));


                list.add(rideObject);
            }

        } catch (Exception E) {


        }


        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        recyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerViewAdapter = new RideAdapter(getActivity(), list);

        recyclerView.setAdapter(recyclerViewAdapter);
    }




    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for you

    }

}
