package com.schoolplus.back.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MemberTypeConverter implements AttributeConverter<MemberType, String> {
    @Override
    public String convertToDatabaseColumn(MemberType attribute) {
        return attribute != null ? attribute.toString() : null;
    }

    @Override
    public MemberType convertToEntityAttribute(String dbData) {
        return dbData != null ? MemberType.fromCode(dbData) : null;
    }
}
