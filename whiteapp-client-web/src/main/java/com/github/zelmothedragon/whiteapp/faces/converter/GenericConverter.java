package com.github.zelmothedragon.whiteapp.faces.converter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Convertisseur générique.
 *
 * @author MOSELLE Maxime
 */
@FacesConverter(managed = true)
public class GenericConverter implements Converter<Object> {

    /**
     * Cache temporaire.
     */
    private final Map<UUID, Object> cache;

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * d'entreprise.
     */
    public GenericConverter() {
        this.cache = new HashMap<>();
        // Ne pas appeler explicitement.
    }

    @Override
    public Object getAsObject(
            final FacesContext context,
            final UIComponent component,
            final String value) {

        var uuid = UUID.fromString(value);
        return cache.get(uuid);
    }

    @Override
    public String getAsString(
            final FacesContext context,
            final UIComponent component,
            final Object data) {

        var uuid = UUID.randomUUID();
        this.cache.put(uuid, data);
        return uuid.toString();
    }

}
