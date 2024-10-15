package com.houses96.module.city.service;

import com.houses96.module.city.dto.CityDTO;
import com.houses96.module.city.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class CityService {

    private final CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<CityDTO> findCityByName(String cityNameLike) throws ExecutionException, InterruptedException {
        return cityRepository.findCityByName(cityNameLike);
    }

}

