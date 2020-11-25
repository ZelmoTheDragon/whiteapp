package com.github.zelmothedragon.whiteapp.common.security;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Utilitaire pour la création de signature numérique.
 *
 * @author MOSELLE Maxime
 */
public final class HashMac {

    /**
     * Algorithme Hmac MD5.
     */
    public static final String HMAC_MD5 = "HmacMD5";

    /**
     * Algorithme Hmac SHA-1.
     */
    public static final String HMAC_SHA1 = "HmacSHA1";

    /**
     * Algorithme Hmac SHA-256.
     */
    public static final String HMAC_SHA256 = "HmacSHA256";

    /**
     * Constructeur interne. Pas d'instanciation.
     */
    private HashMac() {
        throw new UnsupportedOperationException("Instance not allowed");
    }

    /**
     * Hacher une chaîne de caractères avec une clef.
     *
     * @param algorithm L'algorithme de hachage (voir les constantes de cette
     * classe)
     * @param key La clef secrète
     * @param word Le mot à hacher
     *
     * @return Le mot hacher signé avec la clef secrète
     */
    public static String execute(final String algorithm, final String key, final String word) {

        final SecretKey secret = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), algorithm);
        final StringBuilder hexaCode = new StringBuilder();
        try {
            Mac mac = Mac.getInstance(algorithm);
            mac.init(secret);
            final byte[] bytes = mac.doFinal(word.getBytes(StandardCharsets.UTF_8));
            for (byte b : bytes) {
                final String hexa = Integer.toHexString(0xFF & b);
                if (hexa.length() == 1) {
                    hexaCode.append('0');
                }
                hexaCode.append(hexa);
            }
        } catch (NoSuchAlgorithmException | InvalidKeyException ex) {
            throw new IllegalArgumentException(ex);
        }
        return hexaCode.toString();
    }
}
