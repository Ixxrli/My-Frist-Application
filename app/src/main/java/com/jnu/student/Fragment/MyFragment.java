package com.jnu.student.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jnu.student.R;
import com.jnu.student.content.MyTask;
import com.jnu.student.data.TaskStorage;
import com.jnu.student.data.TaskStorageItem;

import java.util.ArrayList;

public class MyFragment extends Fragment {
    private final String FILE_TASK_NAME = "TData";
    public MyFragment(){
        // Required empty public constructor
    }

    public  MyFragment newInstance(String param1, String param2) {
        MyFragment fragment = new MyFragment();
        Bundle T = new Bundle();
        fragment.setArguments(T);
        return fragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_my, container, false);
        ImageView myImage = rootView.findViewById(R.id.myButton);
        Button helpButton = rootView.findViewById(R.id.myHelp);
        Button clearDataButton = rootView.findViewById(R.id.myClearData);

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog("小提示",message);
            }
        });
        clearDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog();
            }
        });

        return rootView;
    }

    private void showCustomDialog(String title,String message) {
        // 创建AlertDialog.Builder对象
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // 设置对话框标题和内容
        builder.setTitle(title)
                .setMessage(message);

        // 设置确认按钮
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // 在点击确认后的处理逻辑，这里可以添加自定义的操作
                dialog.dismiss(); // 关闭对话框
            }
        });

        // 创建并显示对话框
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void showDeleteConfirmationDialog() {
        // 创建AlertDialog.Builder对象
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // 设置对话框标题和内容
        builder.setTitle("删除确认")
                .setMessage("是否确定删除所有数据，包括任务、奖励和金币数？");

        // 设置确认按钮
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // 在点击确认后的处理逻辑，这里可以添加删除操作
                // 例如，执行删除任务、奖励和金币数的操作
                deleteAllData();
                dialog.dismiss(); // 关闭对话框
            }
        });

        // 设置取消按钮
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss(); // 关闭对话框
            }
        });

        // 创建并显示对话框
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteAllData() {
        TaskStorage taskRepository = new TaskStorageItem();
        ArrayList<MyTask> emptyTaskList = new ArrayList<>();
        taskRepository.SaveTaskItems(requireActivity(),FILE_TASK_NAME,emptyTaskList);
        showCustomDialog("删除成功","重启软件后生效");
    }
    private String message = "任务是指一个个具体而明确的目标、活动或工作，通常旨在完成特定的目的或达到某种预期的结果。" +
            "\n" +
            "每日任务：保持每天早晨进行15分钟冥想，促进身心健康。" +
            "\n" +
            "每周任务：每周至少完成两次高强度间歇训练，提高身体耐力和代谢。" +
            "\n" +
            "普通任务：阅读一本经典文学作品，每季度至少完成一次自我评估并制定未来的学习计划。" +
            "\n\n"
            ;
}
