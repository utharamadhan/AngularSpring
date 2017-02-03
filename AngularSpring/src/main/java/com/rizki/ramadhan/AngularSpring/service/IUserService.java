package com.rizki.ramadhan.AngularSpring.service;

import java.util.List;

import com.rizki.ramadhan.AngularSpring.model.User;

public interface IUserService {
    
    User findById(long id);
     
    User findByName(String name);
     
    void saveUser(User user);
     
    void updateUser(User user);
     
    void deleteUserById(long id);
 
    List<User> findAllUsers(); 
     
    void deleteAllUsers();
     
    public boolean isUserExist(User user);
     
}