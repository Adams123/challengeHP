package com.dextra.hp.service;

import com.dextra.hp.consumer.HpCharactersFeignRepo;
import com.dextra.hp.controller.dto.CharacterDTO;
import com.dextra.hp.entity.House;
import com.dextra.hp.entity.HpCharacter;
import com.dextra.hp.exception.UnauthorizedEntityAccessException;
import com.dextra.hp.repository.HpCharacterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import static com.dextra.hp.exception.ExceptionLocalization.CHARACTER_NOT_FOUND_MESSAGE;
import static com.dextra.hp.exception.ExceptionLocalization.DELETED_ENTITY_MESSAGE;

@Service
@Slf4j
public class HpCharacterService {

    private final HpCharacterRepository repository;
    private final HpCharactersFeignRepo feignRepository;
    private final MessageSource messageSource;
    private final HousesService housesService;

    public HpCharacterService(HpCharacterRepository repository,
                              HpCharactersFeignRepo charactersConsumer,
                              MessageSource messageSource, HousesService housesService) {
        this.repository = repository;
        this.feignRepository = charactersConsumer;
        this.messageSource = messageSource;
        this.housesService = housesService;
    }

    public Page<HpCharacter> findAll(Pageable pageable) {
        return repository.findAllByDeletedFalse(pageable);
    }

    public HpCharacter findCharacterById(String characterId) throws UnauthorizedEntityAccessException {
        Optional<HpCharacter> characterOpt = repository.findById(characterId);
        if(characterOpt.isPresent()) {
            if (characterOpt.get().isDeleted()) {
                log.error("Tried to access deleted character: {}", characterId);
                String message = messageSource.getMessage(DELETED_ENTITY_MESSAGE, new String[]{characterId}, null, Locale.getDefault());
                throw new UnauthorizedEntityAccessException(message);
            }
            return characterOpt.get();
        }
        else {
            HpCharacter hpCharacter = feignRepository.getCharacter(characterId);
            if(Objects.nonNull(hpCharacter)) {
                return hpCharacter;
            } else {
                String message = messageSource.getMessage(CHARACTER_NOT_FOUND_MESSAGE, new String[] {characterId}, null, Locale.getDefault());
                throw new EntityNotFoundException(message);
            }
        }
    }

    public HpCharacter createCharacter(CharacterDTO dto) throws UnauthorizedEntityAccessException {
        HpCharacter newCharacter = new HpCharacter(dto);
        House house = housesService.findHouseById(dto.getHouse());
        if(house!=null) {
            newCharacter.setBelongingHouse(house);
        }
        return repository.save(newCharacter);
    }

    public HpCharacter updateCharacter(CharacterDTO dto) throws UnauthorizedEntityAccessException {
        HpCharacter hpCharacter = findCharacterById(dto.getId());
        House house = housesService.findHouseById(dto.getHouse());
        hpCharacter.updateFromDto(dto);
        hpCharacter.setBelongingHouse(house);
        if(house!=null) {
            hpCharacter.setHouse(house.getName());
        }

        return repository.save(hpCharacter);
    }

    public String deleteCharacter(String id) throws UnauthorizedEntityAccessException {
        HpCharacter character = findCharacterById(id);
        character.setDeleted(true);
        character.getBelongingHouse().getPersistedMembers().remove(character);
        repository.save(character);
        return character.getName();
    }
}
