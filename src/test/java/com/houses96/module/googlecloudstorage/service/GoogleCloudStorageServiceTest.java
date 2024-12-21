package com.houses96.module.googlecloudstorage.service;

import com.google.cloud.storage.*;
import com.houses96.module.googlecloudstorage.service.GoogleCloudStorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GoogleCloudStorageServiceTest {

    @Mock
    private Storage storage;

    @Mock
    private Bucket bucket;

    @Mock
    private Blob blob;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private GoogleCloudStorageService googleCloudStorageService;

    private final String bucketName = "test-houses96.firebasestorage.app";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void uploadFile_success() throws IOException {
        // Arrange
        when(storage.get(bucketName)).thenReturn(bucket);
        when(multipartFile.getOriginalFilename()).thenReturn("test-file.txt");
        when(multipartFile.getContentType()).thenReturn("text/plain");
        when(multipartFile.getBytes()).thenReturn("Sample Content".getBytes());

        BlobId blobId = BlobId.of(bucketName, "test-file.txt");
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build();
        when(storage.create(blobInfo, "Sample Content".getBytes())).thenReturn(blob);
        when(blob.getMediaLink()).thenReturn("http://storage.googleapis.com/test-houses96.firebasestorage.app/test-file.txt");

        // Act
        String mediaLink = googleCloudStorageService.uploadFile(multipartFile);

        // Assert
        assertNotNull(mediaLink);
    }

    @Test
    void downloadFile_success() throws IOException {
        // Arrange
        when(storage.get(bucketName)).thenReturn(bucket);
        when(storage.get(BlobId.of(bucketName, "test-file.txt"))).thenReturn(blob);
        when(blob.getContent()).thenReturn("Sample Content".getBytes());

        // Act
        byte[] content = googleCloudStorageService.downloadFile("test-file.txt");

        // Assert
        assertArrayEquals("Sample Content".getBytes(), content);
    }

    @Test
    void deleteFile_success() throws IOException {
        // Arrange
        when(storage.get(bucketName)).thenReturn(bucket);
        when(storage.delete(BlobId.of(bucketName, "test-file.txt"))).thenReturn(true);

        // Act
        boolean deleted = googleCloudStorageService.deleteFile("test-file.txt");

        // Assert
        assertTrue(deleted);
    }

}
