package com.houses96.module.state.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.ServerTimestamp;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class StateEntity {
    @DocumentId
    private String id;

    private String name;

    @ServerTimestamp
    private Date createdAt;

}
