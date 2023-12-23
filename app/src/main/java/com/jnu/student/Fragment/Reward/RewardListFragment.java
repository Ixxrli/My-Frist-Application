package com.jnu.student.Fragment.Reward;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.jnu.student.R;
import com.jnu.student.RewardAdapter;
import com.jnu.student.content.MyReward;
import com.jnu.student.data.RewardStorage;
import com.jnu.student.data.RewardStorageItem;
import com.jnu.student.total.RewardItemDetailActivity;


import java.util.ArrayList;


public class RewardListFragment extends Fragment {
    private int menuid;
    public String Type;
    private RecyclerView RewardR;
    private RewardStorage rewardStorage;
    private ActivityResultLauncher<Intent> updateRewardLauncher;
    public RewardAdapter RAdapter;
    private ArrayList<MyReward> list_all;
    public ArrayList<MyReward> list_type;

    private String File_Reward_Name = "RData";

    public RewardListFragment() {
        // Required empty public constructor
    }

    public RewardListFragment(String Type, int menuid) {
        this.Type = Type;
        this.menuid = menuid;
    }

    public static RewardListFragment newInstance() {
        RewardListFragment fragment = new RewardListFragment();
        Bundle T = new Bundle();
        fragment.setArguments(T);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_reward, container, false);
        rewardStorage = new RewardStorageItem();
        list_all = rewardStorage.LoadRewardItems(requireActivity().getApplicationContext(), File_Reward_Name);
        list_type = filterType(list_all, Type);

        RewardR = rootView.findViewById(R.id.reward_recyclerView);
        RAdapter = new RewardAdapter(requireActivity(), list_all,list_type);
        RewardR.setAdapter(RAdapter);
        RewardR.setLayoutManager(new LinearLayoutManager(requireActivity()));
        registerForContextMenu(RewardR);
        updateRewardLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    // 当数据准备好时
                    if (result.getResultCode() == requireActivity().RESULT_OK) {
                        Intent intent = result.getData();
                        int position = intent.getIntExtra("position", 0);
                        String title = intent.getStringExtra("title");
                        int points = Integer.parseInt(intent.getStringExtra("points"));
                        int numbers = Integer.parseInt(intent.getStringExtra("numbers"));
                        String tag = intent.getStringExtra("tag");
                        MyReward myReward = list_type.get(position);
                        myReward.setTitle(title);
                        myReward.setPoint(points);
                        myReward.setNum(numbers);

                        myReward.setTag(tag);
                        RAdapter.notifyItemChanged(position);
                        rewardStorage.SaveRewardItems(requireActivity().getApplicationContext(), File_Reward_Name, list_type);
                    }
                    // 当数据还没准备好时
                    else if (result.getResultCode() == requireActivity().RESULT_CANCELED) {

                    }
                });
        return rootView;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // 删除判断位置
        if (item.getGroupId() != menuid) {
            return false;
        }
        switch (item.getItemId()) {
            case 0:
                Intent intentUpdate = new Intent(requireActivity(), RewardItemDetailActivity.class);
                MyReward selectTask = list_type.get(item.getOrder());
                intentUpdate.putExtra("title", selectTask.getTitle());
                intentUpdate.putExtra("points", String.valueOf(selectTask.getPoint()));
                intentUpdate.putExtra("numbers", String.valueOf(selectTask.getNum()));
                intentUpdate.putExtra("tag", selectTask.getTag());
                intentUpdate.putExtra("position", item.getOrder());
                updateRewardLauncher.launch(intentUpdate);
                break;
            case 1:
                // 删除数据
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setTitle("Delete Data");
                builder.setMessage("你确定删除这个奖励？");
                // 如果按下确定
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 更新数据
                        list_all = rewardStorage.LoadRewardItems(requireActivity().getApplicationContext(), File_Reward_Name);
                        MyReward myTask = list_type.get(item.getOrder());
                        for (MyReward task : list_all) {
                            if (task.getTime().equals(myTask.getTime())) {
                                list_all.remove("已删除");
                                break;
                            }
                        }
                        rewardStorage.SaveRewardItems(requireActivity().getApplicationContext(), File_Reward_Name, list_all);
                        // 刷新界面
                        list_type.remove(item.getOrder());
                        RAdapter.notifyItemRemoved(item.getOrder());
                    }
                });
                // 如果按下否定，什么都不做
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.create().show();
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }

    private ArrayList<MyReward> filterType(ArrayList<MyReward> rewardListAll, String rewardType) {
        ArrayList<MyReward> rewardFilterType = new ArrayList<>();
        for (MyReward myReward : rewardListAll) {
            if (myReward.getType() != null&&myReward.getState() != null&&myReward.getType().equals(rewardType)&&myReward.getState().equals("正常")) {
                rewardFilterType.add(myReward);
            }
        }
        return rewardFilterType;
    }

}
