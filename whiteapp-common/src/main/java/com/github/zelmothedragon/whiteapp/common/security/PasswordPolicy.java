package com.github.zelmothedragon.whiteapp.common.security;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Utilitaire pour le hachage complexe.
 *
 * @author MOSELLE Maxime
 */
public final class PasswordPolicy {

    /**
     * Algorithme PBKDF2 Hmac SHA224. <i>Password Based Key Derivation Function
     * 2</i>.
     */
    public static final String PBKDF2_HMAC_SHA224 = "PBKDF2WithHmacSHA224";

    /**
     * Algorithme PBKDF2 Hmac SHA256. <i>Password Based Key Derivation Function
     * 2</i>.
     */
    public static final String PBKDF2_HMAC_SHA256 = "PBKDF2WithHmacSHA256";

    /**
     * Algorithme PBKDF2 Hmac SHA384. <i>Password Based Key Derivation Function
     * 2</i>.
     */
    public static final String PBKDF2_HMAC_SHA384 = "PBKDF2WithHmacSHA384";

    /**
     * Algorithme PBKDF2 Hmac SHA512. <i>Password Based Key Derivation Function
     * 2</i>.
     */
    public static final String PBKDF2_HMAC_SHA512 = "PBKDF2WithHmacSHA512";

    /**
     * Nombre d'itération par défaut.
     */
    public static final int DEFAULT_ITERATIONS = 2048;

    /**
     * Taille du grain de sel en octet.
     */
    public static final int DEFAULT_SALT_SIZE_BYTES = 32;

    /**
     * Taille de la clef de hachage en octet.
     */
    public static final int DEFAULT_KEY_SIZE_BYTES = 32;

    /**
     * Caractère de séparation des éléments composant le mot de passe haché.
     */
    private static final String SEPARATOR = ":";

    /**
     * Index de l'emplacement de l'algorithme.
     */
    private static final int ALGORITHM_INDEX = 0;

    /**
     * Index de l'emplacement du nombre d'itération.
     */
    private static final int ITERATION_INDEX = 1;

    /**
     * Index de l'emplacement du sel.
     */
    private static final int SALT_INDEX = 2;

    /**
     * Index de l'emplacement du mot de passe haché.
     */
    private static final int PASSWORD_HASH_INDEX = 3;

    /**
     * Nombre d'élément dans la donnée haché au format:
     * {@code "<algorithm>:<iterations>:<base64(salt)>:<base64(hash)>"}
     */
    private static final int DATA_LENGTH = 4;

    /**
     * Constructeur interne. Pas d'instanciation.
     */
    private PasswordPolicy() {
        throw new UnsupportedOperationException("Instance not allowed");
    }

    /**
     * Hacher un mot de passe avec les paramètres par défaut.
     *
     * @param password Mot de passe en clair
     * @return Le mot de passe haché au format
     * {@code "<algorithm>:<iterations>:<base64(salt)>:<base64(hash)>"}
     */
    public static String generate(final String password) {
        return generate(password,
                PBKDF2_HMAC_SHA256,
                DEFAULT_ITERATIONS,
                DEFAULT_SALT_SIZE_BYTES,
                DEFAULT_KEY_SIZE_BYTES
        );
    }

    /**
     * Hacher un mot de passe.
     *
     * @param password Mot de passe en clair
     * @param algorithm Algorithme de hachage (voir les constantes de cette
     * classe)
     * @param iterations Nombre d'itération
     * @param saltSizeBytes Taille en octet du sel
     * @param keysizeBytes Taille en octet de la clef de hachage
     * @return Le mot de passe haché au format
     * {@code "<algorithm>:<iterations>:<base64(salt)>:<base64(hash)>"}
     */
    public static String generate(
            final String password,
            final String algorithm,
            final int iterations,
            final int saltSizeBytes,
            final int keysizeBytes) {

        String data;
        try {
            // Génération du sel aléatoirement.
            var random = new SecureRandom();
            var salt = new byte[saltSizeBytes];
            random.nextBytes(salt);

            // Hachage du mot de passe.
            var spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keysizeBytes);
            var factory = SecretKeyFactory.getInstance(algorithm);
            var passwordHash = factory.generateSecret(spec).getEncoded();

            // Encodage en Base64 du mot de passe et du sel.
            var base64Salt = Base64.getEncoder().encode(salt);
            var base64Password = Base64.getEncoder().encode(passwordHash);

            // Génération du résultat encodé au format:
            // <algorithm>:<iterations>:<base64(salt)>:<base64(hash)>
            data = String.join(SEPARATOR,
                    algorithm,
                    String.valueOf(iterations),
                    new String(base64Salt, StandardCharsets.UTF_8),
                    new String(base64Password, StandardCharsets.UTF_8)
            );

        } catch (InvalidKeySpecException | NoSuchAlgorithmException ex) {
            throw new IllegalArgumentException(ex);
        }
        return data;
    }

    /**
     * Vérifier si les mots de passe sont identiques.
     *
     * @param password Mot de passe en clair
     * @param passwordEncoded Mot de passe encodé au format
     * {@code "<algorithm>:<iterations>:<base64(salt)>:<base64(hash)>"}
     * @return La valeur {@code true} si les mots de passe sont identiques,
     * sinon la valeur {@code false} est retournée
     */
    public static boolean verify(final String password, final String passwordEncoded) {
        boolean equals;
        try {
            var data = passwordEncoded.split(SEPARATOR);
            if (data.length != DATA_LENGTH) {
                throw new IllegalArgumentException("Wrong password format, expected: \"<algorithm>:<iterations>:<base64(salt)>:<base64(hash)>\"");
            }
            // Récupération des paramètres
            var algorithm = data[ALGORITHM_INDEX];
            var iterations = Integer.valueOf(data[ITERATION_INDEX]);
            var salt = Base64.getDecoder().decode(data[SALT_INDEX]);
            var passwordHash = Base64.getDecoder().decode(data[PASSWORD_HASH_INDEX]);
            var keySizeByte = passwordHash.length * 8;

            // Calcul du hash avec le mot de passe initial.
            var spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keySizeByte);
            var factory = SecretKeyFactory.getInstance(algorithm);
            var currentPasswordHash = factory.generateSecret(spec).getEncoded();

            // Comparaison des deux hashs.
            equals = Arrays.equals(
                    currentPasswordHash,
                    passwordHash
            );

        } catch (InvalidKeySpecException | NoSuchAlgorithmException ex) {
            throw new IllegalArgumentException(ex);
        }
        return equals;
    }

}
