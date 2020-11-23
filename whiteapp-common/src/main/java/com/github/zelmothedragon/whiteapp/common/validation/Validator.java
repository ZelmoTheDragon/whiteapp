package com.github.zelmothedragon.whiteapp.common.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Génère une validation d'objet.
 *
 * @author MOSELLE Maxime
 * @param <T> Type de l'objet à valider
 */
public final class Validator<T> {

    /**
     * Instance à valider.
     */
    private final T element;

    /**
     * Exceptions.
     */
    private final List<ValidationException> exceptions;

    /**
     * Constructeur. Ce constructeur est privé, afin de faciliter l'emploi de
     * cette classe, utiliser la méthode <code>of</code> et chaîner les appels
     * de méthodes.
     *
     * @param element Instance à valider
     */
    private Validator(final T element) {
        this.element = element;
        this.exceptions = new ArrayList<>();
    }

    /**
     * Point d'entrée de cette classe pour commencer à construire le calcul de
     * la validation de l'objet.
     *
     * @param <E> Type de l'objet à valider
     * @param element Instance à valider
     * @return Une instance de cette classe afin de chaîner les appels de
     * méthodes
     */
    public static <E> Validator<E> of(final E element) {
        return new Validator<>(Objects.requireNonNull(element));
    }

    /**
     * Ajouter un prédicat de validation.
     *
     * @param validation Un prédicat
     * @param message Un message en cas d'échec de la validation
     * @return L'instance de cette classe afin de chaîner les appels de méthodes
     */
    public Validator<T> validate(
            final Predicate<T> validation,
            final String message) {

        if (!validation.test(element)) {
            exceptions.add(new ValidationException(message));
        }
        return this;
    }

    /**
     * Ajouter un prédicat de validation.
     *
     * @param <U> Type de retour de l'accesseur
     * @param getter Un accesseur
     * @param validation Un prédicat
     * @param message Un message en cas d'échec de la validation
     * @return L'instance de cette classe afin de chaîner les appels de méthodes
     */
    public <U> Validator<T> validate(
            final Function<T, U> getter,
            final Predicate<U> validation,
            final String message) {

        return validate(getter.andThen(validation::test)::apply, message);
    }

    /**
     * Opération de terminaison, résout le calcul de la validation.
     *
     * @return L'instance de l'objet a valider
     */
    public T get() {
        if (!exceptions.isEmpty()) {
            ValidationException ex = new ValidationException(element.getClass());
            exceptions.forEach(ex::addSuppressed);
            throw ex;
        }
        return element;
    }

}
