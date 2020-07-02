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

import com.example.dockermusic.Activity.AlbumMP3;
import com.example.dockermusic.Activity.Homepage;
import com.example.dockermusic.Model.ListAlbum;
import com.example.dockermusic.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {
    private Context context;
    private List<ListAlbum> listAlbums;
    public AlbumAdapter(Context context, List<ListAlbum> listAlbums) {
        this.context = context;
        this.listAlbums = listAlbums;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_album,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListAlbum listAlbum= listAlbums.get(position);
        holder.nameAlbum.setText(listAlbum.getNameAlbum());
        holder.nameSingerAlbum.setText(listAlbum.getNameAlbumSinger());
        Picasso.get().load(listAlbums.get(position).getImageAlbum()).into(holder.imageAlbum);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, AlbumMP3.class);
                intent.putExtra("album",listAlbum.getId());
                intent.putExtra("imageAlbum", listAlbum.getImageAlbum());
                intent.putExtra("nameAlbum",listAlbum.getNameAlbum());
                intent.putExtra("nameSinger",listAlbum.getNameAlbumSinger());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listAlbums.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageAlbum;
        private TextView nameAlbum;
        private TextView nameSingerAlbum;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageAlbum= itemView.findViewById(R.id.imageAlbum);
            nameAlbum= itemView.findViewById(R.id.nameAlbum);
            nameSingerAlbum= itemView.findViewById(R.id.nameSingerAlbum);

        }
    }
}
