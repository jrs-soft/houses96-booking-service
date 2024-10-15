package com.houses96.module.booking.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.ServerTimestamp;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class Booking {
    @DocumentId
    private String id;

    private String userId;

    private String propertyId;

    private Date checkInDate;

    private Date checkOutDate;

    private int numberOfGuests;

    private Double totalPrice;

    @ServerTimestamp
    private Date createdAt;

    @ServerTimestamp
    private Date modifiedAt;

    private String status;

}



