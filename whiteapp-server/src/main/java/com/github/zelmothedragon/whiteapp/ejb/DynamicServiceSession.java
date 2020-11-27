package com.github.zelmothedragon.whiteapp.ejb;

import com.github.zelmothedragon.whiteapp.enterprise.persistence.Identifiable;
import com.github.zelmothedragon.whiteapp.enterprise.persistence.Pagination;
import com.github.zelmothedragon.whiteapp.persistence.repository.DynamicRepository;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Implémentation <i>EJB</i> des services métiers communs.
 *
 * @author MOSELLE Maxime
 */
@Local(DynamicService.class)
@Stateless
public class DynamicServiceSession implements DynamicService {

    /**
     * Entrepôt dynamique d'entité.
     */
    @Inject
    private DynamicRepository repository;

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * de <i>Jakarta EE</i>.
     */
    public DynamicServiceSession() {
        // Ne pas appeler explicitement.
    }

    @Override
    public <K extends Serializable, E extends Identifiable<K>> E save(final E entity) {
        entity.synchronizeId();
        return repository.add(entity);
    }

    @Override
    public <K extends Serializable, E extends Identifiable<K>> void remove(final E entity) {
        repository.remove(entity);
    }

    @Override
    public <K extends Serializable, E extends Identifiable<K>> void remove(
            final Class<E> entityClass,
            final K id) {

        repository.remove(entityClass, id);
    }

    @Override
    public <K extends Serializable, E extends Identifiable<K>> boolean contains(final E entity) {
        return repository.contains(entity);
    }

    @Override
    public <K extends Serializable, E extends Identifiable<K>> boolean contains(
            final Class<E> entityClass,
            final K id) {

        return repository.contains(entityClass, id);
    }

    @Override
    public <K extends Serializable, E extends Identifiable<K>> void clear(final Class<E> entityClass) {
        repository.clear(entityClass);
    }

    @Override
    public <K extends Serializable, E extends Identifiable<K>> Optional<E> find(
            final Class<E> entityClass,
            final K id) {

        return repository.findFirst(entityClass, id);
    }

    @Override
    public <K extends Serializable, E extends Identifiable<K>> List<E> values(final Class<E> entityClass) {
        return repository.values(entityClass);
    }

    @Override
    public <K extends Serializable, E extends Identifiable<K>> List<E> filter(
            final Class<E> entityClass,
            final Pagination pagination) {

        return repository.filter(entityClass, pagination);
    }

    @Override
    public <K extends Serializable, E extends Identifiable<K>> List<E> filter(
            final Class<E> entityClass,
            final String keyword) {

        return repository.filter(entityClass, keyword);
    }

}
