package com.github.zelmothedragon.whiteapp.persistence;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Convertisseur de type bidirectionnel.
 *
 * @author MOSELLE Maxime
 */
@Converter(autoApply = true)
public class UUIDConverter implements AttributeConverter<UUID, String> {

    /**
     * Type de la colonne en base de données.
     */
    public static final String COLUMN_DEFINITION = "VARCHAR(36)";

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * de Jakarta EE.
     */
    public UUIDConverter() {
        // Ne pas appeler explicitement
    }

    @Override
    public String convertToDatabaseColumn(final UUID attribute) {
        String dbData;
        if (Objects.nonNull(attribute)) {
            dbData = attribute.toString();
        } else {
            dbData = null;
        }
        return dbData;
    }

    @Override
    public UUID convertToEntityAttribute(final String dbData) {
        UUID attribute;
        if (Objects.nonNull(dbData)) {
            attribute = UUID.fromString(dbData);
        } else {
            attribute = null;
        }
        return attribute;
    }

}
