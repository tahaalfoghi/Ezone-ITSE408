package services;

import models.Cart;
import models.Product;

import java.util.ArrayList;

public interface IProductService {
    ArrayList<Product> getAllProducts();
    Product getProductById(int id);
    boolean addProduct(Product product);
    boolean updateProduct(Product product);
    boolean deleteProduct(int id);
    ArrayList<Cart> getCartProducts(ArrayList<Cart> carts);
}
