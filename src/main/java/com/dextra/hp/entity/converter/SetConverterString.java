package com.dextra.hp.entity.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.*;

@Converter
public class SetConverterString implements AttributeConverter<Set<String>, String> {

  @Override
  public String convertToDatabaseColumn(Set<String> list) {
    return String.join(",", list);
  }

  @Override
  public Set<String> convertToEntityAttribute(String joined) {
    return new HashSet<>(Arrays.asList(joined.split(",")));
  }

}