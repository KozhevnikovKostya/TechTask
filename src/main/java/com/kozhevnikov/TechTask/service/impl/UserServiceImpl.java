package com.kozhevnikov.TechTask.service.impl;

import com.kozhevnikov.TechTask.exceptions.BankException;
import com.kozhevnikov.TechTask.model.User;
import com.kozhevnikov.TechTask.model.enums.Status;
import com.kozhevnikov.TechTask.repository.UserRepository;
import com.kozhevnikov.TechTask.security.AuthenticatedUser;
import com.kozhevnikov.TechTask.exceptions.ResourceNotFoundException;
import com.kozhevnikov.TechTask.security.model.Role;
import com.kozhevnikov.TechTask.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticatedUser authenticatedUser;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(String.format("User with id: %s doesn't exist", id)));
    }

    @Transactional
    @Override
    public User createUser(User user) throws BankException {
        checkDuplicates(user.getUsername());
        user.setStatus(Status.ACTIVE);
        user.setRole(Role.USER);
        return userRepository.save(user);
    }


    @Override
    public User updateUser(Long id, User user) throws AccessDeniedException {
        checkAccess(id);
        User foundUser = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(String.format("User with id: %s doesn't exist", id)));

        User result = updateUserFields(foundUser, user);

        return userRepository.save(result);
    }

    @Transactional
    @Override
    public Long deleteUser(Long id) throws AccessDeniedException {
        checkAccess(id);
        userRepository.deleteById(id);
        return id;
    }

    private void checkAccess(Long id) throws AccessDeniedException {
        if(!authenticatedUser.hasRole(Role.ADMIN) && !authenticatedUser.getCurrentUserId().equals(id)){
            throw new AccessDeniedException("You don't have access rights for editing");
        }
    }

    private User updateUserFields(User foundUser, User updatedUser){
        return new User().toBuilder()
                .id(foundUser.getId())
                .firstName(Optional.ofNullable(updatedUser.getFirstName()).orElse(foundUser.getFirstName()))
                .lastName(Optional.ofNullable(updatedUser.getLastName()).orElse(foundUser.getLastName()))
                .role(Optional.ofNullable(updatedUser.getRole()).orElse(foundUser.getRole()))
                .status(Optional.ofNullable(updatedUser.getStatus()).orElse(foundUser.getStatus()))
                .password(Optional.ofNullable(updatedUser.getPassword()).orElse(foundUser.getPassword()))
                .username(Optional.ofNullable(updatedUser.getUsername()).orElse(foundUser.getUsername()))
                .build();
    }

    private void checkDuplicates(String userName) throws BankException {
        var user = userRepository.findByUsername(userName);
        if(user.isPresent()){
            throw new BankException(String.format("User with username: %s already exist", userName));
        }
    }
}
