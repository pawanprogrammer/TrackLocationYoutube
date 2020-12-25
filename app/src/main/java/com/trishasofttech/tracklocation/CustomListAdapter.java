package com.trishasofttech.tracklocation;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {
    Activity activity;
    ArrayList<VideoDetails> singletons;


    public CustomListAdapter(Activity activity, ArrayList<VideoDetails> singletons) {
        this.activity = activity;
        this.singletons = singletons;
    }

    @Override
    public int getCount() {
        return singletons.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_item, viewGroup, false);
        final TextView imgtitle =  view.findViewById(R.id.tvtitle);
        final TextView imgdesc = view.findViewById(R.id.tvdesc);
        final TextView tvURL=view.findViewById(R.id.tvurl);
        final  TextView tvVideoID=view.findViewById(R.id.tvid);
        final ImageView imageView=view.findViewById(R.id.imageview);
        LinearLayout linear_container = view.findViewById(R.id.video_container);
        VideoDetails singleton = (VideoDetails) this.singletons.get(i);
        tvVideoID.setText(singleton.getVideoId());
        imgtitle.setText(singleton.getVideoName());
        imgdesc.setText(singleton.getVideoDesc());
        Glide.with(viewGroup.getContext()).load(singleton.getURL()).into(imageView);


        linear_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), MainActivity.class);
                intent.putExtra("videoId",tvVideoID.getText().toString());
                v.getContext().startActivity(intent);
            }
        });

        return  view;
    }
}
