package com.houses96.module.property.controller;

import com.houses96.module.property.dto.PropertyDTO;
import com.houses96.module.property.service.PropertyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    private final PropertyService propertyService;
    private static final Logger logger = LoggerFactory.getLogger(PropertyController.class);

    @Autowired
    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @PostMapping
    public ResponseEntity<String> insertProperty(@RequestPart("property") PropertyDTO propertyDTO,
                                                 @RequestPart("images") List<MultipartFile> images) {
        try {
            propertyService.insertProperty(propertyDTO, images);
            return ResponseEntity.status(HttpStatus.CREATED).body("Property inserted successfully.");
        } catch (Exception e) {
            logger.error("Error occurred when inserting property.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inserting property.");
        }
    }

    @PutMapping
    public ResponseEntity<String> updateProperty(@RequestPart("property") PropertyDTO propertyDTO,
                                                 @RequestPart("images") List<MultipartFile> images) {
        try {
            propertyService.updateProperty(propertyDTO, images);
            return ResponseEntity.status(HttpStatus.OK).body("Property updated successfully.");
        } catch (Exception e) {
            logger.error("Error occurred when updating property.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating property.");
        }
    }

    @DeleteMapping("/{propertyId}")
    public ResponseEntity<String> deleteProperty(@PathVariable String propertyId) {
        try {
            propertyService.deleteProperty(propertyId);
            return ResponseEntity.status(HttpStatus.OK).body("Property deleted successfully.");
        } catch (Exception e) {
            logger.error("Error occurred when deleting property.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting property.");
        }
    }

    @GetMapping("/{propertyId}")
    public ResponseEntity<PropertyDTO> getPropertyById(@PathVariable String propertyId) {
        try {
            PropertyDTO propertyDTO = propertyService.getPropertyById(propertyId);
            if (propertyDTO != null) {
                return ResponseEntity.status(HttpStatus.OK).body(propertyDTO);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            logger.error("Error occurred when retrieving property.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
