import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server extends Thread{
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
            clientSocket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
            ///   read request code for know which request sent from client
            String jsonRequest = dataInputStream.readUTF();
            Gson gson = new Gson();
            Request request = gson.fromJson(jsonRequest , Request.class);
            request.receiveRequest();
            dataInputStream.close();
            clientSocket.close();
        } catch (IOException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public void run() {
        receiveInformation();
        App.decreaseConcurrencyLevel();
    }
}
