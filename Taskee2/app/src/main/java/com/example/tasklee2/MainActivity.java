package com.example.tasklee2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.google.gson.Gson;

import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private Button SignUpButton ;
    private Button SignInButton ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        BackgroundTask backgroundTask = new BackgroundTask();
        Thread thread = new Thread(backgroundTask);
        thread.start();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

//
//
//        SignInButton = (Button) findViewById(R.id.SignInButton) ;
//        SignInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openHomeActivity() ;
//            }
//        });
//
//        SignUpButton = (Button) findViewById(R.id.SignUpButton) ;
//        SignUpButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openSignUpActivity();
//            }
//        });
//
//    }
//    public void openHomeActivity()
//    {
//        Intent intent = new Intent(this,HomeActivity.class) ;
//        startActivity(intent) ;
//
//    }
//    public void openSignUpActivity() {
//        Intent intent = new Intent(this, SignUpActivity.class);
//        startActivity(intent);
//
//    }

}