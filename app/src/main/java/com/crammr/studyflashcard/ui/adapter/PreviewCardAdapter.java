package com.crammr.studyflashcard.ui.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crammr.studyflashcard.R;
import com.crammr.studyflashcard.data.local.entity.Flashcard;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class PreviewCardAdapter extends RecyclerView.Adapter<PreviewCardAdapter.ViewHolder> {

    private List<Flashcard> cards = new ArrayList<>();

    public PreviewCardAdapter(List<Flashcard> cards) {
        this.cards = cards != null ? cards : new ArrayList<>();
    }

    public List<Flashcard> getCards() {
        return cards;
    }

    public void addCard(Flashcard card) {
        cards.add(card);
        notifyItemInserted(cards.size() - 1);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_preview_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Flashcard card = cards.get(position);
        holder.tvIndex.setText("CARD #" + (position + 1));
        holder.etQuestion.setText(card.getQuestion());
        holder.etAnswer.setText(card.getAnswer());

        holder.etQuestion.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                card.setQuestion(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        holder.etAnswer.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                card.setAnswer(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        holder.btnRemove.setOnClickListener(v -> {
            int currentPos = holder.getAdapterPosition();
            if (currentPos != RecyclerView.NO_POSITION && currentPos < cards.size()) {
                cards.remove(currentPos);
                notifyItemRemoved(currentPos);
                notifyItemRangeChanged(currentPos, cards.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvIndex;
        TextInputEditText etQuestion, etAnswer;
        ImageView btnRemove;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIndex = itemView.findViewById(R.id.tvPreviewIndex);
            etQuestion = itemView.findViewById(R.id.etPreviewQuestion);
            etAnswer = itemView.findViewById(R.id.etPreviewAnswer);
            btnRemove = itemView.findViewById(R.id.btnRemovePreviewCard);
        }
    }
}
