package com.crammr.studyflashcard.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crammr.studyflashcard.R;
import com.crammr.studyflashcard.data.local.entity.StudySession;
import com.crammr.studyflashcard.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class StudyHistoryAdapter extends RecyclerView.Adapter<StudyHistoryAdapter.ViewHolder> {

    private List<StudySession> sessions = new ArrayList<>();

    public void setSessions(List<StudySession> list) {
        this.sessions = list != null ? list : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_study_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StudySession item = sessions.get(position);
        holder.tvCollection.setText(item.getCollectionName() != null ? item.getCollectionName() : "Study Session");
        int percent = item.getCardsLearned() > 0 ? (item.getCardsRemembered() * 100 / item.getCardsLearned()) : 0;
        holder.tvScore.setText(percent + "% Remembered");
        holder.tvDetail.setText(item.getCardsLearned() + " cards • " + item.getDurationMinutes() + " mins");
        holder.tvDate.setText(DateUtils.formatDateTime(item.getStartTime()));
    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCollection, tvScore, tvDetail, tvDate;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCollection = itemView.findViewById(R.id.tvHistoryCollection);
            tvScore = itemView.findViewById(R.id.tvHistoryScore);
            tvDetail = itemView.findViewById(R.id.tvHistoryDetail);
            tvDate = itemView.findViewById(R.id.tvHistoryDate);
        }
    }
}
