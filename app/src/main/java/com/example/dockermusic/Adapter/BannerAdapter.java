package com.example.dockermusic.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dockermusic.Activity.PlayMP3;
import com.example.dockermusic.Model.ListBanner;
import com.example.dockermusic.R;
import com.squareup.picasso.Picasso;
import java.util.List;
import java.util.ArrayList;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.ViewHoler> {
    private List<ListBanner> listBanners;
    private Context context;

    public BannerAdapter(List<ListBanner> listBanners, Context context) {
        this.listBanners = listBanners;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_home,parent,false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        ListBanner listBanner= listBanners.get(position);
        Picasso.get().load(listBanner.getImage()).into(holder.imageBanner);
    }

    @Override
    public int getItemCount() {
        return listBanners.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        private ImageView imageBanner;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            imageBanner= itemView.findViewById(R.id.slideshow);
        }
    }

}
