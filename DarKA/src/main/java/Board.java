import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Board extends WorkSpace{
    private int board_id;
    private String name;
    private final Connection connection;

    public Board () {
        MyConnection myConnection = new MyConnection();
        connection = myConnection.connection();
    }

    public boolean canMakeBoard (User user) {
        String query = "SELECT role FROM WorkSpaceMembers WHERE email = (?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1 , user.getEmail());

            ResultSet resultSet = preparedStatement.executeQuery();
            String role = resultSet.getString(1);
            if (role.equals("3")) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void createBoard (Socket socket) {
        getBoardInformation(socket);

        int max_id = 0;
        String query = "SELECT max(id) FROM Board";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            max_id = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        board_id = max_id + 1;

        query = "INSERT INTO Board (id , name , workspace_id) VALUES (? , ? , ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1 , board_id);
            preparedStatement.setString(2 , name);
            preparedStatement.setInt(3 , getWorkspace_id());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getBoardInformation (Socket socket) {
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

    public void logIntoBoard (Socket socket) {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String json = dataInputStream.readUTF();
            Gson gson = new Gson();
            String id = gson.fromJson(json , String.class);
            board_id = Integer.parseInt(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
