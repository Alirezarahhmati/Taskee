import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws SQLException {


        // we handle multiThread in this section
        try {
            Socket socket = new Socket("127.0.0.1" , 5000);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            Request request = new Request("52");
            Gson gson = new Gson();
            String jsonRequest = gson.toJson(request);
            dataOutputStream.writeUTF(jsonRequest);

//            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
//            ArrayList<String> workspaces = new ArrayList<>();
//
//            while (true) {
//                String json = dataInputStream.readUTF();
//                String id = gson.fromJson(json, String.class);
//
//                if (id .equals("end")) {
//                    break;
//                }
//
//                json = dataInputStream.readUTF();
//                String name = gson.fromJson(json, String.class);
//
//                workspaces.add(id);
//                workspaces.add(name);
//            }
//
//            for (String workspace : workspaces) {
//                System.out.println(workspace);
//            }

            /////////////////////
            String email = "ali";
            String status = "pass";

            jsonRequest = gson.toJson(email);
            dataOutputStream.writeUTF(jsonRequest);

            jsonRequest = gson.toJson(status);
            dataOutputStream.writeUTF(jsonRequest);

            dataOutputStream.flush();

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            jsonRequest = dataInputStream.readUTF();
            Request request1 = gson.fromJson(jsonRequest , Request.class);
            if (request1.getRequestCode().equals("300")) {
                System.out.println("yes");
            } else {
                System.out.println("no");
            }

            dataInputStream.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }
}