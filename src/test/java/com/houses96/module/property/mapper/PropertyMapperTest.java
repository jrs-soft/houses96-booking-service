package com.houses96.property.mapper;

import com.houses96.module.googlecloudstorage.property.dto.PropertyDTO;
import com.houses96.module.googlecloudstorage.property.entity.PropertyEntity;
import com.houses96.module.googlecloudstorage.property.mapper.PropertyMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PropertyMapperTest {

    private PropertyMapper propertyMapper;
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        propertyMapper = new PropertyMapper(modelMapper);
    }

    @Test
    void testToDTO() {
        // Given
        PropertyEntity propertyEntity = new PropertyEntity();
        propertyEntity.setId("123");
        propertyEntity.setHostId("host_001");
        propertyEntity.setTitle("Beautiful House");
        propertyEntity.setDescription("A lovely place to stay.");
        propertyEntity.setDescriptionType("Full");
        propertyEntity.setStateId("ST001");
        propertyEntity.setCityId("CT001");
        propertyEntity.setAddress("123 Main St");
        propertyEntity.setZipCode("12345");
        propertyEntity.setLatitude("40.7128");
        propertyEntity.setLongitude("-74.0060");
        propertyEntity.setTypeId("TYPE01");
        propertyEntity.setTypeOfPlace("Entire Place");
        propertyEntity.setAmenityIds(Arrays.asList("A001", "A002"));
        propertyEntity.setPictures(Arrays.asList("pic1.jpg", "pic2.jpg"));
        propertyEntity.setPricePerNight(150.0);
        propertyEntity.setNumberOfBedrooms(3);
        propertyEntity.setNumberOfBathrooms(2);
        propertyEntity.setNumberOfBeds(4);
        propertyEntity.setMaximumNumberOfGuests(6);
        propertyEntity.setFirstBookingType(1);
        propertyEntity.setCreatedAt(new Date());
        propertyEntity.setModifiedAt(new Date());
        propertyEntity.setStatus("Active");

        // When
        PropertyDTO propertyDTO = propertyMapper.toDTO(propertyEntity);

        // Then
        assertNotNull(propertyDTO);
        assertEquals("123", propertyDTO.getId());
        assertEquals("host_001", propertyDTO.getHostId());
        assertEquals("Beautiful House", propertyDTO.getTitle());
        assertEquals("A lovely place to stay.", propertyDTO.getDescription());
        assertEquals("Full", propertyDTO.getDescriptionType());
        assertEquals("ST001", propertyDTO.getStateId());
        assertEquals("CT001", propertyDTO.getCityId());
        assertEquals("123 Main St", propertyDTO.getAddress());
        assertEquals("12345", propertyDTO.getZipCode());
        assertEquals("40.7128", propertyDTO.getLatitude());
        assertEquals("-74.0060", propertyDTO.getLongitude());
        assertEquals("TYPE01", propertyDTO.getTypeId());
        assertEquals("Entire Place", propertyDTO.getTypeOfPlace());
        assertEquals(Arrays.asList("A001", "A002"), propertyDTO.getAmenityIds());
        assertEquals(Arrays.asList("pic1.jpg", "pic2.jpg"), propertyDTO.getPictures());
        assertEquals(150.0, propertyDTO.getPricePerNight());
        assertEquals(3, propertyDTO.getNumberOfBedrooms());
        assertEquals(2, propertyDTO.getNumberOfBathrooms());
        assertEquals(4, propertyDTO.getNumberOfBeds());
        assertEquals(6, propertyDTO.getMaximumNumberOfGuests());
        assertEquals(1, propertyDTO.getFirstBookingType());
        assertNotNull(propertyDTO.getCreatedAt());
        assertNotNull(propertyDTO.getModifiedAt());
        assertEquals("Active", propertyDTO.getStatus());
    }

    @Test
    void testToEntity() {
        // Given
        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setId("123");
        propertyDTO.setHostId("host_001");
        propertyDTO.setTitle("Beautiful House");
        propertyDTO.setDescription("A lovely place to stay.");
        propertyDTO.setDescriptionType("Full");
        propertyDTO.setStateId("ST001");
        propertyDTO.setCityId("CT001");
        propertyDTO.setAddress("123 Main St");
        propertyDTO.setZipCode("12345");
        propertyDTO.setLatitude("40.7128");
        propertyDTO.setLongitude("-74.0060");
        propertyDTO.setTypeId("TYPE01");
        propertyDTO.setTypeOfPlace("Entire Place");
        propertyDTO.setAmenityIds(Arrays.asList("A001", "A002"));
        propertyDTO.setPictures(Arrays.asList("pic1.jpg", "pic2.jpg"));
        propertyDTO.setPricePerNight(150.0);
        propertyDTO.setNumberOfBedrooms(3);
        propertyDTO.setNumberOfBathrooms(2);
        propertyDTO.setNumberOfBeds(4);
        propertyDTO.setMaximumNumberOfGuests(6);
        propertyDTO.setFirstBookingType(1);
        propertyDTO.setCreatedAt(new Date());
        propertyDTO.setModifiedAt(new Date());
        propertyDTO.setStatus("Active");

        // When
        PropertyEntity propertyEntity = propertyMapper.toEntity(propertyDTO);

        // Then
        assertNotNull(propertyEntity);
        assertEquals("123", propertyEntity.getId());
        assertEquals("host_001", propertyEntity.getHostId());
        assertEquals("Beautiful House", propertyEntity.getTitle());
        assertEquals("A lovely place to stay.", propertyEntity.getDescription());
        assertEquals("Full", propertyEntity.getDescriptionType());
        assertEquals("ST001", propertyEntity.getStateId());
        assertEquals("CT001", propertyEntity.getCityId());
        assertEquals("123 Main St", propertyEntity.getAddress());
        assertEquals("12345", propertyEntity.getZipCode());
        assertEquals("40.7128", propertyEntity.getLatitude());
        assertEquals("-74.0060", propertyEntity.getLongitude());
        assertEquals("TYPE01", propertyEntity.getTypeId());
        assertEquals("Entire Place", propertyEntity.getTypeOfPlace());
        assertEquals(Arrays.asList("A001", "A002"), propertyEntity.getAmenityIds());
        assertEquals(Arrays.asList("pic1.jpg", "pic2.jpg"), propertyEntity.getPictures());
        assertEquals(150.0, propertyEntity.getPricePerNight());
        assertEquals(3, propertyEntity.getNumberOfBedrooms());
        assertEquals(2, propertyEntity.getNumberOfBathrooms());
        assertEquals(4, propertyEntity.getNumberOfBeds());
        assertEquals(6, propertyEntity.getMaximumNumberOfGuests());
        assertEquals(1, propertyEntity.getFirstBookingType());
        assertNotNull(propertyEntity.getCreatedAt());
        assertNotNull(propertyEntity.getModifiedAt());
        assertEquals("Active", propertyEntity.getStatus());
    }
}
