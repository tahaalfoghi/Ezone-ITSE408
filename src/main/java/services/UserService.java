package services;

import Util.DbContext;
import jakarta.enterprise.context.RequestScoped;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

@RequestScoped
public class UserService implements IUserService {

    private final DbContext dbContext = new DbContext();

    @Override
    public User login(User user) {
        User existinguser = null;
        try{

            if(user == null || user.getEmail().isEmpty() || user.getPassword().isEmpty()){
                throw new Exception("Invalid email or password");
            }

            Connection conn = dbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement("select * from users where email = ? and password = ?");
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                existinguser = new User();
                existinguser.setId(rs.getInt("id"));
                existinguser.setName(rs.getString("username"));
                existinguser.setEmail(rs.getString("email"));
                existinguser.setPassword(rs.getString("password"));
                existinguser.setRole(rs.getString("user_role"));

                System.out.println(existinguser);
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("User login failed");
        }
        return existinguser;
    }

    @Override
    public boolean register(User user) {
        int success = 0;
        try{
            if(user == null || user.getEmail().isEmpty()
                    || user.getPassword().isEmpty()
                    || user.getName().isEmpty() || user.getEmail().isEmpty()
                    || user.getPassword().isEmpty()){
                throw new Exception("Invalid user credentials");
            }
            else{
                Connection conn = dbContext.getConnection();
                String insertStmt = "INSERT INTO users(username, email, password) VALUES (?,?,?)";
                PreparedStatement ps = conn.prepareStatement(insertStmt);
                ps.setString(1, user.getName());
                ps.setString(2, user.getEmail());
                ps.setString(3, user.getPassword());
                success =  ps.executeUpdate();


            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return success > 0;
    }

    @Override
    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        try{
            Connection conn = dbContext.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from users");
            while(rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("user_role"));
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public boolean deleteUser(int id) {
        boolean success = false;
        try{
            Connection conn = dbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement("delete from users where id = ?");
            ps.setInt(1, id);
            int result = ps.executeUpdate();
            success = result > 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return success;
    }

    @Override
    public boolean updateUser(User user) {
        boolean success = false;
        try {
            Connection conn = dbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement("update users set username = ?, email = ?, password = ? where id = ?");
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getId());
            int result = ps.executeUpdate();
            success = result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    @Override
    public boolean addUser(User user) {
        int success = 0;
        try{
            if(user == null || user.getEmail().isEmpty()
                    || user.getPassword().isEmpty()
                    || user.getName().isEmpty() || user.getEmail().isEmpty()
                    || user.getPassword().isEmpty()){
                throw new Exception("Invalid user credentials");
            }
            else{
                Connection conn = dbContext.getConnection();
                String insertStmt = "INSERT INTO users(username, email, password,user_role) VALUES (?,?,?,?)";
                PreparedStatement ps = conn.prepareStatement(insertStmt);
                ps.setString(1, user.getName());
                ps.setString(2, user.getEmail());
                ps.setString(3, user.getPassword());
                ps.setString(4, user.getRole());
                success =  ps.executeUpdate();

                System.out.println("Successfully added user"+ success);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return success > 0;
    }

    public User getUserById(int id) {
        User user = null;
        try{
            Connection conn = dbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement("select * from users where id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("user_role"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
