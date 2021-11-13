package com.github.zelmothedragon.whiteapp.persistence.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Une organisation, regroupe un ensemble d'agent.
 *
 * @author MOSELLE Maxime
 */
@Entity
@Table(name = "organization")
@Access(AccessType.FIELD)
public class Organization extends AbstractEntity {

    /**
     * Numéro de série.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Code unique. Caractères alphabétiques majuscules et numériques.
     */
    @Pattern(regexp = "[A-Z0-9]*")
    @Size(min = 1, max = 255)
    @NotBlank
    @Column(name = "code", nullable = false, unique = true)
    @Basic(fetch = FetchType.LAZY)
    private String code;

    /**
     * Nom commun.
     */
    @Size(min = 1, max = 255)
    @Column(name = "name", nullable = false)
    @Basic(fetch = FetchType.LAZY)
    private String name;

    /**
     * Description.
     */
    @Size(min = 1, max = 255)
    @Column(name = "description", nullable = false)
    @Basic(fetch = FetchType.LAZY)
    private String description;

    /**
     * Organisation parent.
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "organization_parent_id")
    private Organization parent;

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * d'entreprise.
     */
    public Organization() {
        // Ne pas appeler explicitement.
    }

    // ------------------------------
    // Accesseurs & Mutateurs
    // ------------------------------
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Organization getParent() {
        return parent;
    }

    public void setParent(Organization parent) {
        this.parent = parent;
    }

}
