package com.mcuadrada.galeria;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder>{


    Context mContext;
    List<String> listUriImage;

    public GalleryAdapter(Context mContext, List<String> listUriImage) {
        this.mContext = mContext;
        this.listUriImage = listUriImage;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String uriImage = listUriImage.get(position);

        Glide.with(mContext)
                .load(new File(uriImage))
                .into(holder.imgItemGallery);

        holder.tvTtile.setText(uriImage);
    }

    @Override
    public int getItemCount() {
        return listUriImage.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgItemGallery;
        TextView tvTtile;

        public ViewHolder(View itemView) {
            super(itemView);

            imgItemGallery = (ImageView) itemView.findViewById(R.id.imgItemGallery);
            tvTtile = (TextView) itemView.findViewById(R.id.tvTitle);
        }
    }

}
