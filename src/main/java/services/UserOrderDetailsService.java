package services;

import Util.DbContext;
import models.UserOrderDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class UserOrderDetailsService {

    public static ArrayList<UserOrderDetails> getUserOrderDetails(int userId) {
        ArrayList<UserOrderDetails> userOrderDetails = new ArrayList<>();
        try {
            Connection conn = DbContext.getConnection();
            String query = "SELECT * FROM UserProductOrders WHERE user_id = ?";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                UserOrderDetails uod = new UserOrderDetails();
                uod.setUsername(rs.getString("username"));
                uod.setProductName(rs.getString("name"));
                uod.setCategory(rs.getString("category"));
                uod.setImage(rs.getString("image"));
                uod.setUnitPrice(rs.getDouble("price"));
                uod.setOrderId(rs.getInt("order_id"));
                uod.setProductId(rs.getInt("Id"));
                
                // Set the order date
                java.sql.Date sqlDate = rs.getDate("order_date");
                if (sqlDate != null) {
                    uod.setOrderDate(new java.util.Date(sqlDate.getTime()));
                }
                
                userOrderDetails.add(uod);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userOrderDetails;
    }
}
