package com.unir.mapper;

import com.unir.dtos.BovineDTO;
import com.unir.entities.Bovine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BovineMapper {

    @Mapping(target = "age", ignore = true)
    BovineDTO toDTO(Bovine source);
    Bovine toEntity(BovineDTO source);

}
