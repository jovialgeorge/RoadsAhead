package com.example.mypc.frider.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mypc.frider.R;
import com.example.mypc.frider.util.GlobalPreference;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * Created by MyPc on 25-09-2017.
 */

public class ProfileFragment extends Fragment {

    GlobalPreference globalPreference;
    View view;
    ImageView profile;
    Bitmap bitmap;
    EditText name;
    EditText address;
    Button submit;
    EditText gender;
    EditText dob;
    EditText phone;
    EditText password;
    private int PICK_IMAGE_REQUEST = 1;
    String ip = "";
    public static final String TAG = "ProfileFragment";
    RadioGroup radioSexGroup;
    RadioButton r;
    RadioButton male;
    RadioButton female;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        radioSexGroup = (RadioGroup)view.findViewById(R.id.radioSex);
        int selectedId = radioSexGroup.getCheckedRadioButtonId();
        r=(RadioButton)view.findViewById(selectedId);

        profile = (ImageView) view.findViewById(R.id.profile_image);
        globalPreference = new GlobalPreference(getActivity());
        ip = globalPreference.RetriveIP();
        name = (EditText) view.findViewById(R.id.pName);

        male=(RadioButton)view.findViewById(R.id.radioMale);
        female=(RadioButton)view.findViewById(R.id.radioFemale);

        address = (EditText) view.findViewById(R.id.pAddress);
        dob = (EditText) view.findViewById(R.id.pdob);
        phone = (EditText) view.findViewById(R.id.pphone);
        password = (EditText) view.findViewById(R.id.ppassword);

        submit = (Button) view.findViewById(R.id.submit_profile);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 update();
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();

            }
        });

        details();

        Log.d(TAG, "onCreateView: ");
        return view;
    }

    public void updateimage() {
        String url = "http://" + ip + "/update_profile_image.php";
        final String username = globalPreference.getName();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse image: " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse image: " + error);
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String image = getStringImage(bitmap);
                Map<String, String> params = new Hashtable<String, String>();

                params.put("image1", image);
                params.put("name", username);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
      requestQueue.add(stringRequest);
    }


    public void update() {
        int selectedId = radioSexGroup.getCheckedRadioButtonId();
        r=(RadioButton)view.findViewById(selectedId);


        String UPDATE_URL="http://"+ip+"/update_profile.php";
        Log.d(TAG, "update: "+UPDATE_URL);
        final String uname = name.getText().toString();
        final String addrss = address.getText().toString();
        final String  pass=password.getText().toString();
        final String gend=r.getText().toString();
        final String dob1=dob.getText().toString();
        final String phone1=phone.getText().toString();

        final ProgressDialog loading = ProgressDialog.show(getActivity(),"Uploading...","Please wait...",false,false);
        StringRequest stringRequest= new StringRequest(Request.Method.POST, UPDATE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();

                Log.d(TAG, "onResponse: "+response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(TAG, "onErrorResponse: "+error);
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                    Toast.makeText(getActivity(), "Error no connection", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {

                    Toast.makeText(getActivity(), "Auth failure", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {

                    Toast.makeText(getActivity(), "server error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(getActivity(), "network error", Toast.LENGTH_SHORT).show();

                } else if (error instanceof ParseError) {
                    Toast.makeText(getActivity(), "server error", Toast.LENGTH_SHORT).show();
                }

                loading.dismiss();

            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String

                //Getting Image Name
                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();
                //Adding parameters
                params.put("name",uname);
               params.put("address",addrss);
                params.put("gender",gend);
                params.put("dob",dob1);
               params.put("phone",phone1);
                params.put("password",pass);

                //returning parameters
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);

    }



    public String getStringImage(Bitmap bmp) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    public void details() {

        final String uname = globalPreference.getName();
        final String pass = globalPreference.getPassword();
        Toast.makeText(getActivity(), "RToo", Toast.LENGTH_SHORT).show();
        String JSON_URL = "http://" + ip + "/login.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: " + response);
                showlist(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error);


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", uname);
                params.put("password", pass);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void showlist(String response) {

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(response);
            for (int i = 0; i <= jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String uname = jsonObject.getString("name");
                String addrss = jsonObject.getString("address");
                String pass = jsonObject.getString("password");
                String gender1 = jsonObject.getString("gender");
                String dob1 = jsonObject.getString("dob");
                String phone1 = jsonObject.getString("phone");
                String url = jsonObject.getString("image");
                String image_url="http://"+ip+"/"+url;

                Picasso.with(getActivity()).load(image_url).into(profile);

                name.setText(uname);
                address.setText(addrss);
                password.setText(pass);

                if(gender1.equals("Male")){

                    male.setChecked(true);
                }
                else{

                    female.setChecked(true);
                }

                dob.setText(dob1);
                phone.setText(phone1);


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Profile Fragment");

    }


    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);

                //Setting the Bitmap to ImageView
                profile.setImageBitmap(bitmap);
                updateimage();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
