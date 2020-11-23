package com.github.zelmothedragon.whiteapp.common.validation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Fournit des prédicats pour la validation d'objet ainsi que des messages
 * d'erreur prédéfinis.
 *
 * @author MOSELLE Maxime
 */
public final class Constraint {

    /**
     * La valeur ne peut pas être nulle.
     */
    public static final String MESSAGE_NOT_NULL = "can not be null";

    /**
     * La valeur ne peut pas être nulle ou vide.
     */
    public static final String MESSAGE_NOT_EMPTY = "can not be null or empty";

    /**
     * La valeur ne peut pas être une instance vide.
     */
    public static final String MESSAGE_NOT_EMPTY_OBJECT = "can not be an empty object";

    /**
     * La valeur ne peut pas contenir une instance vide.
     */
    public static final String MESSAGE_NOT_CONTAIN_EMPTY_OBJECT = "can not contain an empty object";

    /**
     * La valeur ne peut pas être égal à.
     */
    public static final String MESSAGE_NOT_EQUALS = "can not be equals";

    /**
     * La valeur doit être supérieur à zéro.
     */
    public static final String MESSAGE_GREATER_THAN_ZERO = "must be greater than zero";

    /**
     * La valeur doit être supérieur ou égal à zéro.
     */
    public static final String MESSAGE_GREATER_THAN_OR_EQUAL_ZERO = "must be greater than or equal to zero";

    /**
     * L'adresse de courriel ne respecte pas le format.
     */
    public static final String MESSAGE_INVALID_EMAIL = "invalid email format";

    /**
     * La date n'est pas dans le passé.
     */
    public static final String MESSAGE_DATE_NOT_IN_PAST = "date is not in past";

    /**
     * La date n'est pas dans le futur.
     */
    public static final String MESSAGE_DATE_NOT_IN_FUTURE = "date is not in future";

    /**
     * Expression régulière pour une adresse de courriel.
     */
    private static final Pattern EMAIL_REGEX = Pattern.compile("^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$", Pattern.CASE_INSENSITIVE);

    /**
     * Constructeur interne. Pas d'instanciation.
     */
    private Constraint() {
        throw new UnsupportedOperationException("No instances for you!");
    }

    /**
     * Vérifier qu'un nombre soit supérieur à zéro.
     *
     * @param number Un nombre quelconque
     * @return La valeur <code>true</code> si le nombre est supérieur à zéro,
     * sinon <code>false</code>
     */
    public static boolean greaterThanZero(final Integer number) {
        return number > 0;
    }

    /**
     * Vérifier qu'un nombre soit supérieur à zéro.
     *
     * @param number Un nombre quelconque
     * @return La valeur <code>true</code> si le nombre est supérieur à zéro,
     * sinon <code>false</code>
     */
    public static boolean greaterThanZero(final BigDecimal number) {
        return number.signum() > 0;
    }

    /**
     * Vérifier qu'un nombre soit supérieur ou égal à zéro.
     *
     * @param number Un nombre quelconque
     * @return La valeur <code>true</code> si le nombre est supérieur ou égal à
     * zéro, sinon <code>false</code>
     */
    public static boolean greaterThanOrEqualZero(final Integer number) {
        return number >= 0;
    }

    /**
     * Vérifier qu'un nombre soit supérieur ou égal à zéro.
     *
     * @param number Un nombre quelconque
     * @return La valeur <code>true</code> si le nombre est supérieur ou égal à
     * zéro, sinon <code>false</code>
     */
    public static boolean greaterThanOrEqualZero(final BigDecimal number) {
        return number.signum() >= 0;
    }

    /**
     * Vérifier qu'un texte ne soit pas nul ou vide.
     *
     * @param text Un texte quelconque.
     * @return La valeur <code>true</code> si le texte n'est pas nul ou vide,
     * sinon <code>false</code>
     */
    public static boolean notEmpty(final String text) {
        return Objects.nonNull(text) && !text.trim().isEmpty();
    }

    /**
     * Vérifier qu'une collection ne soit pas vide.
     *
     * @param collection Une collection quelconque
     * @return La valeur <code>true</code> si la collection n'est pas vide,
     * sinon <code>false</code>
     */
    public static boolean notEmpty(final Collection<?> collection) {
        return Objects.nonNull(collection) && !collection.isEmpty();
    }

    /**
     * Vérifier q'une collection ne contient pas un objet spécifique.
     *
     * @param collection Une collection quelconque
     * @param obj Un objet spécifique
     * @return La valeur <code>true</code> si la collection ne contient pas un
     * objet spécifique, sinon <code>false</code>
     */
    public static boolean notContains(final Collection<?> collection, final Object obj) {
        return !collection.contains(obj);
    }

    /**
     * Vérifier que deux objets ne sont pas égaux.
     *
     * @param a Premier objet quelconque
     * @param b Second objet quelconque
     * @return La valeur <code>true</code> si les deux objets ne sont pas égaux,
     * sinon <code>false</code>
     */
    public static boolean notEquals(final Object a, final Object b) {
        return !Objects.equals(a, b);
    }

    /**
     * Vérifier qu'une date est dans le passé.
     *
     * @param date Une date quelconque
     * @return La valeur <code>true</code> si la date est dans le passé, sinon
     * <code>false</code>
     */
    public static boolean isInPast(final LocalDateTime date) {
        return LocalDateTime.now().isAfter(date);
    }

    /**
     * Vérifier qu'une date est dans le futur.
     *
     * @param date Une date quelconque
     * @return La valeur <code>true</code> si la date est dans le futur, sinon
     * <code>false</code>
     */
    public static boolean isInFuture(final LocalDateTime date) {
        return LocalDateTime.now().isBefore(date);
    }

    /**
     * Vérifier q'un texte respecte le format d'une adresse de courriel.
     *
     * @param text Un texte quelconque.
     * @return La valeur <code>true</code> si le texte respecte le format d'une
     * adresse de courriel, sinon <code>false</code>
     */
    public static boolean isEmailValid(final String text) {
        return EMAIL_REGEX.matcher(text).matches();
    }
}
