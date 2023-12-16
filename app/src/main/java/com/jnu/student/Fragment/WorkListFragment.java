package com.jnu.student.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jnu.student.R;
import com.jnu.student.WorkItemDetailActivity;
import com.jnu.student.data.WorkStorage;
import com.jnu.student.data.WorkStorageItem;
import com.jnu.student.target.MyTarget;
import com.jnu.student.total.StagetotalFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

public class WorkListFragment extends Fragment {
    private final static  String[] TarType = {"每日任务", "每周任务", "普通任务","副本任务"};
    private final String File_Target_Name ="TData";
    private  ArrayList<MyTarget>targetList;

    private  WorkStorage workStorage;

    private ActivityResultLauncher<Intent> addTargetLauncher;


    public WorkListFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_workllist, container, false);

        workStorage = new WorkStorageItem();
        targetList =workStorage.LoadTargetItems(requireActivity(),File_Target_Name);
        ViewPager2 viewPager2 = rootview.findViewById(R.id.view_pager);
        TabLayout tabLayout = rootview.findViewById(R.id.tab_layout);

        PagerAdapter pagerAdapter = new PagerAdapter(this);
        viewPager2.setAdapter(pagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> tab.setText("Tab " + (position + 1))
        ).attach();
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setText(TarType[position])).attach();

        addTargetLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    // 当数据准备好时
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        String title = null;
                        if (intent != null) {
                            title = intent.getStringExtra("title");
                        }
                        int points = 0;
                        if (intent != null) {
                            points = Integer.parseInt(Objects.requireNonNull(intent.getStringExtra("points")));
                        }
                        int numbers = 0;
                        if (intent != null) {
                            numbers = Integer.parseInt(Objects.requireNonNull(intent.getStringExtra("numbers")));
                        }
                        String type = null;
                        if (intent != null) {
                            type = intent.getStringExtra("type");
                        }
                        String tag = null;
                        if (intent != null) {
                            tag = intent.getStringExtra("tag");
                        }
                        // 创建 Date 对象表示当前时间
                        Date currentDate = new Date();
                        // 创建 SimpleDateFormat 对象，指定日期格式和时区
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai")); // 设置时区为北京时间
                        // 格式化输出
                        String beijingTime = sdf.format(currentDate);
                        MyTarget myTarget = new MyTarget(beijingTime, title, points, numbers, 0, type, tag,"normal");
                        // 存储数据
                        targetList.add(myTarget);
                        System.out.println(targetList);
                        workStorage.SaveTargetItems(requireActivity().getApplicationContext(), File_Target_Name, targetList);

                    }
                });

        ImageView addButton = rootview.findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), WorkItemDetailActivity.class);
            addTargetLauncher.launch(intent);
        });
        return rootview;
    }
    private static class PagerAdapter extends FragmentStateAdapter {

        private static final int NUM_TABS = 4;

        public PagerAdapter(@NonNull Fragment fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            // 根据位置返回不同的子 Fragment
            switch (position) {
                case 0:
                    return new StagetotalFragment(TarType[0]);
                case 1:
                    return new StagetotalFragment(TarType[1]);
                case 2:
                    return new StagetotalFragment(TarType[2]);
                case 3:
                    return new StagetotalFragment(TarType[3]);
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
}