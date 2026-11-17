package com.crammr.studyflashcard.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crammr.studyflashcard.R;
import com.crammr.studyflashcard.data.local.entity.Reminder;
import com.crammr.studyflashcard.utils.DateUtils;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {

    private List<Reminder> reminders = new ArrayList<>();
    private final OnReminderToggleListener listener;

    public interface OnReminderToggleListener {
        void onToggle(Reminder reminder, boolean isEnabled);
    }

    public ReminderAdapter(OnReminderToggleListener listener) {
        this.listener = listener;
    }

    public void setReminders(List<Reminder> list) {
        this.reminders = list != null ? list : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reminder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reminder item = reminders.get(position);
        holder.tvTitle.setText(item.getTitle());
        String info = item.getRepeatType() + " at " + DateUtils.formatTimeOnly(item.getScheduledTimestamp())
                + " • " + (item.getCollectionName() != null ? item.getCollectionName() : "General");
        holder.tvTime.setText(info);
        holder.switchReminder.setChecked(item.isEnabled());

        holder.switchReminder.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (listener != null) {
                listener.onToggle(item, isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reminders.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvTime;
        SwitchMaterial switchReminder;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvReminderTitle);
            tvTime = itemView.findViewById(R.id.tvReminderTime);
            switchReminder = itemView.findViewById(R.id.switchReminder);
        }
    }
}
