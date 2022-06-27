import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

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

}
