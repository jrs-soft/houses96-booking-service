package com.houses96.property.controller;

import com.houses96.application.Application;
import com.houses96.module.googlecloudstorage.property.dto.PropertyDTO;
import com.houses96.module.googlecloudstorage.property.service.PropertyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Application.class)
class PropertyControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private PropertyService propertyService;

    private MockMvc mockMvc;

    private PropertyDTO propertyDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        propertyDTO = new PropertyDTO();
        propertyDTO.setTitle("Test Property");
        // Add other necessary fields
    }

    @Test
    void testInsertProperty_Success() throws Exception {
        MockMultipartFile propertyFile = new MockMultipartFile("property", "", "application/json", "{}".getBytes());
        MockMultipartFile imageFile = new MockMultipartFile("images", "image.jpg", "image/jpeg", "image data".getBytes());

        Mockito.doNothing().when(propertyService).insertProperty(any(PropertyDTO.class), anyList());

        mockMvc.perform(multipart("/api/properties")
                        .file(propertyFile)
                        .file(imageFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated())
                .andExpect(content().string("Property inserted successfully."));
    }

    @Test
    void testInsertProperty_Error() throws Exception {
        MockMultipartFile propertyFile = new MockMultipartFile("property", "", "application/json", "{}".getBytes());
        MockMultipartFile imageFile = new MockMultipartFile("images", "image.jpg", "image/jpeg", "image data".getBytes());

        Mockito.doThrow(new RuntimeException("Error")).when(propertyService).insertProperty(any(PropertyDTO.class), anyList());

        mockMvc.perform(multipart("/api/properties")
                        .file(propertyFile)
                        .file(imageFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error inserting property."));
    }

    @Test
    void testUpdateProperty_Success() throws Exception {
        MockMultipartFile propertyFile = new MockMultipartFile("property", "", "application/json", "{}".getBytes());
        MockMultipartFile imageFile = new MockMultipartFile("images", "image.jpg", "image/jpeg", "image data".getBytes());

        Mockito.doNothing().when(propertyService).updateProperty(any(PropertyDTO.class), anyList());

        mockMvc.perform(multipart("/api/properties")
                        .file(propertyFile)
                        .file(imageFile)
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        })
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(content().string("Property updated successfully."));
    }

    @Test
    void testDeleteProperty_Success() throws Exception {
        Mockito.doNothing().when(propertyService).deleteProperty(any(String.class));

        mockMvc.perform(delete("/api/properties/{propertyId}", "12345"))
                .andExpect(status().isOk())
                .andExpect(content().string("Property deleted successfully."));
    }

    @Test
    void testGetPropertyById_Success() throws Exception {
        Mockito.when(propertyService.getPropertyById(any(String.class))).thenReturn(propertyDTO);

        mockMvc.perform(get("/api/properties/{propertyId}", "12345"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Property"));
    }

    @Test
    void testGetPropertyById_NotFound() throws Exception {
        Mockito.when(propertyService.getPropertyById(any(String.class))).thenReturn(null);

        mockMvc.perform(get("/api/properties/{propertyId}", "12345"))
                .andExpect(status().isNotFound());
    }
}
