package com.github.zelmothedragon.whiteapp.ejb;

import com.github.zelmothedragon.whiteapp.enterprise.ejb.Service;
import com.github.zelmothedragon.whiteapp.enterprise.persistence.Identifiable;
import com.github.zelmothedragon.whiteapp.enterprise.persistence.Pagination;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Service métier commun.
 *
 * @author MOSELLE Maxime
 */
public interface DynamicService {

    /**
     * @see Service#save
     * @param <K> Type de l'identifiant unique
     * @param <E> Type générique de l'entité
     * @param entity Nouvelle entité
     * @return L'entité sauvegardée
     */
    <K extends Serializable, E extends Identifiable<K>> E save(E entity);

    /**
     * @see Service#remove
     * @param <K> Type de l'identifiant unique
     * @param <E> Type générique de l'entité
     * @param entity Entité métier
     */
    <K extends Serializable, E extends Identifiable<K>> void remove(E entity);

    /**
     * @see Service#remove
     * @param <K> Type de l'identifiant unique
     * @param <E> Type générique de l'entité
     * @param entityClass Classe de l'entité
     * @param id Identifiant unique
     */
    <K extends Serializable, E extends Identifiable<K>> void remove(Class<E> entityClass, K id);

    /**
     * @see Service#contains
     * @param <K> Type de l'identifiant unique
     * @param <E> Type générique de l'entité
     * @param entity Entité métier
     * @return La valeur {@code true} si l'entité existe, sinon la valeur
     * {@code false} est retournée
     */
    <K extends Serializable, E extends Identifiable<K>> boolean contains(E entity);

    /**
     * @see Service#contains
     * @param <K> Type de l'identifiant unique
     * @param <E> Type générique de l'entité
     * @param entityClass Classe de l'entité
     * @param id Identifiant unique
     * @return La valeur {@code true} si l'entité existe, sinon la valeur
     * {@code false} est retournée
     */
    <K extends Serializable, E extends Identifiable<K>> boolean contains(Class<E> entityClass, K id);

    /**
     * Supprimer toutes les entités.
     *
     * @param <K> Type de l'identifiant unique
     * @param <E> Type générique de l'entité
     * @param entityClass Classe de l'entité
     */
    <K extends Serializable, E extends Identifiable<K>> void clear(Class<E> entityClass);

    /**
     * @see Service#find
     * @param <K> Type de l'identifiant unique
     * @param <E> Type générique de l'entité
     * @param entityClass Classe de l'entité
     * @param id Identifiant unique
     * @return Une option contenant ou non l'entité correspondante à
     * l'identifiant unique
     */
    <K extends Serializable, E extends Identifiable<K>> Optional<E> find(Class<E> entityClass, K id);

    /**
     * @see Service#values
     * @param <K> Type de l'identifiant unique
     * @param <E> Type générique de l'entité
     * @param entityClass Classe de l'entité
     * @return Une liste des entités métiers
     */
    <K extends Serializable, E extends Identifiable<K>> List<E> values(Class<E> entityClass);

    /**
     * @see Service#filter(Pagination)
     * @param <K> Type de l'identifiant unique
     * @param <E> Type générique de l'entité
     * @param entityClass Classe de l'entité
     * @param pagination Critère de pagination
     * @return Une liste des entités métiers
     */
    <K extends Serializable, E extends Identifiable<K>> List<E> filter(Class<E> entityClass, Pagination pagination);

    /**
     * @see Service#filter(String)
     * @param <K> Type de l'identifiant unique
     * @param <E> Type générique de l'entité
     * @param entityClass Classe de l'entité
     * @param keyword Mot clef pour la recherche
     * @return Une liste des entités persistantes
     */
    <K extends Serializable, E extends Identifiable<K>> List<E> filter(Class<E> entityClass, String keyword);

}
