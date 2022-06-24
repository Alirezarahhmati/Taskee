import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {

        /// we handle multiThread in this section
        try {
            Socket socket = new Socket("127.0.0.1" , 5000);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            Request request = new Request("120");
            Gson gson = new Gson();
            String jsonRequest = gson.toJson(request);
            dataOutputStream.writeUTF(jsonRequest);
            dataOutputStream.flush();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}