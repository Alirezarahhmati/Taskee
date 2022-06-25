import java.sql.*;

public class MyConnection {

    private static final String url = "jdbc:sqlite:Taskee.db";
    private static Connection connection;

    public static void main(String[] args) {
        try {
            connection = DriverManager.getConnection(url);
            System.out.println("connection ");

            String query = "SELECT * FROM users";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int s = resultSet.getInt(1);
                System.out.println(s);
                int a = resultSet.getInt(2);
                System.out.println(a);
            }
//            statement.setString(1 , "users");
            System.out.println("query applied");

            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection connection () {
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}