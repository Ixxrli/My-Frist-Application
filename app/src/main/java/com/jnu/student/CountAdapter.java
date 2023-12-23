package com.jnu.student;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jnu.student.content.MyBill;

import java.util.ArrayList;

public class CountAdapter extends RecyclerView.Adapter<CountAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<MyBill> bill_All;
    public CountAdapter(Context context,ArrayList<MyBill> bill_All){
        this.context = context;
        this.bill_All = bill_All;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.item_count,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyBill bill = bill_All.get(position);
        holder.billTitle.setText(bill.getbTitle());
        holder.billTime.setText(bill.getbTime());
        if(bill.getbType().equals("任务")){
            holder.billNum.setText("+"+bill.getbPoint());
            holder.billNum.setTextColor(Color.parseColor("#87CEFA"));
        }
        else{
            holder.billNum.setText("-"+bill.getbPoint());
            holder.billNum.setTextColor(Color.parseColor("#FFC0CB"));
        }

    }

    @Override
    public int getItemCount() {
        return bill_All.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView billTitle;
        public TextView billTime;
        public TextView billNum;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            billTitle = itemView.findViewById(R.id.bill_item_title);
            billTime = itemView.findViewById(R.id.bill_time);
            billNum = itemView.findViewById(R.id.bill_num);
        }
    }
}
