package com.houses96.module.googlecloudstorage.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Service
public class GoogleCloudStorageService {

    private final String bucketName = "houses96-data-storage";

    private static Storage storage;

    static {
        try {
            InputStream serviceAccount = GoogleCloudStorageService.class.getResourceAsStream("/test-houses96-firebase-adminsdk-qlv0i-e53264f362.json");
            if (serviceAccount == null) {
                throw new IllegalArgumentException("test-houses96-firebase-adminsdk-qlv0i-e53264f362.json not found in resources folder");
            }
            // Load the service account key JSON file
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            // Initialize the storage service with the credentials
            storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Storage getStorage() {
        return storage;
    }

    // Check if bucket exists
    private boolean bucketExists() {
        Bucket bucket = getStorage().get(bucketName);
        return bucket != null;
    }

    // Upload file to Google Cloud Storage
    public String uploadFile(MultipartFile file) throws IOException {
        if (!bucketExists()) {
            throw new IOException("Bucket does not exist");
        }
        if (file.getOriginalFilename() != null && file.getContentType() != null) {
            BlobId blobId = BlobId.of(bucketName, Objects.requireNonNull(file.getOriginalFilename()));
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
            Blob blob = getStorage().create(blobInfo, file.getBytes());
            return blob.getMediaLink(); // URL to access the uploaded file
        } else {
            return null;
        }
    }

    // Download file from Google Cloud Storage
    public byte[] downloadFile(String fileName) throws IOException {
        if (!bucketExists()) {
            throw new IOException("Bucket does not exist");
        }

        Blob blob = getStorage().get(BlobId.of(bucketName, fileName));
        if (blob != null) {
            return blob.getContent();
        } else {
            throw new IOException("File not found in Google Cloud Storage");
        }
    }

    // Delete file from Google Cloud Storage
    public boolean deleteFile(String fileName) throws IOException {
        if (!bucketExists()) {
            throw new IOException("Bucket does not exist");
        }

        return getStorage().delete(BlobId.of(bucketName, fileName));
    }
}
