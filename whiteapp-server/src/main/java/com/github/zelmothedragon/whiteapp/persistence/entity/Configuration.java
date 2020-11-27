package com.github.zelmothedragon.whiteapp.persistence.entity;

import com.github.zelmothedragon.whiteapp.enterprise.persistence.Identifiable;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * Configuration de l'application. Désigne une propriété ou un élément de
 * configuration du fonctionnement de l'application. L'utilité est comparable
 * aux ressources <i>JNDI</i> de Java, à la différence que la donnée est stockée
 * dans un entrepôt (tel qu'une base de données).
 *
 * @author MOSELLE Maxime
 */
@Entity
@Table(name = "configuration")
public class Configuration implements Identifiable<ConfigurationKey>, Serializable {

    /**
     * Numéro de série.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Clef primaire.
     */
    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "id", nullable = false, unique = true)
    private ConfigurationKey id;

    /**
     * Valeur.
     */
    @NotBlank
    @Column(name = "value", nullable = false)
    private String value;

    /**
     * Description.
     */
    @Column(name = "description")
    private String description;

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * de <i>Jakarta EE</i>.
     */
    public Configuration() {
        // Ne pas appeler explicitement.
    }

    @Override
    public boolean equals(Object obj) {
        final boolean eq;
        if (this == obj) {
            eq = true;
        } else if (Objects.isNull(obj) || !Objects.equals(getClass(), obj.getClass())) {
            eq = false;
        } else {
            var other = (Configuration) obj;
            eq = Objects.equals(this.id, other.id)
                    && Objects.equals(this.value, other.value)
                    && Objects.equals(this.description, other.description);
        }
        return eq;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, description);
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Configuration{id=")
                .append(id)
                .append(", value=")
                .append(value)
                .append(", description=")
                .append(description)
                .append('}')
                .toString();
    }

    @Override
    public boolean synchronizeId() {
        return false;
    }

    // ------------------------------
    // Accesseurs & Mutateurs
    // ------------------------------
    @Override
    public ConfigurationKey getId() {
        return id;
    }

    @Override
    public void setId(ConfigurationKey id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
