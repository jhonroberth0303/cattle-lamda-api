package com.unir.repository;

import com.unir.dtos.util.MessageDTO;
import com.unir.entities.Bovine;

import java.util.List;

public interface BovineRepository {

    List<Bovine> findAll();
    List<Bovine> findById(Long id);
    List<Bovine> findByGender(String gender);
    MessageDTO insert(Bovine bovine);
    MessageDTO update(Bovine bovine);
    MessageDTO deleteById(Long id);

}
