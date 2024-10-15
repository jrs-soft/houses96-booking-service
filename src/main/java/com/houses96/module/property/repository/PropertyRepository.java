package com.houses96.module.googlecloudstorage.property.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.houses96.module.googlecloudstorage.property.entity.PropertyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ExecutionException;

@Repository
public class PropertyRepository {

    @Autowired
    private Firestore db;
    private static final String COLLECTION_NAME = "properties";

    // Create or Insert a new property
    public void insertProperty(PropertyEntity propertyEntity) throws ExecutionException, InterruptedException {
        db.collection(COLLECTION_NAME).document(propertyEntity.getId()).set(propertyEntity);
    }

    // Retrieve a property by ID
    public PropertyEntity getPropertyById(String propertyId) throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(propertyId);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            return document.toObject(PropertyEntity.class);
        } else {
            return null;
        }
    }

    // Update property information
    public void updateProperty(PropertyEntity propertyEntity) throws ExecutionException, InterruptedException {
        db.collection(COLLECTION_NAME).document(propertyEntity.getId()).set(propertyEntity);
    }

    // Delete a property by ID
    public void deleteProperty(String propertyId) throws ExecutionException, InterruptedException {
        db.collection(COLLECTION_NAME).document(propertyId).delete();
    }

}
