
import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

    private  String email;
    private  String password;
    private final Connection connection;

    public User() {
        MyConnection myConnection = new MyConnection();
        connection = myConnection.connection();
    }

    public boolean addUser (Socket socket) {
        getUserInformation(socket);

        if (!isEmailUnique()) {
            return false;
        }

        String query = "INSERT INTO users (email , password , profile_picture) VALUES (? , ? , ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3 , null);

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean login(Socket socket) {
        getUserInformation(socket);

        if (isEmailUnique()) {
            return false;
        }

        String query = "SELECT password FROM users WHERE email = (?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1 , email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (password.equals(resultSet.getString(1))) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void getUserInformation (Socket socket) {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            Gson gson = new Gson();

            String json = dataInputStream.readUTF();
            email = gson.fromJson(json , String.class);

            json = dataInputStream.readUTF();
            password = gson.fromJson(json , String.class);

            dataInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isEmailUnique () {
        try {
            String query = "SELECT email FROM users";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String s = resultSet.getString(1);
                if (s.equals(email)) {
                    return false;
                }
            }
        } catch (SQLException ignored) {}

        return true;
    }

    // getter and setter
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
