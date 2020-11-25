package com.github.zelmothedragon.whiteapp.common.lang;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Génère une chaîne de caractères représentant les données d'un objet en
 * utilisant une approche fonctionnelle. Utile pour redéfinir la méthode
 * <code>toString</code>.
 *
 *
 * @param <T> Type quelconque
 * @author MOSELLE Maxime
 */
public final class ToString<T> {

    /**
     * Regroupe toutes les méthodes permettant la génération d'une chaîne de
     * caractères représentant les données associées à un nom. Ce nom est
     * généralement le nom de l'attribut. La méthode, quant à elle est
     * généralement un accesseur.
     */
    private final Map<String, Function<T, ?>> methods;

    /**
     * Constructeur. Ce constructeur est privé, afin de faciliter l'emploi de
     * cette classe, utiliser la méthode <code>with</code> et chaîner les appels
     * de méthodes.
     *
     * @param name Nom de l'attribut
     * @param method Méthode permettant de renvoyer la valeur de l'attribut,
     * généralement un accesseur
     */
    private ToString(final String name, final Function<T, ?> method) {
        this.methods = new HashMap<>();
        this.methods.put(name, method);
    }

    /**
     * Ajouter une propriété pour générer la représentation textuelle de
     * l'objet.
     *
     * @param <T> Type quelconque
     * @param name Nom de l'attribut
     * @param method Méthode permettant de renvoyer la valeur de l'attribut,
     * généralement un accesseur
     * @return Une instance de cette classe afin de chaîner les appels de
     * méthodes
     */
    public static <T> ToString<T> with(final String name, final Function<T, ?> method) {
        return new ToString<>(name, method);
    }

    /**
     * Ajouter une propriété supplémentaire pour générer la représentation
     * textuelle de l'objet.
     *
     * @param name Nom de l'attribut
     * @param method Méthode permettant de renvoyer la valeur de l'attribut,
     * généralement un accesseur
     * @return Une instance de cette classe afin de chaîner les appels de
     * méthodes
     */
    public ToString<T> thenWith(final String name, final Function<T, ?> method) {
        this.methods.put(name, method);
        return this;
    }

    /**
     * Opération de terminaison, génère une chaîne de caractères représentant
     * les données d'un objet.
     *
     * @param target Instance cible pour générer une chaîne de caractères
     * représentant les données.
     * @return La représentation textuelle
     */
    public String apply(final T target) {
        var sb = new StringBuilder();
        sb
                .append(target.getClass().getSimpleName())
                .append("{");

        methods
                .entrySet()
                .stream()
                .map(e -> append(e.getKey(), e.getValue(), target))
                .reduce(sb, StringBuilder::append)
                .deleteCharAt(sb.lastIndexOf(","))
                .append("}");

        return sb.toString();
    }

    /**
     *
     * @param <T> Type quelconque
     * @param name Nom de l'attribut
     * @param method Méthode permettant de renvoyer la valeur de l'attribut,
     * généralement un accesseur
     * @param target Instance de travail
     * @return Une chaîne de caractères sous forme de clef/valeur d'un attribut
     * de l'objet de travail
     */
    private static <T> StringBuilder append(
            final String name,
            final Function<T, ?> method,
            final T target) {

        return new StringBuilder()
                .append(name)
                .append("=")
                .append(String.valueOf(method.apply(target)))
                .append(", ");
    }

}
