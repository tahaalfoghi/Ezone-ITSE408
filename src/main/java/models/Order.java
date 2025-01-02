package models;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.sql.Date;

@Named
@ApplicationScoped
public class Order {
    private int id;
    private int product_id;
    private int user_id;
    private int quantity;
    private Date created_at;
    public Order() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", product_id=" + product_id +
                ", user_id=" + user_id +
                ", quantity=" + quantity +
                ", created_at=" + created_at +
                '}';
    }
}
