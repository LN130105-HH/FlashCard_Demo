package com.crammr.studyflashcard.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crammr.studyflashcard.R;
import com.crammr.studyflashcard.data.local.entity.Flashcard;
import com.crammr.studyflashcard.ui.main.ChatMessage;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.ViewHolder> {

    private List<ChatMessage> messages = new ArrayList<>();
    private final OnPreviewDeckListener previewListener;

    public interface OnPreviewDeckListener {
        void onPreview(List<Flashcard> cards);
    }

    public ChatMessageAdapter(OnPreviewDeckListener previewListener) {
        this.previewListener = previewListener;
    }

    public void addMessage(ChatMessage message) {
        messages.add(message);
        notifyItemInserted(messages.size() - 1);
    }

    public void setMessages(List<ChatMessage> list) {
        this.messages = list != null ? list : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatMessage msg = messages.get(position);
        if (msg.getType() == ChatMessage.TYPE_USER) {
            holder.layoutUser.setVisibility(View.VISIBLE);
            holder.layoutAi.setVisibility(View.GONE);
            holder.tvUserMsg.setText(msg.getText());
        } else {
            holder.layoutUser.setVisibility(View.GONE);
            holder.layoutAi.setVisibility(View.VISIBLE);
            holder.tvAiMsg.setText(msg.getText());

            if (msg.getGeneratedCards() != null && !msg.getGeneratedCards().isEmpty()) {
                holder.btnViewCards.setVisibility(View.VISIBLE);
                holder.btnViewCards.setText("Preview Deck (" + msg.getGeneratedCards().size() + " Cards)");
                holder.btnViewCards.setOnClickListener(v -> {
                    if (previewListener != null) {
                        previewListener.onPreview(msg.getGeneratedCards());
                    }
                });
            } else {
                holder.btnViewCards.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutUser, layoutAi;
        TextView tvUserMsg, tvAiMsg;
        MaterialButton btnViewCards;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutUser = itemView.findViewById(R.id.layoutUserMsg);
            layoutAi = itemView.findViewById(R.id.layoutAiMsg);
            tvUserMsg = itemView.findViewById(R.id.tvUserMsg);
            tvAiMsg = itemView.findViewById(R.id.tvAiMsg);
            btnViewCards = itemView.findViewById(R.id.btnViewGeneratedCards);
        }
    }
}
