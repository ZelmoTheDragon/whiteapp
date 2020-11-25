package com.github.zelmothedragon.whiteapp.common.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utilitaire pour le hachage.
 *
 * @author MOSELLE Maxime
 */
public final class Hash {

    /**
     * Algorithme de hachage MD5.
     */
    public static final String MD5 = "MD5";

    /**
     * Algorithme de hachage SHA-1.
     */
    public static final String SHA1 = "SHA-1";

    /**
     * Algorithme de hachage SHA-224.
     */
    public static final String SHA224 = "SHA-224";

    /**
     * Algorithme de hachage SHA-256.
     */
    public static final String SHA256 = "SHA-256";

    /**
     * Algorithme de hachage SHA-384.
     */
    public static final String SHA384 = "SHA-384";

    /**
     * Algorithme de hachage SHA-512.
     */
    public static final String SHA512 = "SHA-512";

    /**
     * Constructeur interne. Pas d'instanciation.
     */
    private Hash() {
        throw new UnsupportedOperationException("Instance not allowed");
    }

    /**
     * Hacher une chaîne de caractères.
     *
     * @param algorithm L'algorithme de hachage (voir les constantes de cette
     * classe)
     * @param word Le mot à hacher
     *
     * @return Le mot haché.
     */
    public static String execute(final String algorithm, final String word) {
        final StringBuilder hexaCode = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(word.getBytes(StandardCharsets.UTF_8));
            final byte[] bytes = md.digest();
            for (byte b : bytes) {
                final String hexa = Integer.toHexString(0xFF & b);
                if (hexa.length() == 1) {
                    hexaCode.append('0');
                }
                hexaCode.append(hexa);
            }
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalArgumentException(ex);
        }
        return hexaCode.toString();
    }
}
