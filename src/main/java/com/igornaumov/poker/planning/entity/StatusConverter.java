package com.igornaumov.poker.planning.entity;

import com.igornaumov.model.Status;
import jakarta.persistence.AttributeConverter;

public class StatusConverter implements AttributeConverter<Status, String> {

    @Override
    public String convertToDatabaseColumn(Status attr) {

        if (attr == null) {
            return null;
        }

        return attr.name();
    }

    @Override
    public Status convertToEntityAttribute(String dbData) {

        if (dbData == null) {
            return null;
        }

        return Status.fromValue(dbData);
    }
}
