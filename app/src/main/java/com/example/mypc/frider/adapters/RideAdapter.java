package com.example.mypc.frider.adapters;

/**
 * Created by MyPc on 11-10-2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mypc.frider.R;
import com.example.mypc.frider.model.RideObject;
import com.example.mypc.frider.ui.MapsActivity;

import java.util.List;

/**
 * Created by MyPc on 10-10-2017.
 */

public class RideAdapter  extends RecyclerView.Adapter<RideAdapter.ViewHolder>{


    Context context;

    List<RideObject> list;




    public RideAdapter(Context context, List<RideObject> list) {
        this.context = context;
        this.list=list;
    }

    @Override
    public RideAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_ride,parent, false);
        RideAdapter.ViewHolder viewHolder = new RideAdapter.ViewHolder(v);
        return viewHolder;


    }

    @Override
    public void onBindViewHolder(RideAdapter.ViewHolder holder, int position) {

        final RideObject rideObject=list.get(position);



        holder.rider_name.setText(rideObject.getName());
        holder.ride_src.setText(rideObject.getSrc());
        holder.ride_des.setText(rideObject.getDes());
        holder.ride_date.setText(rideObject.getDate());
        holder.ride_time.setText(rideObject.getTime());
        holder.btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(context, MapsActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("name",rideObject.getName());
                i.putExtra("src",rideObject.getSrc());
                i.putExtra("des",rideObject.getDes());
                i.putExtra("time",rideObject.getDate());
                i.putExtra("date",rideObject.getTime());
                context.startActivity(i);
            }
        });





    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView rider_name;
        TextView ride_src;
        TextView ride_des;
        TextView ride_date;
        TextView ride_time;
        Button btn_map;


        public ViewHolder(View itemView) {
            super(itemView);

            rider_name =(TextView) itemView.findViewById(R.id.rider_name);
            ride_src =(TextView) itemView.findViewById(R.id.ride_src);
            ride_des =(TextView) itemView.findViewById(R.id.ride_des);
            ride_date =(TextView) itemView.findViewById(R.id.ride_date);
            ride_time =(TextView) itemView.findViewById(R.id.ride_time);
            btn_map=(Button)itemView.findViewById(R.id.btn_map);

        }
    }

}
