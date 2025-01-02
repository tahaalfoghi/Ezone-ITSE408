package models;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.util.Date;

@Named
@ApplicationScoped
public class UserOrderDetails {
    private String username;
    private int orderId;
    private Date orderDate;
    private int productId;
    private String productName;
    private int quantity;
    private double unitPrice;
    private String category;
    private String image;

    public UserOrderDetails() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "UserOrderDetails{" +
                "username='" + username + '\'' +
                ", orderId=" + orderId +
                ", orderDate=" + orderDate +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", category='" + category + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
