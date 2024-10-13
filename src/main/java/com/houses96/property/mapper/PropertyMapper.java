package com.houses96.property.mapper;

import com.houses96.property.dto.PropertyDTO;
import com.houses96.property.entity.PropertyEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PropertyMapper {

    private final ModelMapper modelMapper;

    public PropertyMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // Convert PropertyEntity to PropertyDTO
    public PropertyDTO toDTO(PropertyEntity propertyEntity) {
        return modelMapper.map(propertyEntity, PropertyDTO.class);
    }

    // Convert PropertyDTO to PropertyEntity
    public PropertyEntity toEntity(PropertyDTO propertyDTO) {
        return modelMapper.map(propertyDTO, PropertyEntity.class);
    }
}
