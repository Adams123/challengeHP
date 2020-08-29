package com.dextra.hp.entity.converter;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Converter
public class SetConverterString implements AttributeConverter<Set<String>, String> {

    @Override
    public String convertToDatabaseColumn(Set<String> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            return String.join(",", list);
        } else {
          return "";
        }
    }

    @Override
    public Set<String> convertToEntityAttribute(String joined) {
        return new HashSet<>(Arrays.asList(StringUtils.defaultString(joined).split(",")));
    }

}