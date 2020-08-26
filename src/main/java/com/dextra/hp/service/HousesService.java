package com.dextra.hp.service;

import com.dextra.hp.client.HousesFeignRepo;
import com.dextra.hp.entity.House;
import com.dextra.hp.exception.UnauthorizedEntityAccessException;
import com.dextra.hp.repository.HouseRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import static com.dextra.hp.exception.ExceptionLocalization.DELETED_ENTITY_MESSAGE;
import static com.dextra.hp.exception.ExceptionLocalization.HOUSE_NOT_FOUND_MESSAGE;

@Service
@Slf4j
public class HousesService {

    private final HouseRepository repository;
    private final HousesFeignRepo feignRepository;
    private final MessageSource messageSource;

    public HousesService(HouseRepository repository,
                         HousesFeignRepo feignRepo,
                         MessageSource messageSource) {
        this.repository = repository;
        this.feignRepository = feignRepo;
        this.messageSource = messageSource;
    }

    public Page<House> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public House findHouseById(String houseId) throws UnauthorizedEntityAccessException {
        if(StringUtils.isBlank(houseId))
            return null;
        Optional<House> houseOpt = repository.findById(houseId);
        if(houseOpt.isPresent()) {
            if(houseOpt.get().isDeleted()){
                log.error("Tried to access deleted character: {}", houseId);
                String message = messageSource.getMessage(DELETED_ENTITY_MESSAGE, new String[]{houseId}, null, Locale.getDefault());
                throw new UnauthorizedEntityAccessException(message);
            }
            return houseOpt.get();
        }else {
            House house = feignRepository.getHouse(houseId);
            if(Objects.nonNull(house)) {
                return house;
            } else {
                String message = messageSource.getMessage(HOUSE_NOT_FOUND_MESSAGE, new String[] {houseId}, null, Locale.getDefault());
                throw new EntityNotFoundException(message);
            }
        }
    }
}
