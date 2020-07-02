package com.example.dockermusic.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dockermusic.Adapter.ListMp3Adapter;
import com.example.dockermusic.Model.ListAlbum;
import com.example.dockermusic.Model.ListSong;
import com.example.dockermusic.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AlbumMP3 extends AppCompatActivity {
    private TextView nameAlbum, noresult;
    private TextView nameSingerAlbum;
    private ImageView imageAlbum;
    private ImageView btnback;
    private RecyclerView recyclerAlbumDetail;
    private ArrayList<ListSong> listSongs= new ArrayList<>();
    private ListMp3Adapter adapterListMp3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_detail_album);
        nameAlbum= findViewById(R.id.nameAlbumDetail);
        noresult= findViewById(R.id.noresult);
        imageAlbum= findViewById(R.id.imageAlbumDetail);

        nameSingerAlbum= (TextView) findViewById(R.id.nameSingerAlbum);
        btnback= findViewById(R.id.btnback);
        recyclerAlbumDetail= findViewById(R.id.recyclerAblumDetail);
        recyclerAlbumDetail.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(AlbumMP3.this);
        recyclerAlbumDetail.setLayoutManager(layoutManager);

        btnback.setOnClickListener(v -> finish());
        Intent intent=getIntent();
        if (intent.hasExtra("album")){
            try {
                Picasso.get().load(intent.getStringExtra("imageAlbum")).into(imageAlbum);
                nameAlbum.setText(intent.getStringExtra("nameAlbum"));
                nameSingerAlbum.setText(intent.getStringExtra("nameSinger"));

                int id= intent.getIntExtra("album",0);
                String url="http://192.168.43.8/api/album/"+id;

                Ion.with(AlbumMP3.this)
                        .load(url)
                        .asJsonArray()
                        .setCallback(new FutureCallback<JsonArray>() {
                            @Override
                            public void onCompleted(Exception e, JsonArray result) {
                                listSongs.clear();
                                for (int i=0; i<result.size(); i++){
                                    JsonObject object= result.get(i).getAsJsonObject();
                                    ListSong listSong= new ListSong();
                                    listSong.setImageSong(object.get("ImageSong").getAsString());
                                    listSong.setNameSong(object.get("NameSong").getAsString());
                                    listSong.setNameSinger(object.get("NameSinger").getAsString());
                                    listSong.setNumberLove(object.get("Love").getAsString());
                                    listSong.setUrlSong(object.get("LinkSong").getAsString());
                                    listSongs.add(listSong);

                                }
                                if (result.size()==0){
                                    noresult.setVisibility(View.VISIBLE);
                                }
                                adapterListMp3= new ListMp3Adapter(AlbumMP3.this, listSongs);
                                recyclerAlbumDetail.setAdapter(adapterListMp3);
                                adapterListMp3.notifyDataSetChanged();
                            }
                        });

            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}