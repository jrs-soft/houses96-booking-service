package com.houses96.module.property.service;

import com.houses96.module.googlecloudstorage.service.GoogleCloudStorageService;
import com.houses96.module.property.dto.PropertyDTO;
import com.houses96.module.property.entity.PropertyEntity;
import com.houses96.module.property.mapper.PropertyMapper;
import com.houses96.module.property.repository.PropertyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PropertyServiceTest {

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private GoogleCloudStorageService googleCloudStorageService;

    @Mock
    private PropertyMapper propertyMapper;

    @InjectMocks
    private PropertyService propertyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInsertProperty() throws Exception {
        // Given
        PropertyDTO propertyDTO = new PropertyDTO();
        PropertyEntity propertyEntity = new PropertyEntity();  // Mock the entity that should be returned
        MultipartFile image1 = mock(MultipartFile.class);
        MultipartFile image2 = mock(MultipartFile.class);

        when(image1.getOriginalFilename()).thenReturn("image1.jpg");
        when(image2.getOriginalFilename()).thenReturn("image2.jpg");
        when(googleCloudStorageService.uploadFile(image1)).thenReturn("url1");
        when(googleCloudStorageService.uploadFile(image2)).thenReturn("url2");
        when(propertyMapper.toEntity(propertyDTO)).thenReturn(propertyEntity);  // Mocking the conversion

        // When
        propertyService.insertProperty(propertyDTO, Arrays.asList(image1, image2));

        // Then
        verify(propertyRepository, times(1)).insertProperty(any(PropertyEntity.class));
        verify(propertyMapper, times(1)).toEntity(propertyDTO);  // Ensure mapping was called
        assertEquals(Arrays.asList("url1", "url2"), propertyDTO.getPictures());
    }

    @Test
    void testUpdateProperty() throws Exception {
        // Given
        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setPictures(Arrays.asList("https://storage.googleapis.com/my-bucket/my-file1.jpg", "https://storage.googleapis.com/my-bucket/my-file2.jpg"));  // Ensure actual URLs are used
        PropertyEntity propertyEntity = new PropertyEntity();  // Mock the entity that should be returned
        MultipartFile image1 = mock(MultipartFile.class);
        MultipartFile image2 = mock(MultipartFile.class);

        when(image1.getOriginalFilename()).thenReturn("my-file1.jpg");
        when(image2.getOriginalFilename()).thenReturn("my-file2.jpg");
        when(googleCloudStorageService.uploadFile(image1)).thenReturn("my-file1.jpg");
        when(googleCloudStorageService.uploadFile(image2)).thenReturn("my-file2.jpg");
        when(googleCloudStorageService.deleteFile(anyString())).thenReturn(true);
        when(propertyMapper.toEntity(propertyDTO)).thenReturn(propertyEntity);  // Mocking the conversion

        // When
        propertyService.updateProperty(propertyDTO, Arrays.asList(image1, image2));

        // Then
        verify(propertyRepository, times(1)).updateProperty(any(PropertyEntity.class));
        verify(googleCloudStorageService, times(2)).deleteFile(anyString());  // Ensures deleteFile is called twice
        verify(googleCloudStorageService, times(2)).uploadFile(any(MultipartFile.class));  // Ensures uploadFile is called twice
        verify(propertyMapper, times(1)).toEntity(propertyDTO);  // Ensure mapping was called
        assertEquals(Arrays.asList("my-file1.jpg", "my-file2.jpg"), propertyDTO.getPictures());
    }


    @Test
    void testDeleteProperty() throws Exception {
        // Given
        String propertyId = "propertyId";
        PropertyEntity propertyEntity = new PropertyEntity();

        // Ensure the pictures list contains valid URLs
        propertyEntity.setPictures(Arrays.asList("https://storage.googleapis.com/my-bucket/my-file1.jpg", "https://storage.googleapis.com/my-bucket/my-file2.jpg"));

        // Mock the repository to return the property entity
        when(propertyRepository.getPropertyById(propertyId)).thenReturn(propertyEntity);

        // Mock the deleteFile method to return true when a valid URL is passed
        when(googleCloudStorageService.deleteFile(anyString())).thenReturn(true);

        // When
        propertyService.deleteProperty(propertyId);

        // Then
        // Verify that deleteProperty is called once with the correct property ID
        verify(propertyRepository, times(1)).deleteProperty(propertyId);

        // Verify that deleteFile is called twice with the correct URLs
        verify(googleCloudStorageService, times(2)).deleteFile(anyString());
        verify(googleCloudStorageService).deleteFile("my-file1.jpg");
        verify(googleCloudStorageService).deleteFile("my-file2.jpg");

        // Verify that getPropertyById is called once to fetch the property details
        verify(propertyRepository, times(1)).getPropertyById(propertyId);
    }

    @Test
    void testGetPropertyById() throws Exception {
        // Given
        String propertyId = "propertyId";
        PropertyEntity propertyEntity = new PropertyEntity();
        PropertyDTO propertyDTO = new PropertyDTO();
        when(propertyRepository.getPropertyById(propertyId)).thenReturn(propertyEntity);
        when(propertyMapper.toDTO(propertyEntity)).thenReturn(propertyDTO);

        // When
        PropertyDTO result = propertyService.getPropertyById(propertyId);

        // Then
        assertNotNull(result);
        verify(propertyRepository, times(1)).getPropertyById(propertyId);
        verify(propertyMapper, times(1)).toDTO(propertyEntity);
    }
}

