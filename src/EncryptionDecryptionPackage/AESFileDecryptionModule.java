package EncryptionDecryptionPackage;

import FilesPackage.FileToDeCrypt;
import HashPackage.SHA512_HashGenerator;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class AESFileDecryptionModule {

    public void decryptWithEcb(FileToDeCrypt fileToDeCrypt, String password) throws IOException,
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {

        byte[] key = (SHA512_HashGenerator.generateHash(password).substring(0, 16)).getBytes(StandardCharsets.UTF_8);// 32 byte = 256 bit key length
        byte[] initialVector = (SHA512_HashGenerator.generateHash(password).substring(16, 32)).getBytes(StandardCharsets.UTF_8);
        try (FileInputStream in = new FileInputStream(fileToDeCrypt.getAbsoluteFilePath().toString());
             FileOutputStream out = new FileOutputStream(fileToDeCrypt.getDestinationPath())) {
            byte[] inputBuffer = new byte[1024];
            int len;
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");

            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(initialVector));
            while ((len = in.read(inputBuffer)) != -1) {
                byte[] outputBuffer = cipher.update(inputBuffer, 0, len);
                if (outputBuffer != null)
                    out.write(outputBuffer);
            }
            byte[] outputBuffer = cipher.doFinal();
            if (outputBuffer != null)
                out.write(outputBuffer);
        }
    }
}
