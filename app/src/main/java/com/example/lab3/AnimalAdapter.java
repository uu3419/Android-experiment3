package com.example.lab3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * 实验5：RecyclerView 的 Adapter 与 ViewHolder。
 */
public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> {

    /** 简单数据模型：名称 + 图片资源 */
    public static class Animal {
        public final String name;
        public final int imageRes;

        public Animal(String name, int imageRes) {
            this.name = name;
            this.imageRes = imageRes;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Animal animal);
    }

    private final List<Animal> mData;
    private final OnItemClickListener mListener;

    public AnimalAdapter(List<Animal> data, OnItemClickListener listener) {
        this.mData = data;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_animal_card, parent, false);
        return new AnimalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalViewHolder holder, int position) {
        final Animal animal = mData.get(position);
        holder.name.setText(animal.name);
        holder.photo.setImageResource(animal.imageRes);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(animal);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class AnimalViewHolder extends RecyclerView.ViewHolder {
        final ImageView photo;
        final TextView name;

        AnimalViewHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.iv_photo);
            name = itemView.findViewById(R.id.tv_animal_name);
        }
    }
}
