package com.github.zelmothedragon.whiteapp.ejb;

import com.github.zelmothedragon.whiteapp.enterprise.persistence.Identifiable;
import com.github.zelmothedragon.whiteapp.enterprise.persistence.Pagination;
import com.github.zelmothedragon.whiteapp.persistence.repository.DynamicRepository;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 * Implémentation <i>EJB</i> des services métiers communs.
 *
 * @author MOSELLE Maxime
 */
@Local(DynamicService.class)
@Stateless
public class DynamicServiceSession implements DynamicService {

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
        return DynamicRepository.add(entity);
    }

    @Override
    public <K extends Serializable, E extends Identifiable<K>> void remove(final E entity) {
        DynamicRepository.remove(entity);
    }

    @Override
    public <K extends Serializable, E extends Identifiable<K>> void remove(
            final Class<E> entityClass,
            final K id) {

        DynamicRepository.remove(entityClass, id);
    }

    @Override
    public <K extends Serializable, E extends Identifiable<K>> boolean contains(final E entity) {
        return DynamicRepository.contains(entity);
    }

    @Override
    public <K extends Serializable, E extends Identifiable<K>> boolean contains(
            final Class<E> entityClass,
            final K id) {

        return DynamicRepository.contains(entityClass, id);
    }

    @Override
    public <K extends Serializable, E extends Identifiable<K>> void clear(final Class<E> entityClass) {
        DynamicRepository.clear(entityClass);
    }

    @Override
    public <K extends Serializable, E extends Identifiable<K>> Optional<E> find(
            final Class<E> entityClass,
            final K id) {

        return DynamicRepository.findFirst(entityClass, id);
    }

    @Override
    public <K extends Serializable, E extends Identifiable<K>> List<E> values(final Class<E> entityClass) {
        return DynamicRepository.values(entityClass);
    }

    @Override
    public <K extends Serializable, E extends Identifiable<K>> List<E> filter(
            final Class<E> entityClass,
            final Pagination pagination) {

        return DynamicRepository.filter(entityClass, pagination);
    }

    @Override
    public <K extends Serializable, E extends Identifiable<K>> List<E> filter(
            final Class<E> entityClass,
            final String keyword) {

        return DynamicRepository.filter(entityClass, keyword);
    }

}
