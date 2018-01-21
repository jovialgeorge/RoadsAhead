package com.example.mypc.frider.fragments;

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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * Created by MyPc on 10-10-2017.
 */

public class AddPostFragment extends Fragment {

    ImageView post;
    EditText title;
    Button chooser;
     Button submit;
    private Bitmap bitmap;
    private int PICK_IMAGE_REQUEST = 1;
    private String KEY_IMAGE = "image";
    String name;
    String ip="";
    View view;
    public  static final String TAG=AddPostFragment.class.getSimpleName();

    GlobalPreference globalPreference;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        view = inflater.inflate(R.layout.fragment_post, container, false);

        globalPreference=new GlobalPreference(getActivity());

        initView();
        return view;
    }

    public  void initView(){

        post=(ImageView)view.findViewById(R.id.image_post);
        title=(EditText)view.findViewById(R.id.title);
        chooser=(Button)view.findViewById(R.id.btn_filechooser);
        submit=(Button)view.findViewById(R.id.btn_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadImage();
            }
        });

        chooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });


    }




    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    private void uploadImage(){

        ip=globalPreference.RetriveIP();

        final String name=globalPreference.getName();
        final String title1=title.getText().toString();


        String url="http://"+ip+"/update_post.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        Log.d(TAG, "onResponse: "+s);

                      startActivity(new Intent(getActivity(), HomeActivity.class));


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = getStringImage(bitmap);
                //Getting Image Name
                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();
                //Adding parameters
                params.put(KEY_IMAGE, image);
                params.put("name",name);
                params.put("title",title1);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());


        //Adding request to the queue
        requestQueue.add(stringRequest);
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
                post.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for you

    }
}

