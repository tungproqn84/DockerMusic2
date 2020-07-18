package com.example.dockermusic.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.dockermusic.Adapter.AlbumAdapter;
import com.example.dockermusic.Adapter.BannerAdapter;
import com.example.dockermusic.Adapter.CategoryAdapter;
import com.example.dockermusic.Adapter.ListMp3Adapter;
import com.example.dockermusic.Model.ListAlbum;
import com.example.dockermusic.Model.ListBanner;
import com.example.dockermusic.Model.ListCategory;
import com.example.dockermusic.Model.ListSong;
import com.example.dockermusic.R;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;
public class Homepage extends AppCompatActivity {
    private List<ListBanner> listBanners;
    private BannerAdapter bannerAdapter;
    private RecyclerView recyclerViewSong, recyclerViewAlbum, recyclerViewCategory;
    private ArrayList<ListSong> listSongs= new ArrayList<>();
    private List<ListAlbum> listAlbums= new ArrayList<>();
    private List<ListCategory> listCategories= new ArrayList<>();
    private CategoryAdapter categoryAdapter;
    private ListMp3Adapter adapterListMp3;
    private AlbumAdapter albumAdapter;
    private TabLayout tabLayout;
    private String urlSong, urlAlbum, urlCategory;
    private TextView tvAlbum, tvSong, tvCategory;
    private ImageView banner, btnsearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);

        banner= findViewById(R.id.slideshow);
        listBanners= new ArrayList<>();
        bannerAdapter= new BannerAdapter(listBanners, Homepage.this);
