package com.github.zelmothedragon.whiteapp.common.lang;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Génère le calcul de la méthode <code>equals</code> des objets en utilisant
 * une approche fonctionnelle. Lors de la redéfinition de la méthode
 * <code>equals</code>, il est également nécessaire de redéfinir la méthode
 * <code>hashCode</code>. La classe <code>java.util.Objects</code> propose déjà
 * une manière simple pour cette méthode.
 *
 * @param <T> Type quelconque
 * @author MOSELLE Maxime
 */
public final class Equal<T> {

    /**
     * Liste de fonctions utilisées pour la comparaison.
     */
    private final List<Function<T, ?>> methods;

    /**
     * Constructeur. Ce constructeur est privé, afin de faciliter l'emploi de
     * cette classe, utiliser la méthode <code>with</code> et chaîner les appels
     * de méthodes.
     *
     * @param method Une méthode de l'instance initiale, en général des
     * accesseurs sont employés
     */
    private Equal(final Function<T, ?> method) {
        this.methods = new ArrayList<>();
        this.methods.add(method);
    }

    /**
     * Point d'entrée de cette classe pour commencer à construire le calcul de
     * l'égalité entre deux objets.
     *
     * @param <T> Type quelconque
     * @param method Une méthode de l'instance initiale, en général des
     * accesseurs sont employés
     * @return Une instance de cette classe afin de chaîner les appels de
     * méthodes
     */
    public static <T> Equal<T> with(final Function<T, ?> method) {
        return new Equal<>(method);
    }

    /**
     * Ajouter une méthode supplémentaire pour le calcul de l'égalité. L'appel
     * de cette méthode peut être chaîner autant de fois que nécessaire.
     *
     * @param method Une méthode de l'instance initiale, en général des
     * accesseurs sont employés
     * @return Une instance de cette classe afin de chaîner les appels de
     * méthodes
     */
    public Equal<T> thenWith(final Function<T, ?> method) {
        methods.add(method);
        return this;
    }

    /**
     * Opération de terminaison, résout le calcul de l'égalité.
     *
     * @param me Instance courante
     * @param target Enstance cible
     * @return La valeur <code>true</code> si les deux objets sont identiques,
     * sinon la valeur <code>false</code>
     */
    public boolean apply(final T me, final Object target) {
        boolean eq;
        if (me == target) {
            eq = true;
        } else if (!Objects.equals(me.getClass(), target.getClass())) {
            eq = false;
        } else {
            T other = (T) target;
            eq = methods
                    .stream()
                    .allMatch(p -> Objects.equals(p.apply(me), p.apply(other)));
        }
        return eq;
    }

}
