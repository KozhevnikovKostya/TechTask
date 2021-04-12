package com.kozhevnikov.TechTask.service.interfaces;

import com.kozhevnikov.TechTask.exceptions.BankException;
import com.kozhevnikov.TechTask.model.User;

import java.nio.file.AccessDeniedException;
import java.util.List;

/**
 *  The service for registration and managing users
 */
public interface UserService {

    /**
     *
     * @return all users in system, can perform only users with permission managed:user
     */
     List<User> getAllUsers();

    /**
     *
     * @param id
     * @return requested user
     * @throws AccessDeniedException if authorized user isn't admin or this account doesn't belong to him
     */
     User getUserById(Long id) throws AccessDeniedException;

    /**
     *
     * @param user
     * @return created  user
     * @throws BankException if user with same username already exist
     */
     User createUser(User user) throws BankException;

    /**
     *
     * @param id of updated user
     * @param user entity with fields for update
     * @return updated user
     * @throws AccessDeniedException if authorized user isn't admin or this account doesn't belong to him
     */
     User updateUser(Long id, User user) throws  AccessDeniedException;

    /**
     * delete user by id
     *
     * @param id
     * @return id of deleted user
     */
     Long deleteUser(Long id);

}
