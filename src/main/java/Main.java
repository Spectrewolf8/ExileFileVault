import BatchFileRenamerPackage.BatchFileRenamer;
import FileConverterPackage.FileConverter;
import VaultPackage.FilesProcessorPackage.FileVaultProcessor;
import VaultPackage.FilesProcessorPackage.FilesBrowser;
import VaultPackage.FilesProcessorPackage.FilesInVaultManager;
import VaultPackage.FilesProcessorPackage.LogFile;

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
//        FilesBrowser filesBrowser = new FilesBrowser();
        FileVaultProcessor fileVaultProcessor = new FileVaultProcessor();
//        filesBrowser = new FilesBrowser();
//        fileVaultProcessor.vault(filesBrowser.browseFileOnly(), "password", false);
//        filesBrowser = new FilesBrowser();
//        fileVaultProcessor.vault(filesBrowser.browseFileOnly(), "password", false);
//        filesBrowser = new FilesBrowser();
//        fileVaultProcessor.vault(filesBrowser.browseFileOnly(),"password",false);


        new FilesInVaultManager().printFilesList();
//        LogFile logFile = new FilesInVaultManager().getFileAtIndex(0);
//        if (logFile != null) {
//            logFile.printFileDetails();
//        }else {
//            System.out.println("No file found/selected");
//        }


        System.out.println("Enter index of file to unvault");
        int index = new Scanner(System.in).nextInt();
        fileVaultProcessor.unVault(index,"password");
        new FilesInVaultManager().listFilesInVault();

        //fileVaultProcessor.unVault("D:\\My Codes\\Java\\OOSE Semester Project FileHub\\ExileFileVault\\192695a757c@37391071708900ENC.-inc","Shit\\Unvaulted.jpg","password");
//        BatchFileRenamer batchFileRenamer = new BatchFileRenamer();
//        batchFileRenamer.test();
//        System.exit(0);

//        FileConverter.convertPdfToWord("R634080006StickerMuleInvoiceForSpec.pdf", "R634080006StickerMuleInvoiceForSpec.docx");
//        FileConverter.convertWordToPdf("TestWordFile.docx", "0. Software Scope Documnet_Revised.pdf");
//          FileConverter.convertFile("TestWordFile.docx", "TestWordFile.pdf",0);
    }
}