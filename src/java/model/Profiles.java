/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
/**
 *
 * @author bellkung
 */
public class Profiles {
    private String username;
    private String first_name;
    private String last_name;
    private String url_image;
    private Connection conn;

    public Profiles(Connection conn, String username) throws SQLException {
        this.username = username;
        this.conn = conn;
        
        PreparedStatement pstmt = conn.prepareStatement("SELECT first_name, last_name, profile_image FROM usami.Profile WHERE user_id = '"+username+"'");
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            this.first_name = rs.getString("first_name");
            this.last_name = rs.getString("last_name");
            this.url_image = rs.getString("profile_image");
        }
    }
    
    public void addNewProfile(Connection conn) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO usami.Profile(user_id, first_name, last_name, profile_image) VALUES(?,?,?,?)");
        pstmt.setString(1, this.username);
        pstmt.setString(2, this.first_name);
        pstmt.setString(3, this.last_name);
        pstmt.setString(4, this.url_image);
        pstmt.executeUpdate();
    }
    
    public void editProfile(Connection conn) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("UPDATE usami.Profile SET first_name = ?, last_name = ?, profile_image = ? WHERE user_id = ?");
            pstmt.setString(1, this.first_name);
            pstmt.setString(2, this.last_name);
            pstmt.setString(4, this.url_image);
            pstmt.setString(5, this.username);
            pstmt.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }
    
    public ArrayList<Art> getAllArt() {
        ArrayList<Art> allArt = new ArrayList<>();
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM usami.Image WHERE user_id = ? ORDER BY upload_date DESC");
            pstmt.setString(1, this.username);
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Art art = new Art(this.conn, rs.getString("image_id"));
                allArt.add(art);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allArt;
    }
    
    public Profiles() {
        
    }

    public String getUsername() {
        
        
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }


    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }
    
    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }
    
    public String getFullname() {
        return this.first_name + " " + this.last_name;
    }
    
    
}
