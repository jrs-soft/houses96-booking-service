package com.houses96.module.property.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.houses96.module.property.entity.PropertyEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PropertyRepositoryTest {

    @Mock
    private Firestore db;

    @Mock
    private CollectionReference collectionReference;

    @Mock
    private DocumentReference documentReference;

    @Mock
    private ApiFuture<DocumentSnapshot> apiFuture;

    @Mock
    private DocumentSnapshot documentSnapshot;

    @InjectMocks
    private PropertyRepository propertyRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInsertProperty() throws ExecutionException, InterruptedException {
        // Given
        PropertyEntity property = new PropertyEntity();
        property.setId("123");

        when(db.collection("properties")).thenReturn(collectionReference);
        when(collectionReference.document("123")).thenReturn(documentReference);

        // When
        propertyRepository.insertProperty(property);

        // Then
        verify(db).collection("properties");
        verify(collectionReference).document("123");
        verify(documentReference).set(property);
    }

    @Test
    void testGetPropertyById_WhenExists() throws ExecutionException, InterruptedException {
        // Given
        String propertyId = "123";
        when(db.collection("properties")).thenReturn(collectionReference);
        when(collectionReference.document(propertyId)).thenReturn(documentReference);
        when(documentReference.get()).thenReturn(apiFuture);
        when(apiFuture.get()).thenReturn(documentSnapshot);
        when(documentSnapshot.exists()).thenReturn(true);
        PropertyEntity property = new PropertyEntity();
        when(documentSnapshot.toObject(PropertyEntity.class)).thenReturn(property);

        // When
        PropertyEntity result = propertyRepository.getPropertyById(propertyId);

        // Then
        assertNotNull(result);
        verify(db).collection("properties");
        verify(documentReference).get();
    }

    @Test
    void testGetPropertyById_WhenNotExists() throws ExecutionException, InterruptedException {
        // Given
        String propertyId = "123";
        when(db.collection("properties")).thenReturn(collectionReference);
        when(collectionReference.document(propertyId)).thenReturn(documentReference);
        when(documentReference.get()).thenReturn(apiFuture);
        when(apiFuture.get()).thenReturn(documentSnapshot);
        when(documentSnapshot.exists()).thenReturn(false);

        // When
        PropertyEntity result = propertyRepository.getPropertyById(propertyId);

        // Then
        assertNull(result);
        verify(db).collection("properties");
        verify(documentReference).get();
    }

    @Test
    void testUpdateProperty() throws ExecutionException, InterruptedException {
        // Given
        PropertyEntity property = new PropertyEntity();
        property.setId("123");

        when(db.collection("properties")).thenReturn(collectionReference);
        when(collectionReference.document("123")).thenReturn(documentReference);

        // When
        propertyRepository.updateProperty(property);

        // Then
        verify(db).collection("properties");
        verify(collectionReference).document("123");
        verify(documentReference).set(property);
    }

    @Test
    void testDeleteProperty() throws ExecutionException, InterruptedException {
        // Given
        String propertyId = "123";

        when(db.collection("properties")).thenReturn(collectionReference);
        when(collectionReference.document(propertyId)).thenReturn(documentReference);

        // When
        propertyRepository.deleteProperty(propertyId);

        // Then
        verify(db).collection("properties");
        verify(collectionReference).document(propertyId);
        verify(documentReference).delete();
    }
}
