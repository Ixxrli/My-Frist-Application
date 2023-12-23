package com.jnu.student.data;

import android.content.Context;

import com.jnu.student.content.MyReward;


import java.util.ArrayList;

public interface RewardStorage {
    ArrayList<MyReward> LoadRewardItems(Context context, String fileName);

    void SaveRewardItems(Context context, String fileName, ArrayList<MyReward> Data);
}
