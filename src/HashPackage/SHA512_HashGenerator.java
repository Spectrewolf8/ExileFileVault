package HashPackage;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA512_HashGenerator {
    public static String generateHash(String input) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            byte[] byteMessageDigest = messageDigest.digest(input.getBytes());
            BigInteger bigInteger = new BigInteger(1, byteMessageDigest);

            StringBuilder hashText = new StringBuilder(bigInteger.toString(16));
            while (hashText.length() < 32) {
                hashText.insert(0, "0");
            }
            return hashText.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        System.out.println(generateHash("jshdjshjdhsjhsdjshd"));
    }
}
