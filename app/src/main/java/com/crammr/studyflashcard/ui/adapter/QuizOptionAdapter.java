package com.crammr.studyflashcard.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.crammr.studyflashcard.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class QuizOptionAdapter extends RecyclerView.Adapter<QuizOptionAdapter.ViewHolder> {

    private List<String> options = new ArrayList<>();
    private int selectedIndex = -1;
    private int correctIndex = -1;
    private boolean isSubmitted = false;
    private final OnOptionSelectedListener listener;

    public interface OnOptionSelectedListener {
        void onSelected(int index);
    }

    public QuizOptionAdapter(OnOptionSelectedListener listener) {
        this.listener = listener;
    }

    public void setOptions(List<String> options, int selectedIndex, int correctIndex, boolean isSubmitted) {
        this.options = options != null ? options : new ArrayList<>();
        this.selectedIndex = selectedIndex;
        this.correctIndex = correctIndex;
        this.isSubmitted = isSubmitted;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quiz_option, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        String text = options.get(position);
        char prefix = (char) ('A' + position);

        holder.tvPrefix.setText(String.valueOf(prefix));
        holder.tvText.setText(text);

        if (!isSubmitted) {
            if (selectedIndex == position) {
                holder.card.setStrokeColor(ContextCompat.getColor(context, R.color.primary_ice_cyan));
                holder.card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.surface_elevated));
                holder.tvPrefix.setTextColor(ContextCompat.getColor(context, R.color.surface_night));
                holder.tvPrefix.setBackgroundResource(R.drawable.bg_circle_primary);
            } else {
                holder.card.setStrokeColor(ContextCompat.getColor(context, R.color.surface_elevated));
                holder.card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.surface_card));
                holder.tvPrefix.setTextColor(ContextCompat.getColor(context, R.color.primary_ice_cyan));
                holder.tvPrefix.setBackgroundResource(R.drawable.bg_circle_badge);
            }
            holder.ivStatus.setVisibility(View.GONE);
        } else {
            // After submit/instant feedback
            if (position == correctIndex) {
                holder.card.setStrokeColor(ContextCompat.getColor(context, R.color.status_success));
                holder.card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.status_success_bg));
                holder.ivStatus.setVisibility(View.VISIBLE);
                holder.ivStatus.setImageResource(R.drawable.ic_checkmark);
                holder.ivStatus.setColorFilter(ContextCompat.getColor(context, R.color.status_success));
            } else if (position == selectedIndex && selectedIndex != correctIndex) {
                holder.card.setStrokeColor(ContextCompat.getColor(context, R.color.status_error));
                holder.card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.status_error_bg));
                holder.ivStatus.setVisibility(View.VISIBLE);
                holder.ivStatus.setImageResource(R.drawable.ic_delete);
                holder.ivStatus.setColorFilter(ContextCompat.getColor(context, R.color.status_error));
            } else {
                holder.card.setStrokeColor(ContextCompat.getColor(context, R.color.surface_elevated));
                holder.card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.surface_card));
                holder.ivStatus.setVisibility(View.GONE);
            }
        }

        holder.itemView.setOnClickListener(v -> {
            if (!isSubmitted && listener != null) {
                listener.onSelected(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView card;
        TextView tvPrefix, tvText;
        ImageView ivStatus;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.cardQuizOption);
            tvPrefix = itemView.findViewById(R.id.tvOptionPrefix);
            tvText = itemView.findViewById(R.id.tvOptionText);
            ivStatus = itemView.findViewById(R.id.ivOptionStatus);
        }
    }
}
