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
    private int workspace_id;
    private String name;
    private boolean status;
    private final Connection connection;

    public WorkSpace () {
        MyConnection myConnection = new MyConnection();
        connection = myConnection.connection();
    }

    public void createWorkSpace (Socket socket , User user) {
        getWorkSpaceInformation(socket);

        int max_id = 0;
        String query = "SELECT max(id) FROM WorkSpace";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            max_id = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        workspace_id = max_id + 1;

        query = "INSERT INTO WorkSpace (id , name , status) VALUES (? ,? , ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1 , workspace_id);
            preparedStatement.setString(2 , name);
            if (status) {
                preparedStatement.setInt(3, 1);
            } else {
                preparedStatement.setInt(3 , 0);
            }

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        query = "INSERT INTO WorkSpaceMembers (email , workspace_id , role) VALUES (? , ? , ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1 , workspace_id);
            preparedStatement.setString(2 , user.getEmail());
            preparedStatement.setInt(3 , 1);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getWorkSpaceInformation (Socket socket) {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String json = dataInputStream.readUTF();
            Gson gson = new Gson();
            name = gson.fromJson(json , String.class);

            json = dataInputStream.readUTF();
            String s = gson.fromJson(json , String.class);
            status = !s.equals("0");

            dataInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logIntoWorkspace (Socket socket) {
        String id = "";
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String json = dataInputStream.readUTF();

            Gson gson = new Gson();
            id = gson.fromJson(json , String.class);

            dataInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.workspace_id = Integer.parseInt(id);

        String query = "SELECT name , id FROM Board WHERE workspace_id = (?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1 , workspace_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            String json;
            Gson gson = new Gson();
            while (resultSet.next()) {
                String name = resultSet.getString(1);
                gson = new Gson();
                json = gson.toJson(name);
                dataOutputStream.writeUTF(json);

                int boardId = resultSet.getInt(2);
                json = gson.toJson(boardId);
                dataOutputStream.writeUTF(json);
            }

            json = gson.toJson("end");
            dataOutputStream.writeUTF(json);

            dataOutputStream.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

    }


    /// setter and getter
    public int getWorkspace_id() {
        return workspace_id;
    }

    public void setWorkspace_id(int workspace_id) {
        this.workspace_id = workspace_id;
    }
}
