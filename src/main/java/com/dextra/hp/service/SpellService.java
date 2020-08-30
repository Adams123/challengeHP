package com.dextra.hp.service;

import com.dextra.hp.client.SpellsFeignRepository;
import com.dextra.hp.entity.Spell;
import com.dextra.hp.exception.UnauthorizedEntityAccessException;
import com.dextra.hp.repository.SpellRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Locale;
import java.util.Optional;

import static com.dextra.hp.exception.ExceptionLocalization.*;

@Service
@Slf4j
public class SpellService {

    private final SpellRepository repository;
    private final SpellsFeignRepository feignRepository;
    private final MessageSource messageSource;

    public SpellService(SpellRepository repository,
                        SpellsFeignRepository charactersClient,
                        MessageSource messageSource) {
        this.repository = repository;
        this.feignRepository = charactersClient;
        this.messageSource = messageSource;
    }

    public Page<Spell> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Spell findBySpellId(String spellId) throws UnauthorizedEntityAccessException {
        Optional<Spell> characterOpt = repository.findById(spellId);
        if(characterOpt.isPresent()) {
            if (characterOpt.get().isDeleted()) {
                log.error("Tried to access deleted spell: {}", spellId);
                String message = messageSource.getMessage(DELETED_ENTITY_MESSAGE, new String[]{spellId}, null, Locale.getDefault());
                throw new UnauthorizedEntityAccessException(message);
            }
            return characterOpt.get();
        }
        else {
            log.debug("Spell {} not found, looking for it on API", spellId);
            Optional<Spell> spellOpt = feignRepository.getSpells().stream().filter(spell -> spell.get_id().equals(spellId)).findFirst();
            if(spellOpt.isPresent() &&
                    !StringUtils.equals(spellOpt.get().getSpell(),"CastError")) { //API returns weird response when character is not found...
                log.debug("Spell {} found on API, updating db", spellId);
                return repository.save(spellOpt.get());
            } else {
                String message = messageSource.getMessage(SPELL_NOT_FOUND_MESSAGE, new String[] {spellId}, null, Locale.getDefault());
                throw new EntityNotFoundException(message);
            }
        }
    }
}
