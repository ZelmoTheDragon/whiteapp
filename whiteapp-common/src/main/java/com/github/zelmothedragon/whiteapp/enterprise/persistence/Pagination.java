package com.github.zelmothedragon.whiteapp.enterprise.persistence;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Modèle de données pour la recherche filtrée et paginée.
 *
 * @author MOSELLE Maxime
 */
public class Pagination implements Serializable {

    /**
     * Numéro de série.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Nombres d'occurrence par page.
     */
    private static final int PAGINATION_LIMIT = 100;

    /**
     * Index dans la pagination.
     */
    public static final int DEFAULT_INDEX = 0;

    /**
     * Mot-clef pour la recherche.
     */
    private final String keyword;

    /**
     * Pagination.
     */
    private final int pageSize;

    /**
     * Index.
     */
    private final int pageNumber;

    /**
     * Champs pour le tri.
     */
    private final List<String> orderBy;

    /**
     * Indique si le tri doit être ascendant.
     */
    private final boolean ascending;

    /**
     * Indique que la requête n'a pas de limite d'occurrence.
     */
    private final boolean noLimit;

    /**
     * Construire un modèle de données pour la pagination.
     *
     * @param keyword Mot-clef pour la recherche
     * @param pageNumber Numéro de page
     * @param pageSize Nombre d'élément par page
     * @param orderBy
     * @param ascending
     * @param noLimit
     */
    public Pagination(
            final String keyword,
            final int pageNumber,
            final int pageSize,
            final List<String> orderBy,
            final boolean ascending,
            final boolean noLimit) {

        this.keyword = keyword;
        this.pageNumber = Math.max(pageNumber, DEFAULT_INDEX);
        this.pageSize = Math.min(Math.abs(pageSize), PAGINATION_LIMIT);
        this.orderBy = orderBy;
        this.ascending = ascending;
        this.noLimit = noLimit;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Pagination{keyword=")
                .append(keyword)
                .append(", pageSize=")
                .append(pageSize)
                .append(", pageNumber=")
                .append(pageNumber)
                .append(", orderBy=")
                .append(orderBy)
                .append(", ascending=")
                .append(ascending)
                .append(", noLimit=")
                .append(noLimit)
                .append('}')
                .toString();
    }

    // ------------------------------
    // Accesseurs
    // ------------------------------
    public String getKeyword() {
        return keyword;
    }

    public int getIndex() {
        return pageNumber * pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public List<String> getOrderBy() {
        return orderBy;
    }

    public boolean isOrdered() {
        return Objects.nonNull(orderBy) && !orderBy.isEmpty();
    }

    public boolean isAscending() {
        return ascending;
    }

    public boolean isNoLimit() {
        return noLimit;
    }

}
