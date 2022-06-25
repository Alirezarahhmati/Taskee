import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkSpace {
    private String name;
    private Connection connection;

    public WorkSpace () {
        MyConnection myConnection = new MyConnection();
        connection = myConnection.connection();
    }

    public void createWorkSpace (Socket socket) {
        getWorkSpaceInformation(socket);

        int max_id = 0;
        String query = "SELECT id FROM WorkSpace";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int x = resultSet.getInt(1);
                if (x > max_id) {
                    max_id = x;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        query = "INSERT INTO WorkSpace (id , name) VALUES (? ,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1 , max_id + 1);
            preparedStatement.setString(2 , name);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getWorkSpaceInformation (Socket socket) {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String json = dataInputStream.readUTF();
            Gson gson = new Gson();
            name = gson.fromJson(json , String.class);
            dataInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {


        // we handle multiThread in this section
        try {
            Socket socket = new Socket("127.0.0.1" , 5000);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            Request request = new Request("2");
            Gson gson = new Gson();
            String jsonRequest = gson.toJson(request);
            dataOutputStream.writeUTF(jsonRequest);

            String email = "mimimimi@gmail.com";
            String password = "85858585";

            Thread.sleep(5000000);

            jsonRequest = gson.toJson(email);
            dataOutputStream.writeUTF(jsonRequest);

            jsonRequest = gson.toJson(password);
            dataOutputStream.writeUTF(jsonRequest);

            dataOutputStream.flush();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
