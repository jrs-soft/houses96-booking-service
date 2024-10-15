package com.houses96.module.city.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.houses96.module.city.dto.CityDTO;
import com.houses96.module.city.entity.CityEntity;
import com.houses96.module.state.dto.StateDTO;
import com.houses96.module.state.entity.StateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class CityRepository {

    @Autowired
    private Firestore db;

    private static final String CITY_COLLECTION_NAME = "cities"; // Assuming the collection name is "cities"
    private static final String STATE_COLLECTION_NAME = "states";

    public List<CityDTO> findCityByName(String cityNameLike) throws ExecutionException, InterruptedException {
        CollectionReference cities = db.collection(CITY_COLLECTION_NAME);
        ApiFuture<QuerySnapshot> query = cities.whereGreaterThanOrEqualTo("name", cityNameLike)
                .whereLessThanOrEqualTo("name", cityNameLike + "\uf8ff")
                .limit(3)
                .get();

        List<QueryDocumentSnapshot> documents = query.get().getDocuments();
        List<CityDTO> result = new ArrayList<>();

        for (QueryDocumentSnapshot document : documents) {
            CityEntity city = document.toObject(CityEntity.class);
            StateEntity state = getStateById(city.getStateId());

            StateDTO stateDTO = new StateDTO(state.getId(), state.getName(), state.getCreatedAt());
            CityDTO cityDTO = new CityDTO(city.getId(), city.getName(), stateDTO, city.getCreatedAt());

            result.add(cityDTO);
        }

        return result;
    }

    private StateEntity getStateById(String stateId) throws ExecutionException, InterruptedException {
        DocumentReference stateRef = db.collection(STATE_COLLECTION_NAME).document(stateId);
        ApiFuture<DocumentSnapshot> future = stateRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            return document.toObject(StateEntity.class);
        } else {
            throw new RuntimeException("State not found for ID: " + stateId);
        }
    }

}
