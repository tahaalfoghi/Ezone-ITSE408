package services;

import models.User;

import java.util.ArrayList;

public interface IUserService {

    User login(User user);
    boolean register(User user);
    ArrayList<User> getAllUsers();
    boolean deleteUser(int id);
    boolean updateUser(User user);
    boolean addUser(User user);
    User getUserById(int id);
}