//        getbanner();
        //
        tvAlbum= findViewById(R.id.tvAlbum);
        tvSong= findViewById(R.id.tvSong);
        tvCategory= findViewById(R.id.tvCategoryHot);
        //
        recyclerViewSong= findViewById(R.id.recyclersongHot);
        recyclerViewSong.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(Homepage.this);
        recyclerViewSong.setLayoutManager(layoutManager);
        adapterListMp3= new ListMp3Adapter(this, listSongs);
        recyclerViewSong.setAdapter(adapterListMp3);
        urlSong= constant.urlSongHome;
        listmp3(urlSong);
        //album hot
        recyclerViewAlbum= findViewById(R.id.recyclerAblumHot);
        recyclerViewAlbum.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager1= new GridLayoutManager(this, 3);
        recyclerViewAlbum.setLayoutManager(layoutManager1);
        albumAdapter= new AlbumAdapter(this, listAlbums);
        recyclerViewAlbum.setAdapter(albumAdapter);
        urlAlbum= constant.urlAlbumHome;
        listAlbum(urlAlbum);
        //category hot
        recyclerViewCategory= findViewById(R.id.recyclerCategoryHot);
        recyclerViewCategory.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager2= new GridLayoutManager(this, 2);
        recyclerViewCategory.setLayoutManager(layoutManager2);
        categoryAdapter= new CategoryAdapter(listCategories, Homepage.this);
        recyclerViewCategory.setAdapter(categoryAdapter);
        urlCategory=constant.urlCategoryHome;
        listCategory(urlCategory);

        //
        tabLayout= findViewById(R.id.tablayout);
        clickItemTab();

        //search
        btnsearch= findViewById(R.id.btnsearch);
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Homepage.this, SearchActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    //get mp3 hot
    private void listmp3(String urlSong) {
        Ion.with(this)
                .load(urlSong)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        try {
                            for (int i = 0; i < result.size(); i++) {
                                JsonObject object = result.get(i).getAsJsonObject();
                                ListSong listSong = new ListSong();
                                listSong.setId(object.get("IDSong").getAsInt());
                                listSong.setNameSong(object.get("NameSong").getAsString());
                                listSong.setNameSinger(object.get("NameSinger").getAsString());
                                listSong.setNumberLove(object.get("Love").getAsString());
                                listSong.setImageSong(object.get("ImageSong").getAsString());
                                listSong.setUrlSong(object.get("LinkSong").getAsString());
                                listSongs.add(listSong);
                            }
                            adapterListMp3.notifyDataSetChanged();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }
                });
    }
    //get album hot
    public void listAlbum(String urlAlbum){
        Ion.with(this)
                .load(urlAlbum)
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
                                listAlbum.setId(object.get("IDAlbum").getAsInt());
                                listAlbums.add(listAlbum);

                            }
                            albumAdapter.notifyDataSetChanged();
                        }catch (Exception ex){
                            ex.printStackTrace();
                            Toast.makeText(Homepage.this, "Không thể lấy dữ liệu", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //get list category
    public void listCategory(String url){
        Ion.with(this)
                .load(url)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        try {
                            for (int i=0;i<result.size();i++){
                                JsonObject object= result.get(i).getAsJsonObject();
                                ListCategory listCategory= new ListCategory();
                                listCategory.setIdCategory(object.get("IDCategory").getAsInt());
                                listCategory.setNameCategory(object.get("NameCategory").getAsString());
                                listCategory.setImageCategory(object.get("ImageCategory").getAsString());
                                listCategories.add(listCategory);
                            }
                            categoryAdapter.notifyDataSetChanged();
                        }catch (Exception ex){
                            ex.printStackTrace();
                            Toast.makeText(Homepage.this, "Không thể lấy dữ liệu", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void clickItemTab(){
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        banner.setVisibility(View.VISIBLE);
                        listSongs.clear();
                        listAlbums.clear();
                        listCategories.clear();

                        tvAlbum.setVisibility(View.VISIBLE);
                        recyclerViewAlbum.setVisibility(View.VISIBLE);

                        tvSong.setVisibility(View.VISIBLE);
                        recyclerViewSong.setVisibility(View.VISIBLE);

                        tvCategory.setVisibility(View.VISIBLE);
                        recyclerViewCategory.setVisibility(View.VISIBLE);

                        urlSong= constant.urlSongHome;
                        listmp3(urlSong);

                        urlAlbum= constant.urlAlbumHome;
                        listAlbum(urlAlbum);

                        urlCategory= constant.urlCategoryHome;
                        listCategory(urlCategory);
                        break;
                    case 1:{
                        banner.setVisibility(View.VISIBLE);
                        listSongs.clear();
                        listAlbums.clear();
                        listCategories.clear();

                        tvSong.setVisibility(View.GONE);
                        recyclerViewSong.setVisibility(View.GONE);

                        tvCategory.setVisibility(View.GONE);
                        recyclerViewCategory.setVisibility(View.GONE);

                        tvAlbum.setVisibility(View.VISIBLE);
                        recyclerViewAlbum.setVisibility(View.VISIBLE);
                        urlAlbum= constant.urlAlbum;
                        listAlbum(urlAlbum);
                        break;

                    }
                    case 2:{
                        listCategories.clear();
                        banner.setVisibility(View.VISIBLE);
                        listSongs.clear();
                        listAlbums.clear();

                        tvAlbum.setVisibility(View.GONE);
                        recyclerViewAlbum.setVisibility(View.GONE);

                        tvCategory.setVisibility(View.GONE);
                        recyclerViewCategory.setVisibility(View.GONE);

                        tvSong.setVisibility(View.VISIBLE);
                        recyclerViewSong.setVisibility(View.VISIBLE);

                        urlSong= constant.urlSong;
                        listmp3(urlSong);
                        break;
                    }
                    case 3:{
                        listCategories.clear();
                        banner.setVisibility(View.GONE);
                        listSongs.clear();
                        listAlbums.clear();

                        tvAlbum.setVisibility(View.GONE);
                        recyclerViewAlbum.setVisibility(View.GONE);

                        tvSong.setVisibility(View.GONE);
                        recyclerViewSong.setVisibility(View.GONE);

                        tvCategory.setVisibility(View.VISIBLE);
                        recyclerViewCategory.setVisibility(View.VISIBLE);
                        String url= constant.urlCategory;
                        listCategory(url);
                        break;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
