package com.example.mypc.frider.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mypc.frider.R;

/**
 * Created by MyPc on 11-10-2017.
 */

public class RideHomeFragment  extends Fragment{

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        view = inflater.inflate(R.layout.fragment_ridehome, container, false);

        Button addRide = (Button) view.findViewById(R.id.btn_rideadd);
        addRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = null;
                fragment = new AddRideFragment();

                if (fragment != null) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                }

            }
        });

        Button viewAllRide = (Button) view.findViewById(R.id.btn_friendride);
        viewAllRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = null;
                fragment = new ViewRideFragment();

                if (fragment != null) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                }

            }
        });



        Button viewMyRide = (Button) view.findViewById(R.id.btn_myride);
        viewMyRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = null;
                fragment = new ViewRideFragment();

                Bundle bundle = new Bundle();
                bundle.putString("myride","my");
                fragment.setArguments(bundle);


                if (fragment != null) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                }

            }
        });


        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here   for different fragments different titles


    }


}
