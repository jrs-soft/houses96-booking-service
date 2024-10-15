package com.houses96.module.city.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.ServerTimestamp;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CityEntity {
    @DocumentId
    private String id;

    private String name;

    private String stateId; // Reference to State

    @ServerTimestamp
    private Date createdAt;

}



