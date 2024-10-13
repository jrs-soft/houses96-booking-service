package com.houses96.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp initializeFirebaseApp() throws IOException {
        InputStream serviceAccount = getClass().getResourceAsStream("/houses96-service-account.json");
        if (serviceAccount == null) {
            throw new IllegalArgumentException("houses96-service-account.json not found in resources folder");
        }

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://houses96-default-rtdb.firebaseio.com/")
                .build();

        FirebaseApp app = FirebaseApp.initializeApp(options);
        testConnectivity();
        return app;
    }

    public void testConnectivity() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("test-connectivity");

        // Writing data to Firebase
        ref.setValue("Hello, Firebase!", new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    System.out.println("Data written successfully.");

                    // Reading data from Firebase to test connectivity
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            System.out.println("Connected to Firebase: " + dataSnapshot.getValue());
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            System.err.println("Failed to connect to Firebase: " + error.getMessage());
                        }
                    });
                } else {
                    System.err.println("Failed to write data: " + databaseError.getMessage());
                }
            }
        });
    }

    @Bean
    public Firestore firestore(FirebaseApp firebaseApp) {
        return FirestoreClient.getFirestore(firebaseApp);
    }
}



