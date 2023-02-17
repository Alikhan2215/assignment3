import java.sql.*;

public class Database {
    public Connection connectToDB(String dbname, String user, String pass){
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname, user, pass);

        } catch (Exception e){
            System.out.println(e);
        }
        return conn;
    }

    public void createTable(Connection conn, String tableName){
        Statement statement;
        try{
            String query = "create table " + tableName + "(userID SERIAL, email varchar(50), password varchar(50), primary key(userID));";
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Table Created");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void insertRow(Connection conn, String tableName, String email, String password){
        Statement statement;
        try{
            String query = String.format("insert into %s(email, password) values('%s', '%s');", tableName, email, password);
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Row Inserted");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public String readEmail(Connection conn, String tableName){
        Statement statement;
        ResultSet rs = null;
        String email = null;
        try {
            String query = String.format("select * from %s", tableName);
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                email = rs.getString("email");
            }

        }catch (Exception e){
            System.out.println(e);
        }
        return email;
    }

    public String readPassword(Connection conn, String tableName){
        Statement statement;
        ResultSet rs = null;
        String pass = null;
        try {
            String query = String.format("select * from %s", tableName);
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                pass = rs.getString("password");
            }

        }catch (Exception e){
            System.out.println(e);
        }
        return pass;
    }

    public void updateData(Connection conn, String tableName, String newData){
        Statement statement;
        try {
            String query = String.format("update %s set password = '%s'", tableName, newData);
            statement = conn.createStatement();
            statement.executeUpdate(query);

        }catch (Exception e){
            System.out.println(e);
        }
    }

}
