package com.danezah.angelhearts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AngelAdapter extends RecyclerView.Adapter<AngelAdapter.AngelViewHolder> {

    private List<String> angels;

    public void setAngels(List<String> angels) {
        this.angels = angels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AngelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_angel, parent, false);
        return new AngelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AngelViewHolder holder, int position) {
        holder.bind(angels.get(position));
    }

    @Override
    public int getItemCount() {
        return angels == null ? 0 : angels.size();
    }

    static class AngelViewHolder extends RecyclerView.ViewHolder {
        private final TextView angelName;

        public AngelViewHolder(@NonNull View itemView) {
            super(itemView);
            angelName = itemView.findViewById(R.id.angelName);
        }

        public void bind(String angel) {
            angelName.setText(angel);
        }
    }
}
