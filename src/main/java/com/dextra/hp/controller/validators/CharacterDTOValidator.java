package com.dextra.hp.controller.validators;

import com.dextra.hp.controller.request.CharacterRequestDTO;
import com.dextra.hp.entity.HpCharacter_;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CharacterDTOValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return CharacterRequestDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, HpCharacter_.NAME, "field.required", new String[]{HpCharacter_.NAME});
    }
}
