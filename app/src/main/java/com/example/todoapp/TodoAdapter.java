package com.example.todoapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    Context mCtx;
    List<Task> taskList;

    public TodoAdapter(Context mCtx, List<Task> taskList) {
        this.mCtx = mCtx;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_tasks,viewGroup,false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int i) {
        Task t = taskList.get(i);
        holder.tvTask.setText(t.getTask());
        holder.tvDesc.setText(t.getDesc());
        holder.tvFinishBy.setText(t.getFinishBy());

        if (t.isFinished())
            holder.tvStatus.setText("Completed");
        else
            holder.tvStatus.setText("Not Completed");
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder{

        TextView tvStatus, tvTask, tvDesc, tvFinishBy;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStatus = itemView.findViewById(R.id.textViewStatus);
            tvTask = itemView.findViewById(R.id.textViewTask);
            tvDesc = itemView.findViewById(R.id.textViewDesc);
            tvFinishBy = itemView.findViewById(R.id.textViewFinishBy);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Task task = taskList.get(getAdapterPosition());

                    Intent intent = new Intent(mCtx, UpdateTaskActivity.class);
                    intent.putExtra("task", task);

                    mCtx.startActivity(intent);
                }
            });

        }
    }

}
