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
import com.example.dockermusic.Model.ListSong;
import com.example.dockermusic.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    private TextView nameCategory, noresult;
    private ImageView btnback;
    private RecyclerView recyclerCategoryDetail;
    private ArrayList<ListSong> listSongs= new ArrayList<>();
    private ListMp3Adapter adapterListMp3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_detail_category);

        nameCategory= findViewById(R.id.nameCategoryDetail);
        btnback= findViewById(R.id.btnback);
        noresult= findViewById(R.id.noresult);
        recyclerCategoryDetail= findViewById(R.id.recyclerCategoryDetail);
        recyclerCategoryDetail.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(CategoryActivity.this);
        recyclerCategoryDetail.setLayoutManager(layoutManager);

        btnback.setOnClickListener(v -> finish());
        Intent intent=getIntent();

            try {
                nameCategory.setText(intent.getStringExtra("name"));
                int id= intent.getIntExtra("id",0);
                String url=constant.urlCategory+id;

                Ion.with(CategoryActivity.this)
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
                                adapterListMp3= new ListMp3Adapter(CategoryActivity.this, listSongs);
                                recyclerCategoryDetail.setAdapter(adapterListMp3);
                                adapterListMp3.notifyDataSetChanged();
                            }
                        });

            }catch (Exception e){
                e.printStackTrace();
            }

        }
}