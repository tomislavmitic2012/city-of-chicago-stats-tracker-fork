package com.depaul.edu.se491.utils;

import com.depaul.edu.se491.global.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Tom Mitic on 3/9/15.
 */
public class TokenUtils {

    public static final String MAGIC_KEY = AppConstants.SECRET_KEY;
    private static final Logger logger = LoggerFactory.getLogger(TokenUtils.class);

    private TokenUtils() {

    }

    public static String createToken(UserDetails userDetails) {
        /* Expires in one hour */
        long exp = System.currentTimeMillis() + 1000L * 60 * 60;

        StringBuilder token = new StringBuilder();
        token.append(userDetails.getUsername()).append(":")
                .append(exp).append(":").append(TokenUtils.computeSignature(userDetails, exp));
        return token.toString();
    }

    public static String computeSignature(UserDetails userDetails, long exp) {
        StringBuilder sig = new StringBuilder();
        sig.append(userDetails.getUsername()).append("|")
                .append(exp).append("|")
                .append(userDetails.getPassword()).append("|")
                .append(TokenUtils.MAGIC_KEY);

        MessageDigest dig;
        try {
            dig = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            logger.error("NoSuchAlgorithmException", e);
            throw new IllegalStateException("No MD5 algorithm is available");
        }

        return new String(Hex.encode(dig.digest(sig.toString().getBytes())));
    }

    public static String getUserNameFromToken(String auth) {
        if (null == auth) {
            return null;
        }
        String[] parts = auth.split(":");
        return parts[0];
    }

    public static boolean validateToken(String auth, UserDetails userDetails) {
        String[] parts = auth.split(":");
        long exp = Long.parseLong(parts[1]);
        String sig = parts[2];

        if (exp < System.currentTimeMillis()) {
            return false;
        }
        return sig.equals(TokenUtils.computeSignature(userDetails, exp));
    }
}
