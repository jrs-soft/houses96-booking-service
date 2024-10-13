package com.houses96.user.service;

import com.houses96.user.dto.UserDTO;
import com.houses96.user.mapper.UserMapper;
import com.houses96.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public void insertUser(UserDTO userDTO) {
        try {
            userRepository.insertUser(userMapper.toEntity(userDTO));
            logger.info("User inserted successfully.");
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Error occurred when inserting user.", e);
        }
    }

    public void updateUser(UserDTO userDTO) {
        try {
            userRepository.updateUser(userMapper.toEntity(userDTO));
            logger.info("User updated successfully.");
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Error occurred when updating user.", e);
        }
    }

    public void deleteUser(String userId) {
        try {
            userRepository.deleteUser(userId);
            logger.info("User deleted successfully.");
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Error occurred when deleting user.", e);
        }
    }

    public UserDTO getUserById(String userId) {
        UserDTO userDTO = null;
        try {
            userDTO = userMapper.toDTO(userRepository.getUserById(userId));
            logger.info("User retrieved successfully.");
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Error occurred when retrieving user.", e);
        }
        return userDTO;
    }

}

