
import java.awt.List;
import static java.awt.image.ImageObserver.HEIGHT;
import static java.lang.Math.E;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.spi.DirStateFactory;
import javax.swing.JOptionPane;

public class ConnectionTrial {

    //username
    private String username = "root";
    //password
    private String password = "";
    //dataBase name
    private String dataBaseName = "kullanici";

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

    public void addUser(String name, String surName, String password) {
        

        try {
            statement = connection.createStatement();
            String addNameUser = name;
            String addSurnameUser = surName;
            String addPasswordUser = password;
            String addQuery = "Insert Into kullanicilar (userName , userSurname ,userPassword) values (" + "'" + addNameUser.toUpperCase() + "'," + "'" + addSurnameUser.toUpperCase() + "'," + "'" + addPasswordUser + "')";
            statement.executeUpdate(addQuery);

        } catch (SQLException ex) {
            Logger.getLogger(ConnectionTrial.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//add Employee

    public void addAdministrator(String name, String password) {
        try {
            statement = connection.createStatement();
            String addNameAdmin = name;
            String addPasswordAdmin = password;
            String addQuery = "Insert into yoneticiler (adminName , adminPassword) values (" + "'" + addNameAdmin.toUpperCase() + "'," + "'" + addPasswordAdmin.toUpperCase() + "')";
            statement.executeUpdate(addQuery);
        } catch (SQLException e) {
        }

    }

    public void updateUser(String userName, String userSurname, String userPassword, String userPastPassword) {
        String updateQuery = "update kullanicilar set userName = ? , userSurname = ? , userPassword = ? where userPassword = ? ";

        try {
            preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, userSurname);
            preparedStatement.setString(3, userPassword);
            preparedStatement.setString(4, userPastPassword);

            int row = preparedStatement.executeUpdate();

            if (row == 1) {
                JOptionPane.showMessageDialog(null, "Kullanıcı Bilgileri Değiştirildi. ", " ! Bilgilendirme", HEIGHT);

            } else {
                JOptionPane.showMessageDialog(null, "Kullanıcı Bulunamadı. \n"
                        + "Bilgilerin Eksiksiz ve Doğru Olduğundan Emin olun ! ", " ! Bilgilendirme", HEIGHT);

            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionTrial.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//update Employee

    public void deleteUser(int id, String userName, String userSurname) {
        String deleteQuery = "DELETE FROM kullanicilar WHERE id = ? and userName = ? and userSurname = ? ";

        try {
            preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, userName);
            preparedStatement.setString(3, userSurname);

            int row = preparedStatement.executeUpdate();
            if (row == 1) {

                JOptionPane.showMessageDialog(null, "Kullanıcı Silindi. ", " ! Bilgilendirme", HEIGHT);

            } else {
                JOptionPane.showMessageDialog(null, "Kullanıcı Bulunamadı. \n"
                        + "Bilgilerin Eksiksiz ve Doğru Olduğundan Emin olun ! ", "! Bilgilendirme", HEIGHT);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConnectionTrial.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//delete employee

    public String cargoS() {
        String query = "select * from kargolar";
        int id;
        int aliciId;
        int gondericiId;
        String aliciAdres = null;
        String gondericiAdres = null;
        int kargoKG;

        ArrayList<String> kargolar = new ArrayList<String>();

        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                id = rs.getInt("id");
                aliciId = rs.getInt("aliciId");
                gondericiId = rs.getInt("gondericiId");
                aliciAdres = rs.getString("aliciAdres");
                gondericiAdres = rs.getString("gondericiAdres");
                kargoKG = rs.getInt("kargoKg");

                kargolar.add(String.valueOf(id + "              "));
                kargolar.add(String.valueOf(aliciId + "              "));
                kargolar.add(String.valueOf(gondericiId + "              "));
                kargolar.add(aliciAdres + "  ");
                kargolar.add(gondericiAdres + "  ");
                kargolar.add(String.valueOf(kargoKG + "   "));
                kargolar.add("\n");

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConnectionTrial.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Arrays.toString(kargolar.toArray());

    }

    public String cargoS(int kargoId) {
        String query = "select * from kargolar where id = ?";
        int id;
        int aliciId;
        int gondericiId;
        String aliciAdres = null;
        String gondericiAdres = null;
        int kargoKG;

        ArrayList<String> kargolar = new ArrayList<String>();

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, kargoId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                id = rs.getInt("id");
                aliciId = rs.getInt("aliciId");
                gondericiId = rs.getInt("gondericiId");
                aliciAdres = rs.getString("aliciAdres");
                gondericiAdres = rs.getString("gondericiAdres");
                kargoKG = rs.getInt("kargoKg");

                kargolar.add(String.valueOf(id + "              "));
                kargolar.add(String.valueOf(aliciId + "              "));
                kargolar.add(String.valueOf(gondericiId + "              "));
                kargolar.add(aliciAdres + "  ");
                kargolar.add(gondericiAdres + "  ");
                kargolar.add(String.valueOf(kargoKG + "   "));
                kargolar.add("\n");

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConnectionTrial.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Arrays.toString(kargolar.toArray());

    }

    public Boolean userLogin(String name, String password) {
        String query = "select  * from kullanicilar";
        Boolean kontrol = false;
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                if (rs.getString("userName").equals(name) && rs.getString("userPassword").equals(password)) {
                    kontrol = true;
                    break;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConnectionTrial.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kontrol;
    }

    public Boolean adminLogin(String name, String password) {
        String query = "select  * from yoneticiler";
        Boolean kontrol = false;
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                if (rs.getString("adminName").equals(name) && rs.getString("adminPassword").equals(password)) {
                    kontrol = true;
                    break;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConnectionTrial.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kontrol;
    }

    public void addCargo(int aliciId, int gondericiId, String aliciAdres, String gondericiAdres, String kargoKg) {
        String query = "insert into  kargolar (aliciId ,gondericiId ,aliciAdres,gondericiAdres,kargoKg) values (" + '?' + "," + '?' + "," + '?' + "," + '?' + "," + '?' + ")";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, aliciId);
            preparedStatement.setInt(2, gondericiId);
            preparedStatement.setString(3, aliciAdres);
            preparedStatement.setString(4, gondericiAdres);
            preparedStatement.setString(5, kargoKg);
            int row = preparedStatement.executeUpdate();

            if (row == 1) {
                JOptionPane.showMessageDialog(null, "Kargo Ekleme Başarılı...", "! Bilgilendirme", HEIGHT);

            } else {
                JOptionPane.showMessageDialog(null, "Kargo Ekleme Başarısız. \n"
                        + "Tekrar Deneyin.", "! Bilgilendirme", HEIGHT);

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConnectionTrial.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteCargo(int kargoId) {
        String query = "DELETE  FROM kargolar WHERE id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, kargoId);
            int row = preparedStatement.executeUpdate();
            if (row == 1) {
                JOptionPane.showMessageDialog(null, "Kargo Silindi.", "! Dikkat", HEIGHT);

            } else {
                JOptionPane.showMessageDialog(null, "Kargo Silme Başarısız. \n"
                        + "Bilgilerin Doğruluğundan Emin Olun.", " ! Dikkat", HEIGHT);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionTrial.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

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
            System.out.println("Connection failed" + ex);
        }

    }//connectrial constructer

    public static void main(String[] args) {

    }//main

}//ConnectionTrial 
