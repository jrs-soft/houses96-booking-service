package com.houses96.user.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.ServerTimestamp;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserEntity {
    @DocumentId
    private String id;

    private String name;

    private String email;

    private String pwd;

    private String phoneNumber;

    private String profilePicture;

    @ServerTimestamp
    private Date createdAt;

    @ServerTimestamp
    private Date modifiedAt;

    private String status;

}



