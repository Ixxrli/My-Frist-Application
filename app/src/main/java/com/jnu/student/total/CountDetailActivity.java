package com.jnu.student.total;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.jnu.student.CountAdapter;
import com.jnu.student.content.MyBill;
import com.jnu.student.content.MyTask;
import com.jnu.student.data.TaskStorage;

import java.util.ArrayList;

public class CountDetailActivity extends AppCompatActivity {
    private final String FILE_TASK_NAME = "taskData.ser";

    private TaskStorage taskStorage;

    private ArrayList<MyTask> task_All;
    private ArrayList<MyBill> bill_All;
    private RecyclerView billRecyclerView;
    public CountAdapter billRecyclerAdapter;
}
