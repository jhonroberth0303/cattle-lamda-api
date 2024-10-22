package com.unir.services;

import com.amazonaws.services.lambda.runtime.Context;
import com.unir.dtos.BovineDTO;
import com.unir.dtos.util.MessageDTO;
import com.unir.entities.Bovine;

import java.util.List;

public interface BovineService {

    List<BovineDTO> findAll(Context context);
    List<BovineDTO> findById(Long id);
    List<BovineDTO> findByGender(String gender);
    MessageDTO insert(Bovine bovine);
    MessageDTO update(Bovine bovine);
    MessageDTO deleteById(Long id);

}
