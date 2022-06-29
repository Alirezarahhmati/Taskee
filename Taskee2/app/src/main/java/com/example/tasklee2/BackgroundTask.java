package com.example.tasklee2;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class BackgroundTask implements Runnable
{
    @Override
    public void run() {
        try {
            Socket socket = new Socket("192.168.1.110" , 5000) ;

            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream()) ;
            Request request = new Request("1") ;
            Gson gson = new Gson() ;
            String jsonRequest = gson.toJson(request) ;
            dataOutputStream.writeUTF(jsonRequest);
            dataOutputStream.flush();
            dataOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

//public class BackgroundTask extends AsyncTask<Void,Void,Void> {
//    @Override
//    protected Void doInBackground (Void... voids)
//    {
//        try {
//            Socket socket = new Socket("192.168.1.110" , 5000) ;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return null ;
//    }
//
//
//
//}
