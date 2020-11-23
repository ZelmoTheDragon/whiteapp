package com.github.zelmothedragon.whiteapp.faces;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;

/**
 * Configuration de <b>Jakarta Faces</b>.
 *
 * @author MOSELLE Maxime
 */
@ApplicationScoped
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
public class FacesConfiguration {

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * de Jakarta EE.
     */
    public FacesConfiguration() {
        // Ne pas appeler explicitement.
    }

}
