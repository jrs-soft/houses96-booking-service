package com.houses96.module.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserDTO {

    private String id;

    private String name;

    private String email;

    private String pwd;

    private String phoneNumber;

    private String profilePicture;

    private Date createdAt;

    private Date modifiedAt;

    private String status;

}



