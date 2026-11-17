package com.crammr.studyflashcard.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crammr.studyflashcard.R;
import com.crammr.studyflashcard.data.local.entity.FlashcardCollection;

import java.util.ArrayList;
import java.util.List;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {

    private List<FlashcardCollection> collections = new ArrayList<>();
    private final OnCollectionClickListener listener;

    public interface OnCollectionClickListener {
        void onCollectionClick(FlashcardCollection collection);
    }

    public CollectionAdapter(OnCollectionClickListener listener) {
        this.listener = listener;
    }

    public void setCollections(List<FlashcardCollection> list) {
        this.collections = list != null ? list : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collection, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FlashcardCollection col = collections.get(position);
        holder.tvName.setText(col.getName());
        holder.tvDesc.setText(col.getDescription() != null && !col.getDescription().isEmpty()
                ? col.getDescription() : "No description provided");
        holder.tvCardCount.setText(col.getCardCount() + " Cards");
        holder.progressBar.setProgress(col.getProgress());
        holder.tvProgressPercent.setText(col.getProgress() + "%");

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCollectionClick(col);
            }
        });
    }

    @Override
    public int getItemCount() {
        return collections.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDesc, tvCardCount, tvProgressPercent;
        ProgressBar progressBar;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvCollectionName);
            tvDesc = itemView.findViewById(R.id.tvCollectionDesc);
            tvCardCount = itemView.findViewById(R.id.tvCardCountBadge);
            progressBar = itemView.findViewById(R.id.progressBar);
            tvProgressPercent = itemView.findViewById(R.id.tvProgressPercent);
        }
    }
}
