package com.jnu.student;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jnu.student.target.MyTarget;

import java.util.ArrayList;

public class TargetAdapter extends RecyclerView.Adapter<TargetAdapter.MyViewHolder>{
    private final Context c;
    private final ArrayList<MyTarget> list ;

    public TargetAdapter(Context c,ArrayList<MyTarget> list){
        this.c=c;
        this.list=list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.c).inflate(R.layout.targetitem,parent,false);
        return new MyViewHolder(view);
    }
    @SuppressLint("SetTextI18n")
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyTarget myTarget = list.get(position);
        holder.TarTitle.setText(myTarget.getTitle());
        holder.taskNumFinish.setText(myTarget.getTarNumFinish()+"/"+ myTarget.getTarNum());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        ImageView Image;
        TextView TarTitle;
        TextView taskNumFinish;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.icon);
            TarTitle = itemView.findViewById(R.id.target_item_title);
            taskNumFinish = itemView.findViewById(R.id.target_complete_num);
            // 设置监听者为自己
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(0,0,this.getAdapterPosition(),"修改"+this.getAdapterPosition());
            menu.add(0,1,this.getAdapterPosition(),"删除"+this.getAdapterPosition());
        }
    }
}
