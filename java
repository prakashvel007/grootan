import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FileUpload {

    private static final String url = "jdbc:mysql://localhost:3306/java_demo?useSSL=false";
    private static final String user = "root";
    private static final String password = "root";
     private static final String sql = "INSERT INTO users (first_name, last_name, CSVfile) values (?, ?, ?)";

    public int uploadFile(String firstName, String lastName, InputStream file) {
        int row = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try (Connection connection = DriverManage.getC"onnection(url, user, password);
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            if (file != null) {
                String fileName = file;
                CSVReader reader = new CSVReader(new FileReader(fileName ));
                //get only the header
                String[] header = reader.readNext();
                String sql="Create Table user(";
                for(int i=0;i<header.length;i++){
                      sql+="header[i] varchar(45),";
                }
                sql=")";
                 preparedStatement.setBlob(3, fileName);
            }

            // sends the statement to the database server
            row = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }
        return row;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
