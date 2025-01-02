package services;

import Util.DbContext;
import jakarta.enterprise.context.RequestScoped;
import models.Cart;
import models.Product;

import java.sql.*;
import java.util.ArrayList;

@RequestScoped
public class ProductService implements IProductService {

    //private final DbContext dbContext = new DbContext();
    @Override
    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();

        try{
            Connection conn = DbContext.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from products");
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setCategory(rs.getString("category"));
                product.setPrice(rs.getDouble("price"));
                product.setImage(rs.getString("image"));
                products.add(product);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public Product getProductById(int id) {
        Product product = null;
        if(id <=0){
            throw new IllegalArgumentException("Product id must be greater than 0");
        }
        try{
            Connection conn = DbContext.getConnection();
            PreparedStatement stmt = conn.prepareStatement("select * from products where id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setCategory(rs.getString("category"));
                product.setPrice(rs.getDouble("price"));
                product.setImage(rs.getString("image"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public boolean addProduct(Product product) {
        boolean result = false;
        try{
            if(!validateProduct(product)){
                return false;
            }
            Connection conn = DbContext.getConnection();
            String sql = "INSERT INTO products (name, category, price, image) VALUES (?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, product.getName());
            ps.setString(2, product.getCategory());
            ps.setDouble(3, product.getPrice());
            ps.setString(4, product.getImage());
            int success = ps.executeUpdate();
            result = success > 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean updateProduct(Product product) {
        System.out.println("ProductService: Attempting to update product");
        System.out.println("Product Details: " + product);

        // Validate the product first
        if (product == null) {
            System.err.println("ERROR: Product is null");
            return false;
        }

        // Validate product details
        StringBuilder validationErrors = new StringBuilder();
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            validationErrors.append("Product name is required. ");
        }
        if (product.getCategory() == null || product.getCategory().trim().isEmpty()) {
            validationErrors.append("Product category is required. ");
        }
        if (product.getPrice() <= 0) {
            validationErrors.append("Product price must be greater than zero. ");
        }
        
        // Optional image validation
        if (product.getImage() != null && product.getImage().trim().isEmpty()) {
            validationErrors.append("Product image cannot be an empty string. ");
        }

        if (validationErrors.length() > 0) {
            System.err.println("Validation Errors: " + validationErrors.toString());
            return false;
        }

        Connection conn = null;
        PreparedStatement ps = null;
        boolean result = false;
        
        try {
            // Establish database connection
            conn = DbContext.getConnection();
            if (conn == null) {
                System.err.println("ERROR: Database connection is null");
                return false;
            }

            // Prepare SQL update statement
            String sql = "UPDATE products SET name = ?, category = ?, price = ?, image = ? WHERE id = ?";
            ps = conn.prepareStatement(sql);
            
            // Set parameters
            ps.setString(1, product.getName());
            ps.setString(2, product.getCategory());
            ps.setDouble(3, product.getPrice());
            ps.setString(4, product.getImage());
            ps.setInt(5, product.getId());

            // Log SQL parameters for debugging
            System.out.println("SQL Update Parameters:");
            System.out.println("Name: " + product.getName());
            System.out.println("Category: " + product.getCategory());
            System.out.println("Price: " + product.getPrice());
            System.out.println("Image: " + product.getImage());
            System.out.println("ID: " + product.getId());

            // Execute update
            int rowsAffected = ps.executeUpdate();
            
            // Check update result
            result = rowsAffected > 0;
            
            System.out.println("Update Operation Result:");
            System.out.println("Rows Affected: " + rowsAffected);
            System.out.println("Update Successful: " + result);

        } catch (SQLException e) {
            System.err.println("DATABASE ERROR during product update:");
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Message: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("UNEXPECTED ERROR during product update:");
            System.err.println("Error Message: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("ERROR closing database resources:");
                System.err.println("Error Message: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return result;
    }

    @Override
    public boolean deleteProduct(int id) {

        int success = 0;
        try{
            Connection conn = DbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement("delete from products where id = ?");
            ps.setInt(1, id);
            success = ps.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
        return success > 0;
    }
    public boolean validateProduct(Product product) {
        if (product == null) {
            System.out.println("Validation failed: Product cannot be null.");
            return false;
        }

        StringBuilder errorMessages = new StringBuilder();

        if (product.getName() == null || product.getName().trim().isEmpty()) {
            errorMessages.append("Product name is required. ");
        }

        if (product.getCategory() == null || product.getCategory().trim().isEmpty()) {
            errorMessages.append("Product category is required. ");
        }

        if (product.getPrice() <= 0) {
            errorMessages.append("Product price must be greater than zero. ");
        }

        // Make image validation optional during update
        if (product.getImage() != null && product.getImage().trim().isEmpty()) {
            errorMessages.append("Product image cannot be an empty string. ");
        }

        if (errorMessages.length() > 0) {
            System.out.println("Validation failed: " + errorMessages.toString().trim());
            return false;
        }

        return true;
    }

    @Override
    public ArrayList<Cart> getCartProducts(ArrayList<Cart> carts) {
        ArrayList<Cart> products = new ArrayList<>();

        try{
            if(carts.size() > 0){
                for(Cart item : carts){
                    String sql = "select * from products where id = ?";
                    PreparedStatement ps = DbContext.getConnection().prepareStatement(sql);
                    ps.setInt(1, item.getId());
                    ResultSet rs = ps.executeQuery();
                    if(rs.next()){
                        Cart cart = new Cart();
                        cart.setId(rs.getInt("id"));
                        cart.setName(rs.getString("name"));
                        cart.setCategory(rs.getString("category"));
                        cart.setPrice(rs.getDouble("price") * item.getQuantity());
                        cart.setImage(rs.getString("image"));
                        cart.setQuantity(item.getQuantity());
                        products.add(cart);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return products;
    }
}
