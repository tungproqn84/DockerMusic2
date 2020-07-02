package com.example.dockermusic.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.dockermusic.Adapter.AlbumAdapter;
import com.example.dockermusic.Adapter.ListMp3Adapter;
import com.example.dockermusic.Model.ListAlbum;
import com.example.dockermusic.Model.ListSong;
import com.example.dockermusic.R;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

public class BXH extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<ListAlbum> listAlbums =new ArrayList<>();
    private ListMp3Adapter adapterListMp3;
    private AlbumAdapter albumAdapter;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bxh);
        recyclerView= findViewById(R.id.recyclerBXH);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(BXH.this);
        recyclerView.setLayoutManager(layoutManager);
        //
        tabLayout= findViewById(R.id.tablayout);
        listmp3();
//        clickItemTab();
    }
    public void listmp3(){
        String URL= "http://192.168.43.8/api/listmp3";
        Ion.with(this)
                .load(URL)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        List<ListSong> listSongs;
                        listSongs= new ArrayList<>();
                        try {
                            for (int i = 0; i < result.size(); i++) {
                                JsonObject object = result.get(i).getAsJsonObject();
                                ListSong listSong = new ListSong();
                                listSong.setNameSong(object.get("NameSong").getAsString());
                                listSong.setNameSinger(object.get("NameSinger").getAsString());
                                listSong.setNumberLove(object.get("Love").getAsString());
                                listSong.setImageSong(object.get("ImageSong").getAsString());
                                listSong.setUrlSong(object.get("LinkSong").getAsString());
                                listSongs.add(listSong);
                            }
                            adapterListMp3= new ListMp3Adapter(BXH.this, (ArrayList<ListSong>) listSongs);
                            recyclerView.setAdapter(adapterListMp3);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            Toast.makeText(BXH.this, "Không thể lấy dữ liệu", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
    //list album
    public void listAlbum(){
        String url= "http://192.168.43.8/api/album";
        Ion.with(BXH.this)
                .load(url)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        try {
                            for (int i=0;i<result.size();i++){
                                JsonObject object= result.get(i).getAsJsonObject();
                                ListAlbum listAlbum= new ListAlbum();
                                listAlbum.setNameAlbum(object.get("NameAlbum").getAsString());
                                listAlbum.setNameAlbumSinger(object.get("NameSinger").getAsString());
                                listAlbum.setImageAlbum(object.get("ImageAlbum").getAsString());
                                listAlbums.add(listAlbum);
                                albumAdapter.notifyDataSetChanged();
                            }
                        }catch (Exception ex){
                            ex.printStackTrace();
                            Toast.makeText(BXH.this, "Không thể lấy dữ liệu", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        listAlbums= new ArrayList<>();
        albumAdapter= new AlbumAdapter(BXH.this, listAlbums);
        recyclerView.setAdapter(albumAdapter);
    }
    //tab item click listener
//    public void clickItemTab(){
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                switch (tab.getPosition()){
//                    case 0: recyclerView.setAdapter(adapterListMp3);
//                        listmp3();
//                    case 1:{
//                        recyclerView.setAdapter(albumAdapter);
//                        listAlbum();
//                    }
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//            }
//        });
//    }
}