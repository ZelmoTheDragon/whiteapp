package com.github.zelmothedragon.whiteapp.persistence.entity;

import com.github.zelmothedragon.whiteapp.enterprise.persistence.Identifiable;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Entité dynamique.
 *
 * @author MOSELLE Maxime
 */
public enum DynamicEntity {

    ACCOUNT("account", Account.class, UUID::fromString, Account::new),
    AGENT("agent", Agent.class, UUID::fromString, Agent::new),
    ORGANIZATION("organization", Organization.class, UUID::fromString, Organization::new);

    /**
     * Nom technique de l'entité.
     */
    private final String typeName;

    /**
     * Classe de l'entité.
     */
    private final Class<? extends Identifiable<? extends Serializable>> entityClass;

    /**
     * Fonction de conversion de l'identifiant unique.
     */
    private final Function<String, Object> identifierConverter;

    /**
     * Fonction d'instanciation de l'entité. Doit faire appel au constructeur
     * par défaut.
     */
    private final Supplier<Identifiable<? extends Serializable>> constructor;

    /**
     * Constructeur interne. Pas d'instanciation.
     *
     * @param typeName Nom technique de l'entité
     * @param entityClass Classe de l'entité
     * @param identifierConverter Fonction de conversion de l'identifiant unique
     * @param constructor Fonction d'instanciation de l'entité
     */
    DynamicEntity(
            final String typeName,
            final Class<? extends Identifiable<? extends Serializable>> entityClass,
            final Function<String, Object> identifierConverter,
            final Supplier<Identifiable<? extends Serializable>> constructor) {

        this.typeName = typeName;
        this.entityClass = entityClass;
        this.identifierConverter = identifierConverter;
        this.constructor = constructor;
    }

    /**
     * Rechercher l'entité dynamique à partir d'un nom technique.
     *
     * @param typeName Nom technique
     * @return Une option contenant ou non l'entité technique
     */
    public static Optional<DynamicEntity> fromTypeName(final String typeName) {
        return List
                .of(values())
                .stream()
                .filter(e -> Objects.equals(e.typeName, typeName))
                .findFirst();
    }

    /**
     * Convertir l'identifiant unique sous son type réel.
     *
     * @param <T> Type réel Java
     * @param id Identifiant unique sous forme de texte
     * @return L'identifiant unique sous le bon type
     */
    public <T> T convertAsIdentifier(final String id) {
        return (T) identifierConverter.apply(id);
    }

    /**
     * Construire une instance vierge de l'entité.
     *
     * @return Une nouvelle instance
     */
    public Identifiable<?> newInstance() {
        return constructor.get();
    }

    // ------------------------------
    // Accesseurs 
    // ------------------------------
    public String getTypeName() {
        return typeName;
    }

    public Class<? extends Identifiable<?>> getEntityClass() {
        return entityClass;
    }

}
