package com.houses96.module.property.service;

import com.houses96.module.googlecloudstorage.service.GoogleCloudStorageService;
import com.houses96.module.googlecloudstorage.util.GoogleCloudStorageUtil;
import com.houses96.module.property.dto.PropertyDTO;
import com.houses96.module.property.entity.PropertyEntity;
import com.houses96.module.property.mapper.PropertyMapper;
import com.houses96.module.property.repository.PropertyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final GoogleCloudStorageService googleCloudStorageService;
    private final PropertyMapper propertyMapper;
    private static final Logger logger = LoggerFactory.getLogger(PropertyService.class);

    @Autowired
    public PropertyService(PropertyRepository propertyRepository, GoogleCloudStorageService googleCloudStorageService, PropertyMapper propertyMapper) {
        this.propertyRepository = propertyRepository;
        this.googleCloudStorageService = googleCloudStorageService;
        this.propertyMapper = propertyMapper;
    }

    public void insertProperty(PropertyDTO propertyDTO, List<MultipartFile> images) {
        try {
            List<String> urlImages = this.insertImages(images);
            propertyDTO.setPictures(urlImages);
            propertyRepository.insertProperty(propertyMapper.toEntity(propertyDTO));
            logger.info("Property inserted successfully.");
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Error occurred when inserting property.", e);
        }
    }

    public void updateProperty(PropertyDTO propertyDTO, List<MultipartFile> images) {
        try {
            this.deleteImages(propertyDTO.getPictures());
            List<String> urlImages = this.insertImages(images);
            propertyDTO.setPictures(urlImages);
            propertyRepository.updateProperty(propertyMapper.toEntity(propertyDTO));
            logger.info("Property updated successfully.");
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Error occurred when updating property.", e);
        }
    }

    public void deleteProperty(String propertyId) {
        try {
            PropertyEntity propertyEntity = propertyRepository.getPropertyById(propertyId);
            this.deleteImages(propertyEntity.getPictures());
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

    private List<String> insertImages(List<MultipartFile> images) {
        List<String> urlImages = new ArrayList<>();
        images.forEach(image -> {
            logger.info("Processing file {}", image.getOriginalFilename());
            try {
                String urlImage = this.googleCloudStorageService.uploadFile(image);
                urlImages.add(urlImage);
            } catch (Exception e) {
                logger.error("Failed to process file {}", image.getOriginalFilename(), e);
            }
        });
        return urlImages;
    }

    private boolean deleteImages(List<String> images) {
        AtomicBoolean deleted = new AtomicBoolean(false);
        images.forEach(image -> {
            String filename = GoogleCloudStorageUtil.extractFileNameFromUrl(image);
            logger.info("Deleting file {}", filename);
            try {
                deleted.set(this.googleCloudStorageService.deleteFile(filename));
            } catch (Exception e) {
                logger.error("Failed to delete file {}", filename, e);
            }
        });
        return deleted.get();
    }

}

