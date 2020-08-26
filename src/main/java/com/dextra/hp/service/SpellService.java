package com.dextra.hp.service;

import com.dextra.hp.consumer.SpellsFeignRepository;
import com.dextra.hp.entity.Spell;
import com.dextra.hp.exception.UnauthorizedEntityAccessException;
import com.dextra.hp.repository.SpellRepository;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Locale;
import java.util.Optional;

import static com.dextra.hp.exception.ExceptionLocalization.*;

@Service
public class SpellService {

    private final SpellRepository repository;
    private final SpellsFeignRepository feignRepository;
    private final MessageSource messageSource;

    public SpellService(SpellRepository repository,
                        SpellsFeignRepository charactersConsumer,
                        MessageSource messageSource) {
        this.repository = repository;
        this.feignRepository = charactersConsumer;
        this.messageSource = messageSource;
    }

    public Page<Spell> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Spell findCharacterById(String spellId) throws UnauthorizedEntityAccessException {
        Optional<Spell> spellOpt = repository.findById(spellId);
        if (spellOpt.isPresent()) {
            if (spellOpt.get().isDeleted()) {
                String message = messageSource.getMessage(DELETED_ENTITY_MESSAGE, new String[]{spellId}, null, Locale.getDefault());
                throw new UnauthorizedEntityAccessException(message);
            }
        } else {
            String message = messageSource.getMessage(SPELL_NOT_FOUND_MESSAGE, new String[]{spellId}, null, Locale.getDefault());
            throw new EntityNotFoundException(message);
        }
        return spellOpt.get();
    }
}
