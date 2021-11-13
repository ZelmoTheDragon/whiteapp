package com.github.zelmothedragon.whiteapp.dynamic.persistence.dao;

import com.github.zelmothedragon.whiteapp.dynamic.persistence.entity.Identifiable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Selection;

@ApplicationScoped
public class GenericDAO {

    @Inject
    private EntityManager em;

    public GenericDAO() {
        // Ne pas appeler explicitement
    }

    public <K extends Serializable, E extends Identifiable<K>> void add(final E entity) {
        em.persist(entity);
    }

    public <K extends Serializable, E extends Identifiable<K>> void remove(final E entity) {
        var attachedEntity = getReference(entity);
        em.remove(attachedEntity);
    }

    public <K extends Serializable, E extends Identifiable<K>> void clear(final Class<E> entityClass) {
        var builder = em.getCriteriaBuilder();
        var query = builder.createCriteriaDelete(entityClass);
        query.from(entityClass);
        em.createQuery(query).executeUpdate();
    }

    public <K extends Serializable, E extends Identifiable<K>> boolean contains(final E entity) {
        return em.contains(entity);
    }

    public <K extends Serializable, E extends Identifiable<K>> long size(final Class<E> entityClass) {
        var builder = em.getCriteriaBuilder();
        var query = builder.createQuery(Long.class);
        var root = query.from(entityClass);
        query.select(builder.count(root));
        return em.createQuery(query).getSingleResult();
    }

    public <K extends Serializable, E extends Identifiable<K>> Optional<E> find(
            final Class<E> entityClass, final K id) {

        return Optional.ofNullable(em.find(entityClass, id));
    }

    public <K extends Serializable, E extends Identifiable<K>> List<E> find(
            final Class<E> entityClass,
            final CriteriaPredicate<K, E> filter) {

        var builder = em.getCriteriaBuilder();
        var query = builder.createQuery(entityClass);
        var root = query.from(entityClass);
        var predicate = filter.apply(builder, query, root);
        query.where(predicate);
        return em.createQuery(query).getResultList();
    }

    public <K extends Serializable, E extends Identifiable<K>> List<E> find(
            final Class<E> entityClass,
            final PaginationPredicate filter) {

        var queryCount = this.createQuery(entityClass, filter, Long.class, QuerySelection::count);
        var queryFind = this.createQuery(entityClass, filter, entityClass, QuerySelection::select);

        return queryFind
                .setFirstResult(filter.getFirstResult())
                .setMaxResults(filter.getMaxResult())
                .getResultList();
    }

    private <T, K extends Serializable, E extends Identifiable<K>> TypedQuery<T> createQuery(
            final Class<E> entityClass,
            final PaginationPredicate filter,
            final Class<T> targetClass,
            final QuerySelection<T, K, E> selection) {

        var builder = em.getCriteriaBuilder();
        var query = builder.createQuery(targetClass);
        var root = query.from(entityClass);

        List<Predicate> restrictions = new ArrayList<>(filter.getPredicates().size());

        for (var q : filter.getPredicates()) {
            var column = root.get(q.getAttributeName());
            var value = q.getValue();
            var predicate = q.getOperator().getPredicate().apply(builder, column, value);
            restrictions.add(predicate);
        }

        query.select(selection.apply(builder, query, root));
        query.where(restrictions.toArray(new Predicate[restrictions.size()]));
        return em.createQuery(query);
    }

    private Object getReference(final Object entity) {
        var entityClass = entity.getClass();
        return em.getReference(entityClass, entity);
    }

    private Object getIdentifier(final Object entity) {
        var puu = em.getEntityManagerFactory().getPersistenceUnitUtil();
        return puu.getIdentifier(entity);
    }

}
