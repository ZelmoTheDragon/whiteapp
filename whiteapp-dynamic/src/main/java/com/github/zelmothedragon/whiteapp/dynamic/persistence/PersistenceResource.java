package com.github.zelmothedragon.whiteapp.dynamic.persistence;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
class PersistenceResource {

    @Produces
    @PersistenceContext
    private EntityManager em;

    PersistenceResource() {
        // Ne pas appeler explicitement.
    }

}
