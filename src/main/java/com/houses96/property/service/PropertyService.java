package com.houses96.property.service;

import com.houses96.property.dto.PropertyDTO;
import com.houses96.property.mapper.PropertyMapper;
import com.houses96.property.repository.PropertyRepository;
import com.houses96.user.dto.UserDTO;
import com.houses96.user.mapper.UserMapper;
import com.houses96.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final PropertyMapper propertyMapper;
    private static final Logger logger = LoggerFactory.getLogger(PropertyService.class);

    @Autowired
    public PropertyService(PropertyRepository propertyRepository, PropertyMapper propertyMapper) {
        this.propertyRepository = propertyRepository;
        this.propertyMapper = propertyMapper;
    }

    public void insertProperty(PropertyDTO propertyDTO) {
        try {
            propertyRepository.insertProperty(propertyMapper.toEntity(propertyDTO));
            logger.info("Property inserted successfully.");
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Error occurred when inserting property.", e);
        }
    }

    public void updateProperty(PropertyDTO propertyDTO) {
        try {
            propertyRepository.updateProperty(propertyMapper.toEntity(propertyDTO));
            logger.info("Property updated successfully.");
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Error occurred when updating property.", e);
        }
    }

    public void deleteProperty(String propertyId) {
        try {
            propertyRepository.deleteProperty(propertyId);
            logger.info("Property deleted successfully.");
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Error occurred when deleting property.", e);
        }
    }

    public PropertyDTO getPropertyById(String propertyId) {
        PropertyDTO propertyDTO = null;
        try {
            propertyDTO = propertyMapper.toDTO(propertyRepository.getPropertyById(propertyId));
            logger.info("Property retrieved successfully.");
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Error occurred when retrieving property.", e);
        }
        return propertyDTO;
    }

}

