package com.example.tasklee2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    LinearLayout liner_tasks;

    private Button homeButton ;
    private Button WorkspacesButton ;
    private Button ProfileButton ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        liner_tasks = findViewById(R.id.liner_tasks);
        String[] titles = {"title1", "title2", "title3"};
        for (String title : titles) {
            addTaskToUI(title);
        }

        homeButton = (Button) findViewById(R.id.homeButton) ;
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHomeActivity() ;
            }
        });

        WorkspacesButton = (Button) findViewById(R.id.WorkspacesButton) ;
        WorkspacesButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openWorkspacesAndBoardsActivity();

            }
        });

        ProfileButton = (Button) findViewById(R.id.ProfileButton) ;
        ProfileButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openProfileActivity();

            }
        });
    }

    public void addTaskToUI(String taskTitle){
        LinearLayout TaskInfo = new LinearLayout(this);;
        //Title
//        TextView title = new TextView(this);
//        title.setTextSize(18);
//        title.setTextColor(Color.rgb(203, 192, 211));
//        title.setText(taskTitle);
//        liner_tasks.addView(TaskInfo) ;

        //Button
        Button BoardButton = new Button(this) ;
        BoardButton.setText(taskTitle);
        BoardButton.setBackgroundColor(Color.rgb(252, 226, 92));
        BoardButton.setWidth(16);
        BoardButton.setHeight(16);
        liner_tasks.addView(BoardButton);
        //Description
        TextView description = new TextView(this) ;
        description.setText(taskTitle);
        liner_tasks.addView(description) ;


    }
    public void openHomeActivity()
    {
        Intent intent = new Intent(this,HomeActivity.class) ;
        startActivity(intent) ;
    }
    public void openWorkspacesAndBoardsActivity()
    {
        Intent intent = new Intent(this,WorkspacesAndBoardsActivity.class);
        startActivity(intent);
    }
    public void openProfileActivity()
    {
        Intent intent = new Intent(this,ProfileActivity.class);
        startActivity(intent);
    }
}