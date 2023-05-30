import FilesProcessorPackage.FileVaultProcessor;
import FilesProcessorPackage.FilesBrowser;
import FilesProcessorPackage.FilesInVaultManager;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InterruptedException, ClassNotFoundException {
//         
//        filesBrowser = new FilesBrowser();
//        fileVaultProcessor.vault(filesBrowser.browseFile(), "password", false);
//        filesBrowser = new FilesBrowser();
//        fileVaultProcessor.vault(filesBrowser.browseFile(), "password", false);
//        filesBrowser = new FilesBrowser();
//        fileVaultProcessor.vault(filesBrowser.browseFile(),"password",false);


        new FilesInVaultManager().printFilesList();
        new FilesInVaultManager().getFileAtIndex(0).printFileDetails();


//        System.out.println("Enter index of file to unvault");
//        int index = new Scanner(System.in).nextInt();
//        fileVaultProcessor.unVault(index,"password");
//        new FilesInVaultManager().listFilesInVault();

        //fileVaultProcessor.unVault("D:\\My Codes\\Java\\OOSE Semester Project FileHub\\ExileFileVault\\192695a757c@37391071708900ENC.-inc","Shit\\Unvaulted.jpg","password");
        System.exit(0);
    }
}