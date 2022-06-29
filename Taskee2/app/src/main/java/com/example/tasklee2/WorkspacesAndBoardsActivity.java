package com.example.tasklee2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;



public class WorkspacesAndBoardsActivity extends AppCompatActivity {
    LinearLayout liner_tasks;

    private Button homeButton ;
    private Button WorkspacesButton ;
    private Button ProfileButton ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workspaces_and_boards);

        liner_tasks = findViewById(R.id.liner_tasks);
        String[] workspaces = {"Workspace1" , "Workspace2" , "Workspace3" , "Workspace4" , "Workspace5"};
        //String[] boards = {"Board1" , "Board2" ,"Board3" } ;
        for (String workspace : workspaces) {
                addTaskToUI(workspace);

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
    public void addTaskToUI(String workspace){
        LinearLayout TaskInfo = new LinearLayout(this);;

        TextView title = new TextView(this);
        title.setTextSize(5);
        title.setTextColor(Color.rgb(203, 192, 211));
        title.setText("");
        TaskInfo.addView(title);


        //Button
        Button BoardButton = new Button(this) ;
        BoardButton.setText(workspace);
        BoardButton.setBackgroundColor(Color.rgb(105, 48, 195));
        BoardButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openBoardsActivity();

            }
        });


        liner_tasks.addView(TaskInfo) ;
        liner_tasks.addView(BoardButton);




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

    public void openBoardsActivity()
    {
        Intent intent = new Intent(this,Boardsactivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1,menu);
        return true;
    }
}