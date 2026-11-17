package com.crammr.studyflashcard.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crammr.studyflashcard.R;
import com.crammr.studyflashcard.data.local.entity.Flashcard;

import java.util.ArrayList;
import java.util.List;

public class FlashcardAdapter extends RecyclerView.Adapter<FlashcardAdapter.ViewHolder> {

    private List<Flashcard> cards = new ArrayList<>();
    private final OnCardActionListener listener;

    public interface OnCardActionListener {
        void onEdit(Flashcard card);
        void onDelete(Flashcard card);
    }

    public FlashcardAdapter(OnCardActionListener listener) {
        this.listener = listener;
    }

    public void setCards(List<Flashcard> list) {
        this.cards = list != null ? list : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flashcard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Flashcard card = cards.get(position);
        holder.tvIndex.setText("CARD #" + (position + 1));
        holder.tvQuestion.setText(card.getQuestion());
        holder.tvAnswer.setText(card.getAnswer());

        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) listener.onEdit(card);
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) listener.onDelete(card);
        });
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvIndex, tvQuestion, tvAnswer;
        ImageView btnEdit, btnDelete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIndex = itemView.findViewById(R.id.tvCardIndex);
            tvQuestion = itemView.findViewById(R.id.tvQuestion);
            tvAnswer = itemView.findViewById(R.id.tvAnswer);
            btnEdit = itemView.findViewById(R.id.btnEditCard);
            btnDelete = itemView.findViewById(R.id.btnDeleteCard);
        }
    }
}
