package com.unir.services.impl;

import com.amazonaws.services.lambda.runtime.Context;
import com.unir.dtos.BovineDTO;
import com.unir.dtos.util.MessageDTO;
import com.unir.entities.Bovine;
import com.unir.mapper.BovineMapper;
import com.unir.repository.BovineRepository;
import com.unir.services.BovineService;

import java.util.List;
import java.util.stream.Collectors;

public class BovineServiceImpl implements BovineService {

    private BovineRepository bovineRepository;
    private BovineMapper bovineMapper;

    public BovineServiceImpl(BovineRepository bovineRepository, BovineMapper bovineMapper) {
        this.bovineRepository = bovineRepository;
        this.bovineMapper = bovineMapper;
    }

    @Override
    public List<BovineDTO> findAll(Context context) {
        return bovineRepository.findAll().stream().map(bovineMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<BovineDTO> findById(Long id) {
        return List.of();
    }

    @Override
    public List<BovineDTO> findByGender(String gender) {
        return bovineRepository.findByGender(gender).stream().map(bovineMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public MessageDTO insert(Bovine bovine) {
        return null;
    }

    @Override
    public MessageDTO update(Bovine bovine) {
        return null;
    }

    @Override
    public MessageDTO deleteById(Long id) {
        return null;
    }
}
