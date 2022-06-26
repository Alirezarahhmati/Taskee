import java.sql.*;

public class MyConnection {

    private static final String url = "jdbc:sqlite:Taskee.db";
    private static Connection connection;

    public Connection connection () {
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}