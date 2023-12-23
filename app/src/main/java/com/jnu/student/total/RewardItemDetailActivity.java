package com.jnu.student.total;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jnu.student.R;

import java.util.Objects;

public class RewardItemDetailActivity extends AppCompatActivity {
    private String ST;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_details);
        // 获取标题、成就点数、数量、类型和标签
        EditText editTitle = findViewById(R.id.editTitle);
        EditText editPoint = findViewById(R.id.editPoints);
        EditText editTag   = findViewById(R.id.editTag);
        Intent intent =getIntent();
        if(intent != null){
            if(intent.getStringExtra("title") != null) {
                TextView textView = findViewById(R.id.titleText);
                textView.setText("修改奖励");

            }
            editTitle.setText(intent.getStringExtra("title"));
            editPoint.setText(intent.getStringExtra("points"));
            editTag.setText(intent.getStringExtra("tag"));
            position = intent.getIntExtra("position",-1);
        }
        ImageView backimage =findViewById(R.id.goBackButton);
        backimage.setOnClickListener(v -> RewardItemDetailActivity.this.finish());

        Spinner editor =findViewById(R.id.spinnerRewardType);
        editor.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                ST = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ST = "单次";
            }
        });

        Button ok =findViewById(R.id.btnSubmit);
        ok.setOnClickListener(v -> {

            if(editTitle.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(),"请输入名称", Toast.LENGTH_SHORT).show();
                return;
            }
            if(editPoint.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(),"请输入消耗得分", Toast.LENGTH_SHORT).show();
                return;
            }
            Objects.requireNonNull(intent).putExtra("title",editTitle.getText().toString());
            Objects.requireNonNull(intent).putExtra("points",editPoint.getText().toString());
            Objects.requireNonNull(intent).putExtra("type",ST);
            if(editTag.getText().toString().equals("")){
                intent.putExtra("tag","全部");
            }
            else{
                intent.putExtra("tag",editTag.getText().toString());
            }
            intent.putExtra("position",position);
            setResult(Activity.RESULT_OK,intent);
            RewardItemDetailActivity.this.finish();
        });
    }
}
