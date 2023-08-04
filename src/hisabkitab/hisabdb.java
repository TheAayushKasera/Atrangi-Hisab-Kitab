/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hisabkitab;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;

/**
 *
 * @author aayus
 */
public class hisabdb {
    static final String jdbcdriver="com.mysql.jdbc.Driver";
    static final String db_url="jdbc:mysql://localhost:3306/hisab";
    static final String user="root";
    static final String pass="995511995511";
    ResultSet rs=null;
    
    static PreparedStatement stmt=null;
    ResultSet tableview(String month) throws SQLException{
        try {
            System.out.println("Aayush");
            Class.forName(jdbcdriver);
            System.out.println("Aayush");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(hisabdb.class.getName()).log(Level.SEVERE, null, ex);
        }
        Connection conn=DriverManager.getConnection(db_url,user,pass);
        rs=conn.createStatement().executeQuery("SELECT * FROM "+month);
        System.out.println("Aayush");
        return rs;
    }
    ResultSet showmonth(String month) throws SQLException{
        try {
            Class.forName(jdbcdriver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(hisabdb.class.getName()).log(Level.SEVERE, null, ex);
        }
        Connection conn=DriverManager.getConnection(db_url,user,pass);
        rs=conn.createStatement().executeQuery("SELECT * FROM month");
        return rs;
        
    }
    void insert(String month) throws SQLException, ClassNotFoundException{
        Class.forName(jdbcdriver);
        String SQL="CREATE TABLE "+month
                + " like dummy;";
        Connection conn=DriverManager.getConnection(db_url,user,pass);
        try {
            conn = DriverManager.getConnection(db_url,user,pass);
        } catch (SQLException ex) {
            Logger.getLogger(hisabdb.class.getName()).log(Level.SEVERE, null, ex);
        }
        stmt=conn.prepareStatement(SQL);
        stmt.executeUpdate();
        stmt=null;
        SQL="INSERT INTO month (idmonth)"
                + "VALUES(?);";
        
        stmt=conn.prepareStatement(SQL);
        stmt.setString(1,month);
        stmt.execute();
        stmt.close();
        conn.close();
    }
    void entry(String item,int price,String via,String date,String mname) throws SQLException, ClassNotFoundException{
        int count=0;
        Class.forName(jdbcdriver);
        System.out.println(mname);
        try (Connection conn = DriverManager.getConnection(db_url,user,pass)) {
            String SQL="select count(*) from "+mname;
            stmt=conn.prepareStatement(SQL);
            rs=stmt.executeQuery();
            while(rs.next()){
                count=rs.getInt("count(*)");
            }
            count=count+1;
            SQL="INSERT INTO "+mname+" (item, price, via, datee,no)"
                    +"VALUES(?,?,?,?,?);";
            try {
                stmt=conn.prepareStatement(SQL);
            } catch (SQLException ex) {
                Logger.getLogger(hisabdb.class.getName()).log(Level.SEVERE, null, ex);
            }
            stmt.setString(1,item);
            stmt.setInt(2,price);
            stmt.setString(3,via);
            stmt.setString(4,date);
            stmt.setInt(5,count);
            stmt.execute();
            stmt.close();
        }    }

    int showpers(String month) throws ClassNotFoundException, SQLException {
      Class.forName(jdbcdriver);
        int count=0;
        try (Connection conn = DriverManager.getConnection(db_url,user,pass)) {
            String SQL="select price from "+month+" where shpe = ? ;";
            stmt=conn.prepareStatement(SQL);
            stmt.setString(1,"Personal");
            
            rs=stmt.executeQuery();
            while(rs.next()){
                count+=rs.getInt("price");
            } 
            return count;
    }
    
}

    int showshar(String month) throws ClassNotFoundException, SQLException {
    Class.forName(jdbcdriver);
        int count=0,count1=0;
        try (Connection conn = DriverManager.getConnection(db_url,user,pass)) {
            
            String SQL="select price from "+month+" where shpe = ?;";
            stmt=conn.prepareStatement(SQL);
            stmt.setString(1,"Shared");
            rs=stmt.executeQuery();
            while(rs.next()){
                count+=rs.getInt("price");
            }
            
            
            return count;
    }
        
    }
    
    int showshark(String month) throws SQLException{
        int count = 0;    
    try (Connection conn = DriverManager.getConnection(db_url,user,pass)) {
            String SQL="select price from "+month+" where shpe = ? and byy=?;";
            stmt=conn.prepareStatement(SQL);
            stmt.setString(1,"Shared");
            stmt.setString(2,"Kasera");
            rs=stmt.executeQuery();
            while(rs.next()){
                count+=rs.getInt("price");
            }
            return count;
    }}
    
    int showspend(String month) throws SQLException, ClassNotFoundException {
    Class.forName(jdbcdriver);
    int totalSpend = 0;

    try (Connection conn = DriverManager.getConnection(db_url, user, pass)) {
        String SQL = "SELECT SUM(price) as total_spend FROM " + month + ";";
        stmt = conn.prepareStatement(SQL);
        rs = stmt.executeQuery();
        
        if (rs.next()) {
            totalSpend = rs.getInt("total_spend");
        }
    }
    
    return totalSpend;
}

    
}
