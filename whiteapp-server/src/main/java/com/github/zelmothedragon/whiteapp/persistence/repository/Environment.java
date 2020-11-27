package com.github.zelmothedragon.whiteapp.persistence.repository;

import com.github.zelmothedragon.whiteapp.persistence.entity.Configuration;
import com.github.zelmothedragon.whiteapp.persistence.entity.ConfigurationKey;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 * Entrepôt des variables d'environnements de l'application.
 *
 * @author MOSELLE Maxime
 */
@ApplicationScoped
public class Environment {

    /**
     * Cache mémoire des configurations disponible pour l'application.
     */
    private final Map<ConfigurationKey, Configuration> cache;

    /**
     * Gestionnaire d'entité.
     */
    @Inject
    private EntityManager em;

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * de <i>Jakarta EE</i>.
     */
    public Environment() {
        this.cache = new HashMap<>();
        // Ne pas appeler explicitement.
    }

    /**
     * Récupérer l'instance unique de travail.
     *
     * @return L'instance unique
     */
    public static Environment getInstance() {
        return CDI.current().select(Environment.class).get();
    }

    /**
     * Charger l'ensemble des configurations en mémoire.
     */
    @PostConstruct
    public void load() {
        this.cache.clear();

        var builder = em.getCriteriaBuilder();
        var query = builder.createQuery(Configuration.class);
        query.from(Configuration.class);
        em
                .createQuery(query)
                .getResultList()
                .forEach(c -> cache.put(c.getId(), c));

    }

    /**
     * Ajouter ou mettre à jour une configuration.
     *
     * @param configuration Nouvelle configuration
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void put(final Configuration configuration) {
        Configuration updatedConfiguration;
        if (em.contains(configuration)) {
            updatedConfiguration = em.merge(configuration);
        } else {
            em.persist(configuration);
            updatedConfiguration = configuration;
        }
        this.cache.put(updatedConfiguration.getId(), updatedConfiguration);
    }

    /**
     * Supprimer une configuration.
     *
     * @param key Identifiant unique de la configuration
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void remove(final ConfigurationKey key) {
        var configurationReference = em.getReference(Configuration.class, key);
        em.remove(configurationReference);
        this.cache.remove(key);
    }

    /**
     * Obtenir une configuration en fonction de son identifiant. L'élément est
     * récupéré depuis le cache en mémoire.
     *
     * @param key Identifiant unique de la configuration
     * @return La configuration correspondante
     */
    public Configuration get(final ConfigurationKey key) {
        return this.cache.get(key);
    }

    /**
     * Obtenir toutes les configurations. Les éléments sont récupérés depuis le
     * cache en mémoire.
     *
     * @return Une collection de toutes les configurations existantes
     */
    public Collection<Configuration> values() {
        return this.cache.values();
    }

}
