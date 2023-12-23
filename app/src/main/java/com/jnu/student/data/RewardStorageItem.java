package com.jnu.student.data;

import android.content.Context;

import com.jnu.student.content.MyReward;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class RewardStorageItem implements RewardStorage{
    @Override
    public ArrayList<MyReward> LoadRewardItems(Context context, String fileName) {
        ArrayList<MyReward> RData = new ArrayList<>();
        try{
            FileInputStream fileInputStream =context.openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            RData =(ArrayList<MyReward>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RData;
    }

    @Override
    public void SaveRewardItems(Context context, String fileName, ArrayList<MyReward> Data) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(fileName,Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(Data);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
