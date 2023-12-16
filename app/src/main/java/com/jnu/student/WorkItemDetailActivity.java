package com.jnu.student;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WorkItemDetailActivity extends AppCompatActivity {
    private String ST;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_details);
        ImageView backimage =findViewById(R.id.goBackButton);
        backimage.setOnClickListener(v -> WorkItemDetailActivity.this.finish());

        Spinner editor =findViewById(R.id.spinnerTaskType);
        editor.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                ST = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ST = "每日任务";
            }
        });

        Button ok =findViewById(R.id.btnSubmit);
        ok.setOnClickListener(v -> {
            // 获取标题、成就点数、数量、类型和标签
            EditText editTitle = findViewById(R.id.editTitle);
            EditText editPoint = findViewById(R.id.editAchievementPoints);
            EditText editNum   = findViewById(R.id.editQuantity);
            EditText editTag   = findViewById(R.id.editTag);
            if(editTitle.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(),"请输入目标", Toast.LENGTH_SHORT).show();
                return;
            }
            if(editPoint.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(),"请输入完成得分", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent();
            intent.putExtra("title",editTitle.getText().toString());
            intent.putExtra("points",editPoint.getText().toString());
            intent.putExtra("type",ST);
            if(editNum.getText().toString().equals("")){
                intent.putExtra("numbers","1");
            }
            else{
                intent.putExtra("numbers",editNum.getText().toString());
            }
            if(editTag.getText().toString().equals("")){
                intent.putExtra("tag","全部");
            }
            else{
                intent.putExtra("tag",editTag.getText().toString());
            }
            setResult(Activity.RESULT_OK,intent);
            WorkItemDetailActivity.this.finish();
        });
    }
}