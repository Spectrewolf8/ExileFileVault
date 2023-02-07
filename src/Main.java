import FilesProcessorPackage.FileVaultProcessor;
import FilesProcessorPackage.FilesBrowser;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InterruptedException {
        FileVaultProcessor fileVaultProcessor = new FileVaultProcessor();
        FilesBrowser filesBrowser = new FilesBrowser();
        fileVaultProcessor.vault(filesBrowser.browseFile(),"password");

    }
}