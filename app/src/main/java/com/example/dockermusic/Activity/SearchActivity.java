package com.example.dockermusic.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.dockermusic.Adapter.ListMp3Adapter;
import com.example.dockermusic.Model.ListSong;
import com.example.dockermusic.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import java.util.ArrayList;
import java.util.List;
public class SearchActivity extends AppCompatActivity {
    private RecyclerView recyclerViewSearch;
    private TextView tvkeyword, noresult;
    private ImageView btnsearch;
    private EditText keywordSearch;
    private ListMp3Adapter adapterListMp3;
    private ArrayList<ListSong> listSongs= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_search);

        keywordSearch= findViewById(R.id.search);
        tvkeyword= findViewById(R.id.tvkeyword);
        btnsearch= findViewById(R.id.btnSearch);
        noresult=findViewById(R.id.noresult);
        recyclerViewSearch= findViewById(R.id.resultSearch);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(SearchActivity.this);
        recyclerViewSearch.setLayoutManager(layoutManager);
        tvkeyword.setVisibility(View.GONE);
        getResultSearch();

    }

    private void getResultSearch() {
        keywordSearch.setVisibility(View.VISIBLE);
        btnsearch.setOnClickListener(new View.OnClickListener() {
            InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            public void setInputMethodManager(InputMethodManager inputMethodManager) {
                this.inputMethodManager = inputMethodManager;
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }

            @Override
            public void onClick(View v) {
                listSongs.clear();
                tvkeyword.setVisibility(View.VISIBLE);

                String keyword= keywordSearch.getText().toString();
                String url= "http://192.168.43.8/api/search/"+keyword;
                Ion.with(SearchActivity.this)
                        .load(url)
                        .asJsonArray()
                        .setCallback(new FutureCallback<JsonArray>() {
                            @Override
                            public void onCompleted(Exception e, JsonArray result) {
                                if (result.size()==0){
                                    noresult.setVisibility(View.VISIBLE);
                                }
                                else {
                                    tvkeyword.setText("Kết quả tìm kiếm: "+result.size()+" bài hát");
                                    noresult.setVisibility(View.GONE);
                                    for (int i=0; i<result.size(); i++){
                                        JsonObject object= result.get(i).getAsJsonObject();
                                        ListSong listSong= new ListSong();
                                        listSong.setUrlSong(object.get("LinkSong").getAsString());
                                        listSong.setImageSong(object.get("ImageSong").getAsString());
                                        listSong.setNameSong(object.get("NameSong").getAsString());
                                        listSong.setNameSinger(object.get("NameSinger").getAsString());
                                        listSong.setNumberLove(object.get("Love").getAsString());
                                        listSongs.add(listSong);
                                    }
                                    adapterListMp3= new ListMp3Adapter(SearchActivity.this, listSongs);
                                    recyclerViewSearch.setAdapter(adapterListMp3);
                                }
                            }
                        });

            }
        });

    }

}