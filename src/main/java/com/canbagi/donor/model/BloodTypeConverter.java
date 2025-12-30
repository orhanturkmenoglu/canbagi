package com.canbagi.donor.model;

import com.canbagi.donor.enums.BloodType;
import jakarta.persistence.AttributeConverter;
import lombok.extern.slf4j.Slf4j;

@jakarta.persistence.Converter(autoApply = true)
@Slf4j
public class BloodTypeConverter  implements AttributeConverter<BloodType,String> {

    @Override
    public String convertToDatabaseColumn(BloodType attribute) {
        log.info("convertToDatabaseColumn: {}", attribute);
        return attribute !=null ? attribute.getDisplayName() : null;
    }

    @Override
    public BloodType convertToEntityAttribute(String dbData) {
        log.info("convertToEntityAttribute: {}", dbData);
        return dbData !=null ? BloodType.fromDisplayName(dbData) : null;
    }
}
