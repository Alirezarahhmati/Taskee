import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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

    public void addMember (Socket socket) {
        //receive the email of member
        String email = "";
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String json = dataInputStream.readUTF();
            Gson gson = new Gson();
            email = gson.fromJson(json , String.class);
            dataInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // check if there is a member with received email in workspace
        boolean isInWorkspace = false;
        String query = "SELECT email FROM WorkSpaceMembers WHERE workspace_id = (?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1 , getWorkspace_id());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if (email.equals(resultSet.getString(1))) {
                    isInWorkspace = true;
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (!isInWorkspace) {
            return;
        }

        // check if there is a member with received email
        query = "SELECT email FROM BoardMember WHERE board_id = (?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1 , board_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if (email.equals(resultSet.getString(1))) {
                    return;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // insert new member
        query = "INSERT INTO BoardMember (email , board_id) VALUES (? , ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1 , email);
            preparedStatement.setInt(2 , board_id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeMember (Socket socket) {
        String email = "";
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String json = dataInputStream.readUTF();
            Gson gson = new Gson();
            email = gson.fromJson(json , String.class);
            dataInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String query = "DELETE FROM BoardMember WHERE email = (?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1 , email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void workspacesBoards (Socket socket) {
        DataOutputStream dataOutputStream = null;
        String json;
        Gson gson = new Gson();
        String query = "SELECT name , id FROM Board WHERE workspace_id = (?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1 , getWorkspace_id());
            ResultSet resultSet = preparedStatement.executeQuery();

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            while (resultSet.next()) {
                String name = resultSet.getString(1);
                json = gson.toJson(name);
                dataOutputStream.writeUTF(json);

                int id = resultSet.getInt(2);
                json = gson.toJson(id);
                dataOutputStream.writeUTF(json);
            }

            String message = "end";
            json = gson.toJson(message);
            dataOutputStream.writeUTF(json);

            dataOutputStream.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

    }
}
