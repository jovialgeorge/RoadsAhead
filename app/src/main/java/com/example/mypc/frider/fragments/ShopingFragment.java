package com.example.mypc.frider.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
import com.example.mypc.frider.adapters.ProductAdapter;
import com.example.mypc.frider.model.ProductObject;
import com.example.mypc.frider.util.GlobalPreference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MyPc on 22-09-2017.
 */


public class ShopingFragment extends Fragment {

    List<ProductObject> list;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    RecyclerView.Adapter recyclerViewAdapter;
    public static final String TAG = ShopingFragment.class.getSimpleName();
    GlobalPreference globalPreference;
    String url="";

    String GET_JSON_DATA_HTTP_URL ="";
    String JSON_IMAGE_TITLE_NAME = "name";
    String JSON_IMAGE_URL = "url";

    JsonArrayRequest jsonArrayRequest;
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;
    RequestQueue requestQueue;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View view = inflater.inflate(R.layout.fragment_shopping, container, false);

        globalPreference = new GlobalPreference(getActivity());
        list = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.product_list);

        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);
        recyclerView.setHasFixedSize(true);

        json_data_web_call();
        return view;


    }


    public void json_data_web_call() {
         url = globalPreference.RetriveIP();

        GET_JSON_DATA_HTTP_URL="http://"+url+"/details.php";

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
                ProductObject productObject = new ProductObject();
                JSONObject json = null;
                json = jsonArray.getJSONObject(i);

                productObject.setProductName(json.getString("name"));
                productObject.setPid(json.getString("pid"));
                productObject.setColor(json.getString("color"));
                productObject.setDescription(json.getString("description"));
                productObject.setSize(json.getString("size"));
                productObject.setPrize(json.getString("prize"));

               String    url1 ="http://"+url+"/" + json.getString("url");
                Log.d(TAG, "JSON_PARSE_DATA_AFTER_WEBCALL: " + url1);

                productObject.setProuductServerUrl(url1);
                list.add(productObject);
            }

        } catch (Exception E) {


        }
        recyclerViewAdapter = new ProductAdapter(getActivity(), list);

        recyclerView.setAdapter(recyclerViewAdapter);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for you

    }
}