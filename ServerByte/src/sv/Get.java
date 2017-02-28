/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tien
 */
public class Get {

    String nameDb = "nguyenhuutien";
    String nameTb = "chat";
    Connection connection;
    Statement statement;

    public Get() {
        try {
            connection = createConnnection(nameDb);
            statement = createStatement(connection);
        } catch (IOException ex) {
            Logger.getLogger(Get.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Get.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Get.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection createConnnection(String name_db) throws IOException, ClassNotFoundException, SQLException {
        Connection connection = null;
        try {
            Properties p = new Properties();
            p.load(new FileInputStream("server.properties"));
            String url = p.getProperty("url");
            String user = p.getProperty("user");
            String password = p.getProperty("password");
            // System.out.println(url + name_db + " " + user + " " + password);
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url + name_db, user, password);
            return connection;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(com.sun.corba.se.spi.activation.Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (connection == null) {
            throw new NullPointerException("Connect is null");
        }
        return null;

    }

    public Statement createStatement(Connection connection) {
        Statement stt = null;
        if (stt == null) {
            try {
                stt = (Statement) connection.createStatement();
                return stt;
            } catch (SQLException ex) {
                Logger.getLogger(com.sun.corba.se.spi.activation.Server.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return null;
    }

    public String getListUser(String name_tb, int stt) {
        try {
            String sqlCommand = "select name from " + name_tb + " where status='" + String.valueOf(stt) + "'";
            ResultSet rs = statement.executeQuery(sqlCommand);
            String user = "";
            String str="";
            while (rs.next()) {
                str = rs.getString(1);
                str+= "|" + String.valueOf(stt) + "|";
                user+=str;
            }
            rs.close();
            return user;
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getList(String name_tb) {
        try {
            String sqlCommand = "select name from " + name_tb;
            ResultSet rs = statement.executeQuery(sqlCommand);
            String user = "";
            while (rs.next()) {
                user += rs.getString("name") + "|";
            }
            return user;
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean isTrue(String name_tb, String username, String password) {
        try {
            String sqlCommand = "select password from " + name_tb + " where name = " + "'" + username + "'";
            ResultSet rs = statement.executeQuery(sqlCommand);
            String pass = null;
            while (rs.next()) {
                pass = rs.getString("password");
            }
            if (password.equals(pass)) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean isConnected(String name_tb, String username) {
        try {
            String sqlCommand = "select status from " + name_tb + " where name = " + "'" + username + "'";
            ResultSet rs = statement.executeQuery(sqlCommand);
            String status = null;
            while (rs.next()) {
                status = rs.getString("status");
            }
            if (status.equals("1")) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public int correctConnect(String name_tb, String username, int con) {
        int rowNum = 0;
        try {
            String sqlCommand = "update " + name_tb + " set status=? where name = " + "'" + username + "'";
            PreparedStatement ps = connection.prepareStatement(sqlCommand);
            ps.setString(1, String.valueOf(con));
            rowNum = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rowNum;

    }

    public int addUser(String name_tb, String user, String pass) {
        int rowNum = 0;
        try {
            String sqlCommand = "select id from " + name_tb;
            ResultSet rs = statement.executeQuery(sqlCommand);
            String count = null;
            while (rs.next()) {
                count = rs.getString("id");
            }
            sqlCommand = "insert into " + name_tb + " value(?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sqlCommand);
            ps = connection.prepareStatement(sqlCommand);
            int co = Integer.parseInt(count);
            co++;
            ps.setString(1, String.valueOf(co));
            ps.setString(2, user);
            ps.setString(3, pass);
            ps.setString(4, "0");
            rowNum = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rowNum;
    }

    public int delUser(String name_tb, String user) {
        int rowNum = 0;
        try {
            String sqlCommand = "delete  from " + name_tb + " where name = '" + user + "'";
            PreparedStatement ps = connection.prepareStatement(sqlCommand);
            rowNum = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rowNum;
    }

    public int updatePass(String name_tb, String user, String pass) {
        int rowNum = 0;
        try {
            String sqlCommand = "update " + name_tb + " set password=? where name=?";
            PreparedStatement ps = connection.prepareStatement(sqlCommand);
            ps.setString(1, pass);
            ps.setString(2, user);
            rowNum = ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rowNum;
    }

    public boolean isUserToOff(String user, int port) {
        try {
            String sqlCommand = "select name from message where port='" + String.valueOf(port) + "'";
            ResultSet rs = statement.executeQuery(sqlCommand);
             String str="";
            while (rs.next()) {
                str=rs.getString("name");
                if (str.equals(user)) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Get.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return false;
    }
    
    public void insertUserToOff(String user, int port) {
        try {
            String sqlCommand = "insert into message(name,port) values(?,?)";
            PreparedStatement ps = connection.prepareStatement(sqlCommand);
            ps.setString(1, user);
            ps.setString(2, String.valueOf(port));
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean haveMessage(String user, int port) {
        try {
            String sqlCommand = "select haveMess from message where port='" + String.valueOf(port) + "' and name='" + user + "'";
            ResultSet rs = statement.executeQuery(sqlCommand);
            while (rs.next()) {
                int hm = rs.getInt("haveMess");
                if (hm == 1) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public byte[] getMessage(String user, int port) {
        try {
            String sqlCommand = "select mess from message where port='" + String.valueOf(port) + "' and name='" + user + "'";
            ResultSet rs = statement.executeQuery(sqlCommand);
            while (rs.next()) {
                byte[] off = rs.getBytes("mess");
                return off;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void insertMessage(String user, int port, byte[] mess, int type, int hm) {
        try {
            String sqlCommand = "select mess from message where port='" + String.valueOf(port) + "' and name='" + user + "'";
            ResultSet rs = statement.executeQuery(sqlCommand);
            while (rs.next()) {
                byte[] off = rs.getBytes("mess");
                byte[] newOff;
                if (off == null) {
                    newOff = new byte[mess.length];
                } else {
                    newOff = new byte[off.length + mess.length];
                }
                int i = 0, j = 0;
                if (off != null) {
                    for (j = 0; j < off.length; j++) {
                        newOff[i++] = off[j];
                    }
                }
                for (j = 0; j < mess.length; j++) {
                    newOff[i++] = mess[j];
                }
                sqlCommand = "update message set mess=?,type =?,haveMess=?  where port='" + String.valueOf(port) + "' and name='" + user + "'";
                PreparedStatement ps = connection.prepareStatement(sqlCommand);
                ps.setBytes(1, newOff);
                ps.setInt(2, type);
                ps.setInt(3, hm);
                ps.executeUpdate();
            }

        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int testRoom(String user, int port) {
        try {
            String sqlCommand = "select type from message where port='" + String.valueOf(port) + "' and name='" + user + "'";
            ResultSet rs = statement.executeQuery(sqlCommand);
            while (rs.next()) {
                int type = rs.getInt("type");
                return type;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public void delMess() {
        try {
            String sqlCommand = "delete from message where 1";
            PreparedStatement ps = connection.prepareStatement(sqlCommand);
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getSingleUser(String user, int port) {
        try {
            String sqlCommand = "select name from message where port='" + String.valueOf(port) + "'";
            ResultSet rs = statement.executeQuery(sqlCommand);
            String name;
            while (rs.next()) {
                if ((name = rs.getString("name")).equals(user) == false) {
                    return name;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public void setAvatar(String user, byte[] avatar) {
        try {
            String sqlCommand = "update chat set avatar=?  where name='" + user + "'";
            PreparedStatement ps = connection.prepareStatement(sqlCommand);
            ps.setBytes(1, avatar);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Get.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public byte[] getAvatar(String user) {
        try {
            byte[] avatar;
            String sqlCommand = "select avatar from chat where name='" + user + "'";
            ResultSet rs = statement.executeQuery(sqlCommand);
            while (rs.next()) {
                avatar = rs.getBytes("avatar");
                return avatar;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Get.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
