package com.jnu.student.data;

import android.content.Context;

import com.jnu.student.target.MyTarget;

import java.util.ArrayList;

public interface WorkStorage {
    ArrayList<MyTarget> LoadTargetItems(Context context, String fileName);
    ArrayList<MyTarget> LoadTargetItems(Context context, String fileName, String type);

    void SaveTargetItems(Context context, String fileName, ArrayList<MyTarget> Data);
}
