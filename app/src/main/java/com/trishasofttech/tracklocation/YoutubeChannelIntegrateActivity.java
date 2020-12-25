package com.trishasofttech.tracklocation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.util.ArrayList;

public class YoutubeChannelIntegrateActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<VideoDetails> videoDetailsArrayList;
    CustomListAdapter customListAdapter;
    String searchName;
    String TAG = "ChannelActivity";
    String URL = "https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=UCcxjQ2GuA1SD3i4OXCt_H5g&maxResults=25&key=AIzaSyBmN4qq5YHSznEr8ZspxnfrfyP2UCJFaPo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_channel_integrate);
        listView = findViewById(R.id.listview);
        /*to allocate the memory for Arraylist object*/
        videoDetailsArrayList=new ArrayList<>();
        /*get all video from youtube channel*/
        showVideo();
        customListAdapter=new CustomListAdapter(YoutubeChannelIntegrateActivity.this,
                videoDetailsArrayList);


    }

    private void showVideo() {
        StringRequest sr = new StringRequest(0, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                /*json parse data into arraylist*/
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("items");
                    for(int i=2;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        JSONObject jsonVideoId=jsonObject1.getJSONObject("id");
                        JSONObject jsonsnippet= jsonObject1.getJSONObject("snippet");
                        JSONObject jsonObjectdefault = jsonsnippet.getJSONObject("thumbnails")
                                .getJSONObject("medium");

                        VideoDetails videoDetails=new VideoDetails();

                        String videoid=jsonVideoId.getString("videoId");
                        Toast.makeText(YoutubeChannelIntegrateActivity.this,
                                videoid, Toast.LENGTH_SHORT).show();
                        Log.e(TAG," New Video Id" +videoid);
                        videoDetails.setURL(jsonObjectdefault.getString("url"));
                        videoDetails.setVideoName(jsonsnippet.getString("title"));
                        videoDetails.setVideoDesc(jsonsnippet.getString("description"));
                        videoDetails.setVideoId(videoid);


                        videoDetailsArrayList.add(videoDetails);

                    }
                    listView.setAdapter(customListAdapter);
                    customListAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue rq = Volley.newRequestQueue(YoutubeChannelIntegrateActivity.this);
        rq.add(sr);
    }
}