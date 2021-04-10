package com.kozhevnikov.TechTask.service.interfaces;

import com.kozhevnikov.TechTask.model.User;

import java.util.List;

public interface UserService {

    public List<User> getAllUsers();

    public User getUserById(Long id);

    public User createUser(User user);

    public User updateUser(Long id, User user);

    public Long deleteUser(Long id);

}
