package com.houses96.module.googlecloudstorage.property.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.ServerTimestamp;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PropertyEntity {
    @DocumentId
    private String id;

    private String hostId;

    private String title;

    private String description;

    private String descriptionType;

    private String stateId;

    private String cityId;

    private String address;

    private String zipCode;

    private String latitude;

    private String longitude;

    private String typeId;

    private String typeOfPlace;

    private List<String> amenityIds;

    private List<String> pictures;

    private Double pricePerNight;

    private int numberOfBedrooms;

    private int numberOfBathrooms;

    private int numberOfBeds;

    private int maximumNumberOfGuests;

    private int firstBookingType;

    @ServerTimestamp
    private Date createdAt;

    @ServerTimestamp
    private Date modifiedAt;

    private String status;

}



