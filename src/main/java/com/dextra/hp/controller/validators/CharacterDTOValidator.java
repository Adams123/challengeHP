package com.dextra.hp.controller.validators;

import com.dextra.hp.controller.dto.CharacterDTO;
import com.dextra.hp.entity.HpCharacter_;
import com.dextra.hp.service.HpCharacterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("characterDtoValidator")
public class CharacterDTOValidator implements Validator {

    private final HpCharacterService hpCharacterService;
    private final MessageSource messageSource;

    public CharacterDTOValidator(HpCharacterService hpCharacterService, MessageSource messageSource) {
        this.hpCharacterService = hpCharacterService;
        this.messageSource = messageSource;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(CharacterDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CharacterDTO dto = (CharacterDTO) target;
        if(StringUtils.isBlank(dto.getName())){
            errors.rejectValue(HpCharacter_.NAME, "blank.name");
        }

    }
}
