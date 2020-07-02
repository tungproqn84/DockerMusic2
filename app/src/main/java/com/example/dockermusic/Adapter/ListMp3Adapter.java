package com.example.dockermusic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dockermusic.Activity.PlayMP3;
import com.example.dockermusic.Model.ListSong;
import com.example.dockermusic.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListMp3Adapter extends RecyclerView.Adapter<ListMp3Adapter.ViewHolder> {
    private Context context;
    private ArrayList<ListSong> listSongs;
    public ListMp3Adapter(Context context, ArrayList<ListSong> listSongs) {
        this.context = context;
        this.listSongs = listSongs;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_music, parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListSong listSong= listSongs.get(position);
        holder.nameSong.setText(listSong.getNameSong());
        holder.nameSinger.setText(listSong.getNameSinger());
        holder.numberLove.setText(listSong.getNumberLove());
        Picasso.get().load(listSongs.get(position).getImageSong()).into(holder.imageSong);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayMP3.class);
                intent.putParcelableArrayListExtra("listmp3", listSongs);
                intent.putExtra("position",position);
                intent.putExtra("mp3", listSongs.get(position));
                context.startActivity(intent);
                listSongs.clear();
            }
        });
    }
    @Override
    public int getItemCount() {
        return listSongs.size();
    }
    public class
    ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameSong;
        private TextView nameSinger;
        private TextView numberLove;
        private ImageView imageSong;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameSong= itemView.findViewById(R.id.nameSong);
            nameSinger= itemView.findViewById(R.id.nameSinger);
            numberLove= itemView.findViewById(R.id.numberlove);
            imageSong= itemView.findViewById(R.id.imageSong);
        }
    }
}
