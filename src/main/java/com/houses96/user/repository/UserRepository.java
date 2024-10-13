package com.houses96.user.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.houses96.user.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.concurrent.ExecutionException;

@Repository
public class UserRepository {

    @Autowired
    private Firestore db;
    private static final String COLLECTION_NAME = "users";

    // Create or Insert a new user
    public void insertUser(UserEntity userEntity) throws ExecutionException, InterruptedException {
        db.collection(COLLECTION_NAME).document(userEntity.getId()).set(userEntity);
    }

    // Retrieve a user by ID
    public UserEntity getUserById(String userId) throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(userId);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            return document.toObject(UserEntity.class);
        } else {
            return null;
        }
    }

    // Update user information
    public void updateUser(UserEntity userEntity) throws ExecutionException, InterruptedException {
        db.collection(COLLECTION_NAME).document(userEntity.getId()).set(userEntity);
    }

    // Delete a user by ID
    public void deleteUser(String userId) throws ExecutionException, InterruptedException {
        db.collection(COLLECTION_NAME).document(userId).delete();
    }

}
