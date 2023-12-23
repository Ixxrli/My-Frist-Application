package com.jnu.student;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jnu.student.content.MyReward;
import com.jnu.student.data.RewardStorage;
import com.jnu.student.data.RewardStorageItem;
import com.jnu.student.data.TimeUtil;


import java.util.ArrayList;

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.MyViewHolder>{
    private final String FILE_Reward_NAME = "tData";
    private RewardStorage rewardStorage;
    private final Context context;
    private final ArrayList<MyReward> list ;
    private final ArrayList<MyReward> Rtype;

    public RewardAdapter(Context context, ArrayList<MyReward> list,ArrayList<MyReward> type){
        this.context = context;
        this.list=list;
        this.Rtype=type;
        this.rewardStorage=new RewardStorageItem();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.item_reward,parent,false);
        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull RewardAdapter.MyViewHolder holder, int position) {
        MyReward myReward = list.get(position);
        holder.RTitle.setText(myReward.getTitle());
        String result = myReward.getNumFinish()+((myReward.getType().equals("单次"))?"/1":"/∞");
        result = "-"+myReward.getPoint();
        holder.RNumFinish.setText(result);
        holder.ReditPoint.setText(result);
        holder.ReditPoint.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("获取奖励");
                builder.setMessage("你确定消耗"+myReward.getPoint()+"积分吗？");
                // 如果按下确定
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean finalIsOnce = myReward.getType().equals("单次");
                        // 如果奖励为不限次，或者奖励为一次并且还没使用
                        if((!finalIsOnce) || (finalIsOnce && myReward.getNumFinish() == 0)){
                            // 压栈，记录这一次的消费时间
                            myReward.getRTurnover().push(TimeUtil.getBeijingTime());
                            // 改变reward数据，并且进行界面更新
                            myReward.setNumFinish(myReward.getNumFinish()+1);
                            if(myReward.getType().equals("单次")){
                                myReward.setState("已完成");
                                Rtype.remove(myReward);
                                notifyItemRemoved(holder.getAdapterPosition());
                            }
                            else{
                                holder.RNumFinish.setText(myReward.getNumFinish()+"/∞");
                            }
                            // 保存数据
                            rewardStorage.SaveRewardItems(context, FILE_Reward_NAME,list);
                            // 发布广播，通知更新金币数量
                            Intent intent = new Intent("MY_CUSTOM_ACTION");
                            intent.putExtra("data", "奖励-"+myReward.getPoint());
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        }
                    }
                });
                // 如果按下否定，什么都不做
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        public int menuid;
        ImageView Image;
        TextView RTitle;
        TextView RNumFinish;
        TextView ReditPoint;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.icon);
            RTitle = itemView.findViewById(R.id.reward_item_title);
            RNumFinish = itemView.findViewById(R.id.reward_complete_num);
            ReditPoint=itemView.findViewById(R.id.reward_num_add);
            // 设置监听者为自己
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(menuid,0,this.getAdapterPosition(),"修改");
            menu.add(menuid,1,this.getAdapterPosition(),"删除");
        }
    }
}
