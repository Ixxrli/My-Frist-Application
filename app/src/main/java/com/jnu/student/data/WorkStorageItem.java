package com.jnu.student.data;


import android.content.Context;

import com.jnu.student.target.MyTarget;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class WorkStorageItem implements WorkStorage{

    @Override
    public ArrayList<MyTarget> LoadTargetItems(Context context, String fileName) {
        ArrayList<MyTarget> TData = new ArrayList<>();
        try{
            FileInputStream fileInputStream =context.openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            TData =(ArrayList<MyTarget>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TData;
    }

    @Override
    public ArrayList<MyTarget> LoadTargetItems(Context context, String fileName, String type) {
        ArrayList<MyTarget> targetData = LoadTargetItems(context,fileName);
        ArrayList<MyTarget> targetDataFilter = new ArrayList<>();
        for(MyTarget target : targetData){
            if(target.getTarType().equals(type)){
                targetDataFilter.add(target);
            }
        }
        return targetDataFilter;
    }


    @Override
    public void SaveTargetItems(Context context, String fileName, ArrayList<MyTarget> taskData) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(fileName,Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(taskData);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}