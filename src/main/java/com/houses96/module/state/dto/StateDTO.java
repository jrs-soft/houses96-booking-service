package com.houses96.module.state.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class StateDTO {

    private String id;
    private String name;
    private Date createdAt;
}
