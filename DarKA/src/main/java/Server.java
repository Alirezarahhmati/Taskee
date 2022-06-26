import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server implements Runnable{
    ServerSocket serverSocket;
    Socket clientSocket;
    DataInputStream dataInputStream;
    User user;

    public Server (int port) {
        try {
            serverSocket = new ServerSocket(port);
            user = new User();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /// this function receive a code request and do what must do
    public void receiveInformation () {
        // save information
        User user = new User();
        WorkSpace workSpace = new WorkSpace();
        Board board = new Board();
        //
        while (true) {
            try {
                clientSocket = serverSocket.accept();
                dataInputStream = new DataInputStream(clientSocket.getInputStream());
                ///   read request code for know which request sent from client
                String jsonRequest = dataInputStream.readUTF();
                Gson gson = new Gson();
                Request request = gson.fromJson(jsonRequest, Request.class);

                if (!request.receiveRequest(clientSocket, user, workSpace ,board)){
                    break;
                }

            } catch (IOException e) {
                System.out.println(Arrays.toString(e.getStackTrace()));
            }
        }

        try {
            dataInputStream.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("start");
        receiveInformation();
        App.decreaseConcurrencyLevel();
        System.out.println("end");
    }
}
