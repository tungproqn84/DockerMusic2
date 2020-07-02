package com.example.dockermusic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dockermusic.Activity.CategoryActivity;
import com.example.dockermusic.Model.ListCategory;
import com.example.dockermusic.R;
import com.squareup.picasso.Picasso;

import java.util.List;
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<ListCategory> listCategories;
    private Context context;

    public CategoryAdapter(List<ListCategory> listCategories, Context context) {
        this.listCategories = listCategories;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListCategory listCategory= listCategories.get(position);
        holder.nameCategory.setText(listCategory.getNameCategory());
        Picasso.get().load(listCategory.getImageCategory()).into(holder.imageCategory);

        holder.itemView.setOnClickListener(v -> {
            Intent intent= new Intent(context, CategoryActivity.class);
            intent.putExtra("id", listCategory.getIdCategory());
            intent.putExtra("name", listCategory.getNameCategory());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameCategory;
        private ImageView imageCategory;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameCategory= itemView.findViewById(R.id.nameCategory);
            imageCategory= itemView.findViewById(R.id.imageCategory);
        }
    }
}
