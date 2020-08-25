package com.dextra.hp.service;

import com.dextra.hp.entity.HpCharacter;
import com.dextra.hp.repository.HpCharacterRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class HpCharacterService {

    private final HpCharacterRepository repository;

    public HpCharacterService(HpCharacterRepository repository) {
        this.repository = repository;
    }

    public Page<HpCharacter> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
