package com.houses96.module.city.dto;

import com.houses96.module.state.dto.StateDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class CityDTO {
    private String id;
    private String name;
    private StateDTO state;
    private Date createdAt;
}
