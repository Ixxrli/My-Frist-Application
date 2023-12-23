package com.jnu.student.Fragment.Reward;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jnu.student.R;
import com.jnu.student.RewardAdapter;
import com.jnu.student.content.MyReward;
import com.jnu.student.data.RewardStorage;
import com.jnu.student.data.RewardStorageItem;
import com.jnu.student.data.TimeUtil;
import com.jnu.student.total.RewardItemDetailActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

public class RewardFragment extends Fragment {
    private final static String[] RType ={"单次", "n次"};

    private final String File_Reward_Name ="RData";

    private ArrayList<MyReward> RList_all;
    private ArrayList<MyReward> RList_type;

    private RewardStorage rewardStorage;
    public RewardAdapter rewardAdapter;
    private RecyclerView rewardRecyclerView;


    private int PointSum;

    private ActivityResultLauncher<Intent> AddRewardLauncher;
    private TextView PointSumView;
    private final BroadcastReceiver receiver=new BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction()!=null&&intent.getAction().equals("MY_CUSTOM_ACTION")){
                String data = Objects.requireNonNull(intent.getStringExtra("data")).substring(2);
                PointSum = PointSum + Integer.parseInt(data);
                if(PointSum<0){
                    PointSumView.setTextColor(Color.RED);
                }else{
                    PointSumView.setTextColor(Color.GREEN);
                }
                PointSumView.setText("  "+PointSum);
            }
        }
    };
    public RewardFragment() {
        // Required empty public constructor
    }
    public static RewardFragment newInstance() {
        RewardFragment fragment = new RewardFragment();
        Bundle T = new Bundle();
        fragment.setArguments(T);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(receiver, new IntentFilter("MY_CUSTOM_ACTION"));
    }
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_reward, container, false);
        // 数据读取
        rewardStorage = new RewardStorageItem();
        RList_all = rewardStorage.LoadRewardItems(requireActivity(), File_Reward_Name);
        RList_type = filterWithState(RList_all,"正常");
        // 设置左下角点数
        PointSum=getPointSum(RList_all);
        PointSumView=rootview.findViewById(R.id.point_sum_text);
        if(PointSum<0){
            PointSumView.setTextColor(Color.RED);
        }else{
            PointSumView.setTextColor(Color.GREEN);
        }
        PointSumView.setText("   "+PointSum);
        ViewPager2 viewPager2 = rootview.findViewById(R.id.Rview_pager);
        TabLayout tabLayout = rootview.findViewById(R.id.Rtab_layout);

        RewardFragment.PagerAdapter pagerAdapter = new RewardFragment.PagerAdapter(this);
        viewPager2.setAdapter(pagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> tab.setText("Tab " + (position + 1))
        ).attach();
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setText(RType[position])).attach();
        rewardRecyclerView = rootview.findViewById(R.id.reward_recyclerView);
        rewardAdapter = new RewardAdapter(requireActivity(),RList_all, RList_type);
        rewardRecyclerView.setAdapter(rewardAdapter);
        rewardRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        // 注册上下文菜单
        registerForContextMenu(rewardRecyclerView);

        AddRewardLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    // 当数据准备好时
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        String title =  Objects.requireNonNull(intent).getStringExtra("title");
                        int points = Integer.parseInt(Objects.requireNonNull(intent.getStringExtra("points")));
                        int numbers = Integer.parseInt(Objects.requireNonNull(intent.getStringExtra("numbers")));
                        String type = intent.getStringExtra("type");
                        String tag = intent.getStringExtra("tag");
                        // 创建 SimpleDateFormat 对象，指定日期格式和时区
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai")); // 设置时区为北京时间
                        // 格式化输出
                        MyReward myReward = new MyReward(TimeUtil.getBeijingTime(), title, points, numbers, 0, type, tag,"正常");
                        // 存储数据
                        RList_all.add(myReward);
                        List<Fragment> fragments = getChildFragmentManager().getFragments();
                        for(Fragment fragment:fragments){
                            if(fragment instanceof RewardListFragment){
                                RewardListFragment rewardListFragment = (RewardListFragment)fragment;
                                if(rewardListFragment.Type.equals(myReward.getType())) {
                                    rewardListFragment.list_type.add(myReward);
                                    rewardListFragment.RAdapter.notifyItemInserted(rewardListFragment.list_type.size());
                                    break;
                                }
                            }
                        }

                        rewardStorage.SaveRewardItems(requireActivity().getApplicationContext(), File_Reward_Name, RList_all);

                    }
                });

        ImageView addButton = rootview.findViewById(R.id.RaddButton);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), RewardItemDetailActivity.class);
            AddRewardLauncher.launch(intent);
        });
        return rootview;
    }
    private static class PagerAdapter extends FragmentStateAdapter {

        private static final int NUM_TABS = 2;

        public PagerAdapter(@NonNull Fragment fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            // 根据位置返回不同的子 Fragment
            switch (position) {
                case 0:
                    return new RewardListFragment(RType[0],0);
                case 1:
                    return new RewardListFragment(RType[1],1);
                default:
                    throw new IllegalArgumentException("Invalid position");
            }
        }
        @Override
        public int getItemCount() {
            // 返回子 Fragment 的数量
            return NUM_TABS;
        }
    }
    private int getPointSum(ArrayList<MyReward>TList){
        int sum=0;
        for(MyReward myReward:TList){
            sum-=myReward.getPoint()*myReward.getNumFinish();
        }
        return sum;
    }
    @Override
    public void onDestroy() {
        // 注销广播接收器
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(receiver);
        super.onDestroy();
    }

    public ArrayList<MyReward> filterWithState(ArrayList<MyReward> rewardListAll,String rewardState){
        ArrayList<MyReward> rewardFilterWithState = new ArrayList<>();
        for(MyReward reward:rewardListAll){
            if(reward.getState().equals(rewardState)){
                rewardFilterWithState.add(reward);
            }
        }
        return rewardFilterWithState;
    }
    public ArrayList<MyReward> filterWithTags(ArrayList<String> tagList){
        // 如果为空或者选择了全部的标签
        if(RList_all.isEmpty()){
            return RList_all;
        }
        ArrayList<MyReward> rewardListWithTag = filterWithState(RList_all,"正常");
        if(tagList.contains("全部")){
            return rewardListWithTag;
        }
        ArrayList<MyReward> rewardWithTags = new ArrayList<>();
        for(MyReward reward :rewardListWithTag){
            if(tagList.contains(reward.getTag())){
                rewardWithTags.add(reward);
            }
        }
        return rewardWithTags;
    }
}
