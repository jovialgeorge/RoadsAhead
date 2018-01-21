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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mypc.frider.R;
import com.example.mypc.frider.adapters.PostAdapter;
import com.example.mypc.frider.model.ImageObject;
import com.example.mypc.frider.util.GlobalPreference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MyPc on 10-10-2017.
 */

public class ViewPostFragment extends Fragment {

    List<ImageObject> list;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    RecyclerView.Adapter recyclerViewAdapter;
    public static final String TAG = ViewPostFragment.class.getSimpleName();
    GlobalPreference globalPreference;
    String url="";

    String GET_JSON_DATA_HTTP_URL ="";
    String JSON_IMAGE_TITLE_NAME = "name";
    String JSON_IMAGE_URL = "url";

    JsonArrayRequest jsonArrayRequest;

    RequestQueue requestQueue;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View view = inflater.inflate(R.layout.fragment_viewpost, container, false);


        globalPreference = new GlobalPreference(getActivity());
        list = new ArrayList<>();


        recyclerView= (RecyclerView)view.findViewById(R.id.post_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        recyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        json_data_web_call();
        return view;

    }



    public void json_data_web_call() {
        url = globalPreference.RetriveIP();

        GET_JSON_DATA_HTTP_URL="http://"+url+"/getoffer.php";

        Log.d(TAG, "json_data_web_call: "+GET_JSON_DATA_HTTP_URL);
        jsonArrayRequest = new JsonArrayRequest(GET_JSON_DATA_HTTP_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


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
                ImageObject imageObject = new ImageObject();
                JSONObject json = null;
                json = jsonArray.getJSONObject(i);


                imageObject.setTitle(json.getString("title"));
                imageObject.setName(json.getString("name"));

                String    url1 ="http://"+url+"/" + json.getString("image");

                Log.d(TAG, "JSON_PARSE_DATA_AFTER_WEBCALL: " + url1);


                imageObject.setServer_url(url1);
                list.add(imageObject);
            }

        } catch (Exception E) {


        }
        recyclerViewAdapter = new PostAdapter(getActivity(), list);

        recyclerView.setAdapter(recyclerViewAdapter);
    }




    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for you

    }

}
