
import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Request {
    private final String requestCode;

    public Request (String requestCode) {
        this.requestCode = requestCode;
    }

    public void receiveRequest (Socket socket , User user) {
        switch (requestCode) {
            case "1" :
                User newUser = new User();
                if (newUser.addUser(socket)) {
                    //sendSuccessfulRequest(socket);
                }else {
                    //sendUnSuccessfulRequest(socket);
                }
                break;
            case "2" :
                if (user.login(socket)) {
                    //sendSuccessfulRequest(socket);
                }else {
                    //sendUnSuccessfulRequest(socket);
                }
                break;
        }
    }

    public void sendSuccessfulRequest (Socket socket) {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            Request request = new Request("300");
            Gson gson = new Gson();
            String jsonRequest = gson.toJson(request);
            dataOutputStream.writeUTF(jsonRequest);
            dataOutputStream.flush();
            dataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendUnSuccessfulRequest (Socket socket) {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            Request request = new Request("301");
            Gson gson = new Gson();
            String jsonRequest = gson.toJson(request);
            dataOutputStream.writeUTF(jsonRequest);
            dataOutputStream.flush();
            dataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // send changed information to client and update it
    public void refreshRequest () {

    }


}
