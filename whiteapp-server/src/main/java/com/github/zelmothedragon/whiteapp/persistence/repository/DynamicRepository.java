package com.github.zelmothedragon.whiteapp.persistence.repository;

import com.github.zelmothedragon.whiteapp.enterprise.persistence.Identifiable;
import com.github.zelmothedragon.whiteapp.enterprise.persistence.Pagination;
import com.github.zelmothedragon.whiteapp.enterprise.persistence.Repository;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.Attribute;

/**
 * Entrepôt dynamique et générique pour n'importe quel type d'entité
 * persistance. Cette classe propose les opérations de base.
 *
 * @author MOSELLE Maxime
 */
@ApplicationScoped
public class DynamicRepository {

    /**
     * Gestionnaire d'entité.
     */
    @Inject
    private EntityManager em;

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * de <i>Jakarta EE</i>.
     */
    public DynamicRepository() {
        // Ne pas appeler explicitement.
    }

    /**
     * Récupérer l'instance unique de travail.
     *
     * @return L'instance unique
     */
    public static DynamicRepository getInstance() {
        return CDI.current().select(DynamicRepository.class).get();
    }

    /**
     * {@link Repository#add}
     *
     * @param <K> Type de l'identifiant unique
     * @param <E> Type de l'entité persistante
     * @param entity Nouvelle entité
     * @return L'instance de l'entité synchronisé
     */
    public <K extends Serializable, E extends Identifiable<K>> E add(final E entity) {
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
    public <K extends Serializable, E extends Identifiable<K>> void remove(final E entity) {
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
    public <K extends Serializable, E extends Identifiable<K>> void remove(final Class<E> entityClass, final K id) {
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
    public <K extends Serializable, E extends Identifiable<K>> boolean contains(final E entity) {
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
    public <K extends Serializable, E extends Identifiable<K>> boolean contains(final Class<E> entityClass, final K id) {
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
    public <K extends Serializable, E extends Identifiable<K>> void clear(final Class<E> entityClass) {
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
    public <K extends Serializable, E extends Identifiable<K>> Optional<E> findFirst(final Class<E> entityClass, final K id) {
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
    public <K extends Serializable, E extends Identifiable<K>> List<E> values(final Class<E> entityClass) {
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
    public <K extends Serializable, E extends Identifiable<K>> Stream<E> stream(final Class<E> entityClass) {
        var builder = em.getCriteriaBuilder();
        var query = builder.createQuery(entityClass);
        query.from(entityClass);
        return em.createQuery(query).getResultStream();
    }

    /**
     * @see Repository#filter
     * @param <K> Type de l'identifiant unique
     * @param <E> Type de l'entité persistante
     * @param entityClass Classe de l'entité persistante
     * @param pagination Critère de filtrage pour la pagination
     * @return Une liste d'entités persistantes
     */
    public <K extends Serializable, E extends Identifiable<K>> List<E> filter(
            final Class<E> entityClass,
            final Pagination pagination) {

        var builder = em.getCriteriaBuilder();
        var query = builder.createQuery(entityClass);
        var root = query.from(entityClass);
        query.distinct(true);

        if (Objects.nonNull(pagination.getKeyword())) {
            String search = String.format("%%%s%%", pagination.getKeyword().trim().toLowerCase());

            var restrictions = em
                    .getMetamodel()
                    .entity(entityClass)
                    .getAttributes()
                    .stream()
                    .filter(a -> Objects.equals(a.getJavaType(), String.class))
                    .map(a -> builder.like(builder.lower(root.get(a.getName())), search))
                    .collect(Collectors.toList());

            query.where(builder.or(restrictions.toArray(new Predicate[restrictions.size()])));
        }

        if (pagination.isOrdered()) {
            var orderByAttributes = em
                    .getMetamodel()
                    .entity(entityClass)
                    .getAttributes()
                    .stream()
                    .map(Attribute::getName)
                    .filter(pagination.getOrderBy()::contains)
                    .collect(Collectors.toList());

            List<Order> orders;
            if (orderByAttributes.isEmpty() && pagination.isAscending()) {
                orders = List.of(builder.asc(root));
            } else if (orderByAttributes.isEmpty() && !pagination.isAscending()) {
                orders = List.of(builder.desc(root));
            } else if (pagination.isAscending()) {
                orders = orderByAttributes
                        .stream()
                        .map(a -> builder.asc(root.get(a)))
                        .collect(Collectors.toList());
            } else {
                orders = orderByAttributes
                        .stream()
                        .map(a -> builder.desc(root.get(a)))
                        .collect(Collectors.toList());
            }
            query.orderBy(orders);
        }

        List<E> result;
        if (pagination.isNoLimit()) {
            result = em
                    .createQuery(query)
                    .getResultList();
        } else {
            result = em
                    .createQuery(query)
                    .setFirstResult(pagination.getIndex())
                    .setMaxResults(pagination.getPageSize())
                    .getResultList();
        }
        return result;
    }

    /**
     * @see Repository#filter
     * @param <K> Type de l'identifiant unique
     * @param <E> Type de l'entité persistante
     * @param entityClass Classe de l'entité persistante
     * @param keyword Mot clef pour la recherche
     * @return Une liste d'entités persistantes
     */
    public <K extends Serializable, E extends Identifiable<K>> List<E> filter(
            final Class<E> entityClass,
            final String keyword) {

        var pagination = new Pagination(
                keyword,
                0,
                -1,
                List.of(),
                true,
                false
        );

        return filter(entityClass, pagination);
    }
}
