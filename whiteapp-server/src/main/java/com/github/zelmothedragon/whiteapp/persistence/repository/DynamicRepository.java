package com.github.zelmothedragon.whiteapp.persistence.repository;

import com.github.zelmothedragon.whiteapp.enterprise.persistence.Identifiable;
import com.github.zelmothedragon.whiteapp.enterprise.persistence.Repository;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import javax.enterprise.inject.spi.CDI;
import javax.persistence.EntityManager;

/**
 * Entrepôt dynamique et générique pour n'importe quel type d'entité
 * persistance. Cette classe propose les opérations de base.
 *
 * @author MOSELLE Maxime
 */
public final class DynamicRepository {

    /**
     * Constructeur interne. Pas d'instanciation.
     */
    private DynamicRepository() {
        throw new UnsupportedOperationException("Instance not allowed");
    }

    /**
     * {@link Repository#add}
     *
     * @param <K> Type de l'identifiant unique
     * @param <E> Type de l'entité persistante
     * @param entity Nouvelle entité
     * @return L'instance de l'entité synchronisé
     */
    public static <K extends Serializable, E extends Identifiable<K>> E add(final E entity) {
        var em = CDI.current().select(EntityManager.class).get();
        E attachedEntity;

        if (em.contains(entity)) {
            attachedEntity = em.merge(entity);
        } else {
            em.persist(entity);
            attachedEntity = entity;
        }
        return attachedEntity;
    }

    /**
     * {@link Repository#remove}
     *
     * @param <K> Type de l'identifiant unique
     * @param <E> Type de l'entité persistante
     * @param entity Entité à supprimer
     */
    public static <K extends Serializable, E extends Identifiable<K>> void remove(final E entity) {
        var em = CDI.current().select(EntityManager.class).get();
        var entityClass = entity.getClass();
        var primaryKey = entity.getId();
        var attachedEntity = em.getReference(entityClass, primaryKey);
        em.remove(attachedEntity);
    }

    /**
     * {@link Repository#remove}
     *
     * @param <K> Type de l'identifiant unique
     * @param <E> Type de l'entité persistante
     * @param entityClass Classe de l'entité persistante
     * @param id Identifiant unique
     */
    public static <K extends Serializable, E extends Identifiable<K>> void remove(final Class<E> entityClass, final K id) {
        var em = CDI.current().select(EntityManager.class).get();
        var attachedEntity = em.getReference(entityClass, id);
        em.remove(attachedEntity);
    }

    /**
     * {@link Repository#contains}
     *
     * @param <K> Type de l'identifiant unique
     * @param <E> Type de l'entité persistante
     * @param entity Entité à tester
     * @return La valeur {@code true} si l'entité existe déjà, sinon la valeur
     * {@code false} est retournée
     */
    public static <K extends Serializable, E extends Identifiable<K>> boolean contains(final E entity) {
        var em = CDI.current().select(EntityManager.class).get();
        boolean exist;
        if (em.contains(entity)) {
            exist = true;
        } else {
            var entityClass = (Class<E>) entity.getClass();
            var primaryKey = entity.getId();

            var builder = em.getCriteriaBuilder();
            var query = builder.createQuery(Long.class);
            var root = query.from(entityClass);
            query.select(builder.count(root));

            var primaryKeyClass = primaryKey.getClass();
            var primaryKeyAttribute = em.getMetamodel().entity(entityClass).getId(primaryKeyClass);
            var predicate = builder.equal(root.get(primaryKeyAttribute), primaryKey);

            query.where(predicate);
            exist = em.createQuery(query).getSingleResult() == 1L;
        }
        return exist;
    }

    /**
     * {@link Repository#contains}
     *
     * @param <K> Type de l'identifiant unique
     * @param <E> Type de l'entité persistante
     * @param entityClass Classe de l'entité persistante
     * @param id Identifiant unique
     * @return La valeur {@code true} si l'entité existe déjà, sinon la valeur
     * {@code false} est retournée
     */
    public static <K extends Serializable, E extends Identifiable<K>> boolean contains(final Class<E> entityClass, final K id) {
        var em = CDI.current().select(EntityManager.class).get();
        var attachedEntity = em.find(entityClass, id);
        boolean exist;
        if (Objects.nonNull(attachedEntity)) {
            exist = true;
        } else {
            var builder = em.getCriteriaBuilder();
            var query = builder.createQuery(Long.class);
            var root = query.from(entityClass);
            query.select(builder.count(root));

            var primaryKeyClass = id.getClass();
            var primaryKeyAttribute = em.getMetamodel().entity(entityClass).getId(primaryKeyClass);
            var predicate = builder.equal(root.get(primaryKeyAttribute), id);

            query.where(predicate);
            exist = em.createQuery(query).getSingleResult() == 1L;
        }
        return exist;
    }

    /**
     * {@link Repository#clear}
     *
     * @param <K> Type de l'identifiant unique
     * @param <E> Type de l'entité persistante
     * @param entityClass Classe de l'entité persistante
     */
    public static <K extends Serializable, E extends Identifiable<K>> void clear(final Class<E> entityClass) {
        var em = CDI.current().select(EntityManager.class).get();
        var builder = em.getCriteriaBuilder();
        var query = builder.createCriteriaDelete(entityClass);
        query.from(entityClass);
        em.createQuery(query).executeUpdate();
    }

    /**
     * {@link Repository#findFirst}
     *
     * @param <K> Type de l'identifiant unique
     * @param <E> Type de l'entité persistante
     * @param entityClass Classe de l'entité persistante
     * @param id Identifiant unique
     * @return Une option contenant ou non l'entité correspondante à
     * l'identifiant unique
     */
    public static <K extends Serializable, E extends Identifiable<K>> Optional<E> findFirst(final Class<E> entityClass, final K id) {
        var em = CDI.current().select(EntityManager.class).get();
        var entity = em.find(entityClass, id);
        return Optional.ofNullable(entity);
    }

    /**
     * {@link Repository#values}
     *
     * @param <K> Type de l'identifiant unique
     * @param <E> Type de l'entité persistante
     * @param entityClass Classe de l'entité persistante
     * @return Une liste contenant toutes les occurrences
     */
    public static <K extends Serializable, E extends Identifiable<K>> List<E> values(final Class<E> entityClass) {
        var em = CDI.current().select(EntityManager.class).get();
        var builder = em.getCriteriaBuilder();
        var query = builder.createQuery(entityClass);
        query.from(entityClass);
        return em.createQuery(query).getResultList();
    }

    /**
     * {@link Repository#stream}
     *
     * @param <K> Type de l'identifiant unique
     * @param <E> Type de l'entité persistante
     * @param entityClass Classe de l'entité persistante
     * @return Une séquence contenant toutes les occurrences
     */
    public static <K extends Serializable, E extends Identifiable<K>> Stream<E> stream(final Class<E> entityClass) {
        var em = CDI.current().select(EntityManager.class).get();
        var builder = em.getCriteriaBuilder();
        var query = builder.createQuery(entityClass);
        query.from(entityClass);
        return em.createQuery(query).getResultStream();
    }
}
