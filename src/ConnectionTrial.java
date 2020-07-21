
import com.sun.xml.internal.bind.v2.runtime.output.SAXOutput;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.spi.DirStateFactory;

public class ConnectionTrial {

    //username
    private String username = "root";
    //password
    private String password = "";
    //dataBase name
    private String dataBaseName = "demo";
    //server host
    private String host = "localhost";
    //server port
    private int port = 3306;

    //server connection
    private Connection connection = null;

    //sql run query
    private Statement statement = null;
    
    //prepared statement 
    private PreparedStatement preparedStatement = null;
    
    
    
    public void preparedEmployeeInfos(int userid , String username){
        String query = "Select * From calisanlar where id > ?  and name like ?";
        try {
            preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, userid);
                    preparedStatement.setString(2, username);
                  ResultSet rs =   preparedStatement.executeQuery();
                  while (rs.next()) {
                    int id = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String email = rs.getString("email");
                System.out.println("İD: " + id + "\n"
                        + "Name : " + name + "\n"
                        + "Surname: " + surname + "\n"
                        + "Email: " + email);
                
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConnectionTrial.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

    public void workingInfos() {
        String query = "Select * from calisanlar where name like 'Y%'";

        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String email = rs.getString("email");
                System.out.println("İD: " + id + "\n"
                        + "Name : " + name + "\n"
                        + "Surname: " + surname + "\n"
                        + "Email: " + email);

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConnectionTrial.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//working 

    public void addEmployee() {
        try {
            statement = connection.createStatement();
            String addName = "Ayşe";
            String addSurname = "sevinç";
            String addEmail = "aysesevinc@gmail.com";
            String addQuery = "Insert Into calisanlar (name , surname ,email) values (" + "'" + addName.toUpperCase() + "'," + "'" + addSurname.toUpperCase() + "'," + "'" + addEmail + "')";
            statement.executeUpdate(addQuery);

        } catch (SQLException ex) {
            Logger.getLogger(ConnectionTrial.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//add Employee

    public void updateEMployee() {
        try {
            statement = connection.createStatement();
            String updateQuery = "update calisanlar  set surname = 'SEVİNÇ' where id = 4 ";
            statement.executeUpdate(updateQuery);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionTrial.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//update Employee
    public void deleteEmployee(){
        try {
            statement = connection.createStatement();
            String deleteQuery = "delete from calisanlar where id >4";
            int value = statement.executeUpdate(deleteQuery);
            System.out.println(value + " employee deleted.");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionTrial.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//delete employee

    public ConnectionTrial() {
        //jbdc:mysql://localhost:3306/demo
        String url = "jdbc:mysql://" + host + ":" + port + "/" + dataBaseName + "?useUnicode=true&characterEncoding=utf8";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("No driver found");
        }
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection succesfull");
        } catch (SQLException ex) {
            System.out.println("Connection failed");
        }

    }//connectrial constructer

    public static void main(String[] args) {
        ConnectionTrial ct = new ConnectionTrial();
//        ct.workingInfos();
//        ct.addEmployee();
//        ct.updateEMployee();
//        ct.workingInfos();
//         ct.deleteEmployee();
ct.preparedEmployeeInfos(0,"N%");
    }//main

}//ConnectionTrial 
