package com.dextra.hp.service;

import com.dextra.hp.client.HousesFeignRepo;
import com.dextra.hp.client.HpCharactersFeignRepo;
import com.dextra.hp.client.SpellsFeignRepository;
import com.dextra.hp.entity.House;
import com.dextra.hp.entity.HpCharacter;
import com.dextra.hp.entity.Populated;
import com.dextra.hp.entity.constants.Constants;
import com.dextra.hp.repository.HouseRepository;
import com.dextra.hp.repository.HpCharacterRepository;
import com.dextra.hp.repository.PopulatedRepository;
import com.dextra.hp.repository.SpellRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
@Profile("default")
public class DbPopulateStartup implements ApplicationListener<ApplicationReadyEvent> {

    private final HpCharactersFeignRepo hpCharactersFeignRepo;
    private final HousesFeignRepo housesFeignRepo;
    private final SpellsFeignRepository spellsFeignRepository;

    private final SpellRepository spellRepository;
    private final HpCharacterRepository hpCharacterRepository;
    private final HouseRepository houseRepository;

    private final PopulatedRepository populatedRepository;

    public DbPopulateStartup(HpCharactersFeignRepo hpCharactersFeignRepo, HousesFeignRepo housesFeignRepo, SpellsFeignRepository spellsFeignRepository,
                             SpellRepository spellRepository, HpCharacterRepository hpCharacterRepository, HouseRepository houseRepository, PopulatedRepository populatedRepository) {
        this.hpCharactersFeignRepo = hpCharactersFeignRepo;
        this.housesFeignRepo = housesFeignRepo;
        this.spellsFeignRepository = spellsFeignRepository;
        this.spellRepository = spellRepository;
        this.hpCharacterRepository = hpCharacterRepository;
        this.houseRepository = houseRepository;
        this.populatedRepository = populatedRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Optional<Populated> populated = populatedRepository.findById(Constants.DB_IDENTIFIER);
        if (populated.isPresent() && populated.get().isPopulated()) {
            log.debug("DB for {} is already populated, skipping", Constants.DB_IDENTIFIER);
            return;
        }
        try {
            List<House> houses = houseRepository.saveAll(housesFeignRepo.getHouses());
            Map<String, List<House>> houseNames = houses.stream().collect(Collectors.groupingBy(house -> house.getName().name()));

            List<HpCharacter> hpCharacters = hpCharactersFeignRepo.getCharacters();
            hpCharacters.stream().filter(entry -> StringUtils.isNotBlank(entry.getHouse())).forEach(entry ->
                    entry.setBelongingHouse(houseNames.get(entry.getHouse()).get(0)));

            hpCharacterRepository.saveAll(hpCharacters);
            spellRepository.saveAll(spellsFeignRepository.getSpells());
            populatedRepository.save(new Populated(Constants.DB_IDENTIFIER, true));
            log.debug("DB {} populated successfully", Constants.DB_IDENTIFIER);

        } catch (Exception e) {
            populatedRepository.save(new Populated(Constants.DB_IDENTIFIER, false));
            log.error("Failed to initialize db repository with API data.", e);
        }
    }
}
