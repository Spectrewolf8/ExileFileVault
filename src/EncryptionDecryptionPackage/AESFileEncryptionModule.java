package EncryptionDecryptionPackage;

import FilesPackage.FileToEncrypt;
import HashPackage.SHA512_HashGenerator;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class AESFileEncryptionModule {


    public void encryptWitEcb(FileToEncrypt fileToEncrypt, String password) throws IOException,
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {

        byte[] key = (SHA512_HashGenerator.generateHash(password).substring(0, 16)).getBytes(StandardCharsets.UTF_8);// 32 byte = 256 bit key length
        byte[] initialVector = (SHA512_HashGenerator.generateHash(password).substring(16, 32)).getBytes(StandardCharsets.UTF_8);
        System.out.println(SHA512_HashGenerator.generateHash(password).substring(0, 16) + "  " + SHA512_HashGenerator.generateHash(password).substring(16, 32));
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");

        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(initialVector));

        try (FileInputStream fis = new FileInputStream(fileToEncrypt.getAbsoluteFilePath().toString());
             BufferedInputStream in = new BufferedInputStream(fis);
             FileOutputStream out = new FileOutputStream(fileToEncrypt.getDestinationPath());
             BufferedOutputStream bos = new BufferedOutputStream(out)) {
            byte[] inputBuffer = new byte[1024];
            int len;
            while ((len = in.read(inputBuffer)) != -1) {
                byte[] outputBuffer = cipher.update(inputBuffer, 0, len);
                if (outputBuffer != null)
                    bos.write(outputBuffer);
            }
            byte[] outputBuffer = cipher.doFinal();
            if (outputBuffer != null)
                bos.write(outputBuffer);

        }
    }

}
