package com.example.mypc.frider.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.mypc.frider.R;
import com.example.mypc.frider.model.ImageObject;

import java.util.List;

import static com.example.mypc.frider.R.id.coverImageView;
import static com.example.mypc.frider.R.id.titleTextView;

/**
 * Created by MyPc on 10-10-2017.
 */

public class PostAdapter  extends RecyclerView.Adapter<PostAdapter.ViewHolder> {


    Context context;

    List<ImageObject> list;

    ImageLoader imageLoader1;


    public PostAdapter(Context context, List<ImageObject> list) {
        this.context = context;
        this.list=list;
    }

    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card,parent, false);
        PostAdapter.ViewHolder viewHolder = new PostAdapter.ViewHolder(v);
        return viewHolder;


    }

    @Override
    public void onBindViewHolder(PostAdapter.ViewHolder holder, int position) {

        final ImageObject imageObject=list.get(position);

        imageLoader1 = ServerImageParseAdapter.getInstance(context).getImageLoader();
        imageLoader1.get(imageObject.getServer_url(),
                ImageLoader.getImageListener(
                        holder.networkImageView,//Server Image
                        R.mipmap.ic_launcher,//Before loading server image the default showing image.
                        android.R.drawable.ic_dialog_alert //Error image if requested image dose not found on server.
                )
        );
        holder.networkImageView.setImageUrl(imageObject.getServer_url(),imageLoader1);
        holder.title.setText(imageObject.getTitle());
        holder.likeImageView.setTag(R.drawable.ic_like);
        holder.networkImageView.setTag(list.get(position).getImageResourceId());
        holder.user_name.setText(imageObject.getName());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        NetworkImageView networkImageView;
        ImageView  likeImageView;
        ImageView shareImageView;
        TextView user_name;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(titleTextView);
            networkImageView = (NetworkImageView) itemView.findViewById(coverImageView);
            likeImageView = (ImageView)itemView.findViewById(R.id.likeImageView);
            shareImageView = (ImageView)itemView.findViewById(R.id.shareImageView);
            user_name=(TextView)itemView.findViewById(R.id.name);

            likeImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    int id = (int)likeImageView.getTag();
                    if( id == R.drawable.ic_like){

                        likeImageView.setTag(R.drawable.ic_liked);
                        likeImageView.setImageResource(R.drawable.ic_liked);


                    }else{

                        likeImageView.setTag(R.drawable.ic_like);
                        likeImageView.setImageResource(R.drawable.ic_like);



                    }

                }
            });

            shareImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)  {

                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, PostAdapter.this.list.toString());
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, PostAdapter.this.list.toString());
                    v.getContext().startActivity(Intent.createChooser(sharingIntent, "Share via"));

                }
            });



        }
    }

}
