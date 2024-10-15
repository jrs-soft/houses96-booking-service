package com.houses96.module.state.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.QuerySnapshot;
import com.houses96.module.state.entity.StateEntity;
import com.houses96.module.state.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class StateService {

    private final StateRepository stateRepository;

    @Autowired
    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    public ApiFuture<QuerySnapshot> getAllStates() {
        return stateRepository.getAllStates();
    }

    public String saveState(StateEntity state) {
        try {
            return stateRepository.saveState(state);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException("Error saving state to Firestore", e);
        }
    }

}