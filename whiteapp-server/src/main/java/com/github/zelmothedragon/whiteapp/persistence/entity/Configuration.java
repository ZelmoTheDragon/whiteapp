package com.github.zelmothedragon.whiteapp.persistence.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
public class Configuration implements Serializable {

    /**
     * Numéro de série.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Clef primaire.
     */
    @NotNull
    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "id", nullable = false, unique = true)
    private ConfigurationKey id;

    /**
     * Valeur. Attention, cette information peut être sensible, ne pas publier
     * cette information dans les journaux applicatif.
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

    /**
     * Convertir la valeur sous forme numérique. Si la valeur ne peut pas être
     * convertie, une exception est levée.
     *
     * @return La valeur numérique
     */
    public Integer asInteger() {
        return Integer.parseInt(value);
    }

    /**
     * Convertir la valeur sous forme numérique. Si la valeur ne peut pas être
     * convertie, une exception est levée.
     *
     * @return La valeur numérique
     */
    public Long asLong() {
        return Long.parseLong(value);
    }

    /**
     * Convertir la valeur sous forme numérique. Si la valeur ne peut pas être
     * convertie, une exception est levée.
     *
     * @return La valeur numérique
     */
    public Float asFloat() {
        return Float.parseFloat(value);
    }

    /**
     * Convertir la valeur sous forme numérique. Si la valeur ne peut pas être
     * convertie, une exception est levée.
     *
     * @return La valeur numérique
     */
    public Double asDouble() {
        return Double.parseDouble(value);
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
                .append("<hidden>")
                .append(", description=")
                .append(description)
                .append('}')
                .toString();
    }

    // ------------------------------
    // Accesseurs & Mutateurs
    // ------------------------------
    public ConfigurationKey getId() {
        return id;
    }

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
