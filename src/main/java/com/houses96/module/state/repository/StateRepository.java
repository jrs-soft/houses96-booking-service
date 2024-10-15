package com.houses96.module.state.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.houses96.module.state.entity.StateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class StateRepository {

    @Autowired
    private Firestore db;

    public String saveState(StateEntity state) throws ExecutionException, InterruptedException {
        // Set the document ID
        if (state.getId() == null || state.getId().isEmpty()) {
            state.setId(db.collection("states").document().getId());
        }

        // Save the state to Firestore
        WriteResult writeResult = db.collection("states")
                .document(state.getId())
                .set(state)
                .get();

        return writeResult.getUpdateTime().toString();
    }

    public ApiFuture<QuerySnapshot> getAllStates() {
        return db.collection("states").get();
    }

}
