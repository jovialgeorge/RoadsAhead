package com.example.mypc.frider.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.mypc.frider.R;
import com.example.mypc.frider.model.ProductObject;
import com.example.mypc.frider.ui.ProductActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;



/**
 * Created by MyPc on 15-09-2017.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {


    Context context;

    List<ProductObject> list;

    ImageLoader imageLoader1;

    public ProductAdapter(Context context, List<ProductObject> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list,parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProductAdapter.ViewHolder holder, int position) {
        final ProductObject productObject=list.get(position);

        imageLoader1 = ServerImageParseAdapter.getInstance(context).getImageLoader();
        imageLoader1.get(productObject.getProuductServerUrl(),
                ImageLoader.getImageListener(
                        holder.networkImageView,//Server Image
                        R.mipmap.ic_launcher,//Before loading server image the default showing image.
                        android.R.drawable.ic_dialog_alert //Error image if requested image dose not found on server.
                )
        );
        holder.networkImageView.setImageUrl(productObject.getProuductServerUrl(),imageLoader1);
        holder.title.setText(productObject.getProductName());
        holder.networkImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, ProductActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                GsonBuilder gsonBuilder=new GsonBuilder();
                Gson gson= new Gson();
                String objectRepresentation=gson.toJson(productObject);
                i.putExtra("PRODUCT",objectRepresentation);
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        NetworkImageView networkImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.product_name);
            networkImageView = (NetworkImageView) itemView.findViewById(R.id.img);
        }
    }

}
