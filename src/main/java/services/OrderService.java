package services;

import Util.DbContext;
import models.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class OrderService implements IOrderService {


    @Override
    public ArrayList<Order> getAllOrders() {
        return null;
    }

    @Override
    public Order getOrderById(int id) {
        return null;
    }

    @Override
    public boolean deleteOrderById(int id) {
        return false;
    }

    @Override
    public boolean addOrder(Order order) {
        boolean result = false;
        try{
            Connection conn = DbContext.getConnection();
            String sql = "insert into orders(product_id,user_id,quantity,created_at) values(?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, order.getProduct_id());
            ps.setInt(2, order.getUser_id());
            ps.setInt(3, order.getQuantity());
            ps.setDate(4, order.getCreated_at());

            System.out.println(order);
            int success = ps.executeUpdate();
            result = success > 0;
        }catch (Exception e){

        }
        return result;
    }

    @Override
    public boolean updateOrder(Order order) {
        return false;
    }
}
