package com.github.zelmothedragon.whiteapp.faces;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Classe de configuration du contexte <i>Jakarta Servlet</i>.
 *
 * @author MOSELLE Maxime
 */
@WebListener
public class StartupListener implements ServletContextListener {

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * d'entreprise.
     */
    public StartupListener() {
        // Ne pas appeler explicitement.
    }

    @Override
    public void contextInitialized(final ServletContextEvent context) {

    }

}
