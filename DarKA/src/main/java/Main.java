import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {


        // we handle multiThread in this section
        try {
            Socket socket = new Socket("127.0.0.1" , 5000);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            Request request = new Request("0");
            Gson gson = new Gson();
            String jsonRequest = gson.toJson(request);
            dataOutputStream.writeUTF(jsonRequest);

            String name = "Taskee";
            String status = "1";

            jsonRequest = gson.toJson(name);
            dataOutputStream.writeUTF(jsonRequest);

            jsonRequest = gson.toJson(status);
            dataOutputStream.writeUTF(jsonRequest);

            dataOutputStream.flush();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}