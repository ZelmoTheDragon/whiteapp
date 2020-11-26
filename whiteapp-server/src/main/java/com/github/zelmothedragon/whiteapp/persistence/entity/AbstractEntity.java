package com.github.zelmothedragon.whiteapp.persistence.entity;

import com.github.zelmothedragon.whiteapp.enterprise.persistence.Identifiable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

/**
 * Classe mère pour les entités persistantes.
 *
 * @author MOSELLE Maxime
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractEntity implements Identifiable<UUID>, Serializable {

    /**
     * Numéro de série.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Clef primaire.
     */
    @NotNull
    @Id
    @Column(name = "id", nullable = false, unique = true)
    protected UUID id;

    /**
     * Version de l'objet. Ce champ est alimenté automatiquement par <i>JPA</i>
     * au moment de la persistance. Il permet d'éviter les problèmes de mise à
     * jour en cas d'accès concurrent.
     */
    @NotNull
    @Version
    @Column(name = "version", nullable = false)
    protected Long version;

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * de <i>Jakarta EE</i>.
     */
    protected AbstractEntity() {
        this.id = UUID.randomUUID();
        this.version = 0L;
        // Ne pas appeler explicitement.
    }

    @Override
    public boolean equals(Object obj) {
        // Calcul de l'égalité de l'objet.
        // Basé sur la clef primaire, cet méthode est suffisante 
        // pour la plupart des entités.
        // Comme l'attribut 'id' est généré à l'instanciation de l'objet
        // le calcul de cette méthode renverra toujours un résultat cohérent
        // car jamais 'id' ne pourra prendre la valeur 'null'.
        final boolean eq;
        if (this == obj) {
            eq = true;
        } else if (Objects.isNull(obj) || !Objects.equals(getClass(), obj.getClass())) {
            eq = false;
        } else {
            var other = (AbstractEntity) obj;
            eq = Objects.equals(this.id, other.id)
                    && Objects.equals(this.version, other.version);
        }
        return eq;
    }

    @Override
    public int hashCode() {
        // Java propose une méthode native pour le calcul du code de hachage.
        // Pourquoi faire autrement ?
        return Objects.hash(id, version);
    }

    @Override
    public String toString() {
        return String.format(
                "%s{id=%s, version=%s}",
                getClass().getName(),
                id,
                version
        );
    }

    @Override
    public boolean synchronizeId() {
        if (Objects.isNull(id)) {
            id = UUID.randomUUID();
        }
        if (Objects.isNull(version)) {
            version = 0L;
        }
        return true;
    }

    // ------------------------------
    // Accesseurs & Mutateurs
    // ------------------------------
    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}
