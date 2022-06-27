import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Card extends Board{
    private int card_id;
    private String title;
    private String description;
    private String path;
    private String date;      // YYYY_XX_ZZ
    private String time;      // YY:XX
    public Connection connection;

    public Card () {
        MyConnection myConnection = new MyConnection();
        connection = myConnection.connection();
    }

    public void addCard (Socket socket , User user) {
        getCardInformation(socket);

        int max_id = 0;
        String query = "SELECT max(id) FROM Card";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            max_id = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        card_id = max_id + 1;

        query = "INSERT INTO Card (id , created_by , title , description , date , time , board_id) VALUES (? , ? , ? , ? , ? , ? , ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1 , card_id);
            preparedStatement.setString(2 , user.getEmail());
            preparedStatement.setString(3 , title);
            preparedStatement.setString(4 , description);
            preparedStatement.setString(5 , date);
            preparedStatement.setString(6 , time);
            preparedStatement.setInt(7 ,getBoard_id());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void getCardInformation (Socket socket) {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            Gson gson = new Gson();

            String json = dataInputStream.readUTF();
            title = gson.fromJson(json , String.class);

            json = dataInputStream.readUTF();
            description = gson.fromJson(json , String.class);

            json = dataInputStream.readUTF();
            path = gson.fromJson(json , String.class);

            json = dataInputStream.readUTF();
            date = gson.fromJson(json , String.class);

            json = dataInputStream.readUTF();
            time = gson.fromJson(json , String.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addMember (Socket socket) {
        String email = "";
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            Gson gson = new Gson();
            String json = dataInputStream.readUTF();
            email = gson.fromJson(json , String.class);
            dataInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String query = "INSERT INTO cardMember (card_id , email) VALUES (?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1 , card_id );
            preparedStatement.setString(2 , email);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCard (Socket socket) {
        int id = 0;
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            Gson gson = new Gson();
            String json = dataInputStream.readUTF();
            String x = gson.fromJson(json , String.class);

            id = Integer.parseInt(x);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String query = "DELETE FROM Card WHERE id = (?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1 , id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
