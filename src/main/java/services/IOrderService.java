package services;

import models.Order;

import java.util.ArrayList;

public interface IOrderService {
    ArrayList<Order> getAllOrders();
    Order getOrderById(int id);
    boolean deleteOrderById(int id);
    boolean addOrder(Order order);
    boolean updateOrder(Order order);
}
