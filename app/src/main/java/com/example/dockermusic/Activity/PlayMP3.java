package com.example.dockermusic.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;
import com.example.dockermusic.Model.ListSong;
import com.example.dockermusic.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
public class PlayMP3 extends AppCompatActivity {
    private static final int PEMISSION_STORE_CODE = 1000;
    private TextView nameSong, nameSinger, timecurrent, timemax;
    private ImageView imageSong,previousButton, playButton, nextButton, btnback, btndownload, btnlove;
    private ObjectAnimator objectAnimator;
    public MediaPlayer player= new MediaPlayer();
    private SeekBar sktime;
    private int startTime=0;
    private Handler myHandler = new Handler();
    private int position=0, idsong=0;
    private ArrayList<ListSong> listSongs;
    private String urlSong="", name="";
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_playmp3);
        nameSong= findViewById(R.id.nameSongPlay);
        nameSinger= findViewById(R.id.nameSingerPlay);
        timecurrent= findViewById(R.id.timecurrent);
        timemax= findViewById(R.id.timesong);
        imageSong= findViewById(R.id.imageSongPlay);
        btnback= findViewById(R.id.btnback);
        btndownload= findViewById(R.id.download);
        btnlove= findViewById(R.id.btnlove);
        previousButton= findViewById(R.id.previvous);
        playButton= findViewById(R.id.play);
        nextButton= findViewById(R.id.next);
        sktime= findViewById(R.id.sktime);
        Intent intent= getIntent();
        listSongs= new ArrayList<>();


        if (intent.hasExtra("mp3")){
            ListSong listSong = intent.getParcelableExtra("mp3");
            nameSong.setText(listSong.getNameSong());
            nameSinger.setText(listSong.getNameSinger());
            Picasso.get().load(listSong.getImageSong()).into(imageSong);
            urlSong= listSong.getUrlSong();
            name= listSong.getNameSong();
            idsong= listSong.getId();
            position= intent.getIntExtra("position",0);
            Log.d("position", ""+position);
            //set animation for image song
            Animation();

            //play song

            new Playmp3().execute(listSong.getUrlSong());
            playButton.setImageResource(R.drawable.pause);

            // check for the internet

            StrictMode.ThreadPolicy policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            // event click button play music

            player.setOnCompletionListener(mp -> {
                player.stop();
                try {
                    player.prepare();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                player.setOnPreparedListener(mp1 -> player.start());
            });

            playButton.setOnClickListener(v -> {
                if (player.isPlaying()){
                    player.pause();
                    objectAnimator.pause();
                    playButton.setImageResource(R.drawable.play);
                }
                else {
                    player.start();
                    objectAnimator.resume();
                    playButton.setImageResource(R.drawable.pause);
                }
            });

            // event click button next song

            nextButton.setOnClickListener(v -> {
                if (intent.hasExtra("listmp3")){
                    listSongs= intent.getParcelableArrayListExtra("listmp3");
                    ListSong listSong1= listSongs.get(position+1);
                    nameSong.setText(listSong1.getNameSong());
                    nameSinger.setText(listSong1.getNameSinger());
                    Picasso.get().load(listSong1.getImageSong()).into(imageSong);
                    urlSong= listSong1.getUrlSong();
                    name= listSong1.getNameSong();
                    idsong= listSong1.getId();
                    position= position+1;
                    player.stop();
                    player.reset();
                    new Playmp3().execute(listSong1.getUrlSong());
                    playButton.setImageResource(R.drawable.pause);
                    Animation();
            }
                else
                    Toast.makeText(this, "Không thể lấy dữ liệu", Toast.LENGTH_SHORT).show();
            });

            //event clicck previous button

            previousButton.setOnClickListener(v -> {
                if (position==0){
                    player.stop();
                    try {
                        player.prepare();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    player.setOnPreparedListener(mp1 -> player.start());
                }
                else {
                    if (intent.hasExtra("listmp3")) {
                        listSongs = intent.getParcelableArrayListExtra("listmp3");
                        ListSong listSong1 = listSongs.get(position + 1);
                        nameSong.setText(listSong1.getNameSong());
                        nameSinger.setText(listSong1.getNameSinger());
                        Picasso.get().load(listSong1.getImageSong()).into(imageSong);
                        urlSong = listSong1.getUrlSong();
                        name = listSong1.getNameSong();
                        idsong = listSong1.getId();
                        position = position + 1;
                        player.stop();
                        player.reset();
                        new Playmp3().execute(listSong1.getUrlSong());
                        playButton.setImageResource(R.drawable.pause);
                        Animation();
                    } else
                        Toast.makeText(this, "Không thể lấy dữ liệu", Toast.LENGTH_SHORT).show();
                }
            });

            //event seekbar change

            sktime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                int progressChangedValue=0;
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    progressChangedValue= progress;
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    player.seekTo(progressChangedValue);
                }
            });

            //event click love music

            btnlove.setOnClickListener(v -> {
                String url= "http://192.168.43.8/api/love/"+idsong;
                Toast.makeText(this, ""+idsong, Toast.LENGTH_SHORT).show();
                Ion.with(PlayMP3.this)
                        .load(url)
                        .asJsonArray()
                        .setCallback((e, result) -> {
                            Toast.makeText(this, "Đã thích", Toast.LENGTH_SHORT).show();
                            btnlove.setImageResource(R.drawable.activeheart);
                            btnlove.setEnabled(false);
                        });
            });

            //download mp3
            btndownload.setOnClickListener(v -> {
                //check permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                        String [] permission= {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permission, PEMISSION_STORE_CODE);
                    }
                    else {
                        startDowload();
                    }
                }
                else {
                    startDowload();
                }
            });
        }
        //back button
        btnback.setOnClickListener(v -> {
            player.stop();
            Intent intent1= new Intent(PlayMP3.this, Homepage.class);
            startActivity(intent1);
        });

    }
    // download file
    private void Animation(){
        objectAnimator= ObjectAnimator.ofFloat(imageSong,"rotation",0f,360f);
        objectAnimator.setDuration(10000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void startDowload() {

        DownloadManager.Request request= new DownloadManager.Request(Uri.parse(urlSong));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle(name);
        request.setDescription("Đang tải...");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/"+System.currentTimeMillis());

        //get download service and enqueue file
        DownloadManager manager= (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);

        //
    }
    //

    //love
    //get and set time current
    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = player.getCurrentPosition();
            SimpleDateFormat simpleDateFormat= new SimpleDateFormat("mm:ss");
            timecurrent.setText(simpleDateFormat.format(startTime));
            sktime.setProgress((int)startTime);
            myHandler.postDelayed(this, 100);
        }
    };
    // load data play music from server
    class Playmp3 extends AsyncTask<String, Void, String>{
        final ProgressDialog dialog= new ProgressDialog(PlayMP3.this);
        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Đang tải...");
            dialog.show();
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected void onPostExecute(String song) {
            super.onPostExecute(song);
            try {
                player.setAudioAttributes(
                        new AudioAttributes
                        .Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                            );
                player.setDataSource(song);
                player.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            dialog.dismiss();
            player.start();
            TimeSong();
        }
    }
    private void TimeSong() {
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("mm:ss");
        timemax.setText(simpleDateFormat.format(player.getDuration()));
        sktime.setMax(player.getDuration());
        startTime= player.getCurrentPosition();
        timecurrent.setText(simpleDateFormat.format(startTime));
        sktime.setProgress((int)startTime);
        myHandler.postDelayed(UpdateSongTime,100);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PEMISSION_STORE_CODE:{
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    startDowload();
                }
                else {
                    Toast.makeText(this, "Truy cập bộ nhớ bị từ chối !", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}