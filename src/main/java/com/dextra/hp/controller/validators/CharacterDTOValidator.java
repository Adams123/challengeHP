package com.dextra.hp.controller.validators;

import com.dextra.hp.controller.request.CharacterRequestDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Locale;

@Component
public class CharacterDTOValidator implements Validator {

    private final MessageSource messageSource;

    public CharacterDTOValidator(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return CharacterRequestDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field.required", new String[]{"name"});

        CharacterRequestDTO dto = (CharacterRequestDTO) target;
        if(StringUtils.isNotBlank(dto.getId()) && StringUtils.containsWhitespace(dto.getId())) {
            String message = messageSource.getMessage("id.space", new String[]{"id"}, null, Locale.getDefault());
            errors.rejectValue("id","id.space", new String[]{"id"}, message);
        }
    }
}
