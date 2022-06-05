import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    ServerSocket serverSocket;
    Socket clientSocket;

    public Server (int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void clientAccept () {
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /// this function receive a code request and do what must do
    public void receiveInformation () {
        try {
            DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
            ///   read request code for know which request sent from client
            String jsonRequest = dataInputStream.readUTF();
            Gson gson = new Gson();
            String requestCode = gson.toJson(jsonRequest);

            switch (requestCode) {

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        receiveInformation();
    }
}
