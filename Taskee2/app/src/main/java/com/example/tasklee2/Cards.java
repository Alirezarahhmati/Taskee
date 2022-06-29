package com.example.tasklee2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Cards extends AppCompatActivity {
    private Button button ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);

        button = (Button) findViewById(R.id.ToDo) ;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openToDoActivity();
            }
        });

        button = (Button) findViewById(R.id.Doing) ;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDoingActivity();
            }
        });

        button = (Button) findViewById(R.id.Done) ;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDoneActivity();
            }
        });

    }

    public void openToDoActivity()
    {
        Intent intent = new Intent(this,ToDoActivity.class);
        startActivity(intent);
    }

    public void openDoingActivity()
    {
        Intent intent = new Intent(this,DoingActivity.class);
        startActivity(intent);
    }

    public void openDoneActivity()
    {
        Intent intent = new Intent(this,DoneActivity.class);
        startActivity(intent);
    }
}