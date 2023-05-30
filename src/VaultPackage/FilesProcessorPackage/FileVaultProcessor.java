package VaultPackage.FilesProcessorPackage;

import CompressionDecompressionPackage.CompressionModule;
import CompressionDecompressionPackage.DeCompressionModule;
import VaultPackage.EncryptionDecryptionPackage.AESFileDecryptionModule;
import VaultPackage.EncryptionDecryptionPackage.AESFileEncryptionModule;

import FilesPackage.*;
import VaultPackage.HashPackage.SHA512_HashGenerator;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;

public class FileVaultProcessor {

    private String encryptedDestinationPath;
    private FilesInVaultManager filesInVaultManager = new FilesInVaultManager();

    public void vault(String filePathToVault, String Password, Boolean requireCompression) throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InterruptedException, ClassNotFoundException {
        FileToProcess fileBeingAddedToVault = new FileToProcess(filePathToVault);
        if (requireCompression) {
            FileToCompress fileToCompress = new FileToCompress(filePathToVault, "tempCompressedFileToEncrypt.zip");
            System.out.println("tempCompressedFileToEncrypt.zip created");
            CompressionModule compressor = new CompressionModule();
            compressor.compressToZipWithPassword(fileToCompress, Password);
            compressor.runCompressionProgressStats();
            fileToCompress.getFile().delete();//delete the file from original location as it has been moved to vault

            //encrypting file

            String nameHash = SHA512_HashGenerator.generateHash(filePathToVault + Password).substring(0, 10);
            encryptedDestinationPath = nameHash + "c@" + new Time(System.nanoTime()).getTime() + "ENC.-inc";//generating a file name so that it never clashes with an yother file no matter what
            FileToEncrypt fileToEncrypt = new FileToEncrypt("tempCompressedFileToEncrypt.zip", encryptedDestinationPath);//creating a file to encrypt
            AESFileEncryptionModule aesFileEncryptionModule = new AESFileEncryptionModule();
            aesFileEncryptionModule.encryptWitEcb(fileToEncrypt, Password);

            //hiding file and setting file as readonly
            FileToProcess fileToProcess_Hide = new FileToProcess(encryptedDestinationPath);// creating a file for hiding process, then hiding it and making it read only to add a little more security
            Files.setAttribute(fileToProcess_Hide.getAbsoluteFilePath(), "dos:hidden", true);
            fileToProcess_Hide.getFile().setReadOnly();
            //Deleting Compressed File
            FileToProcess fileToDelete = new FileToProcess("tempCompressedFileToEncrypt.zip");
            fileToDelete.getFile().delete();

            filesInVaultManager.addFileToVault(fileBeingAddedToVault, filePathToVault, encryptedDestinationPath, SHA512_HashGenerator.generateHash(Password));
        } else {

            //encrypting file
            String nameHash = SHA512_HashGenerator.generateHash(filePathToVault + Password).substring(0, 10);
            encryptedDestinationPath = nameHash + "c@" + new Time(System.nanoTime()).getTime() + "ENC.-inc";//generating a file name so that it never clashes with an yother file no matter what
            FileToEncrypt fileToEncrypt = new FileToEncrypt(filePathToVault, encryptedDestinationPath);//creating a file to encrypt
            AESFileEncryptionModule aesFileEncryptionModule = new AESFileEncryptionModule();
            aesFileEncryptionModule.encryptWitEcb(fileToEncrypt, Password);

            //hiding file and setting file as readonly
            FileToProcess fileToProcess_Hide = new FileToProcess(encryptedDestinationPath);// creating a file for hiding process, then hiding it and making it read only to add a little more security
            Files.setAttribute(fileToProcess_Hide.getAbsoluteFilePath(), "dos:hidden", true);
            fileToProcess_Hide.getFile().setReadOnly();
            filesInVaultManager.addFileToVault(fileBeingAddedToVault, filePathToVault, encryptedDestinationPath, SHA512_HashGenerator.generateHash(Password));

        }

    }

    //Path path = FileSystems.getDefault().getPath("/j", "sa");
//Files.setAttribute(path, "dos:hidden", true);
    public void unVault(String filePathToUnVault, String originFilePath, String Password) throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InterruptedException {
        //Unhiding file and making it re-writeable again
        FileToProcess fileToProcess_UnHide = new FileToProcess(filePathToUnVault);
        Files.setAttribute(fileToProcess_UnHide.getAbsoluteFilePath(), "dos:hidden", false);
        fileToProcess_UnHide.getFile().setWritable(true);
        //Decrypting file
        FileToDeCrypt fileToDeCrypt = new FileToDeCrypt(filePathToUnVault, "tempDeCryptedFileToProcess");
        AESFileDecryptionModule aesFileDecryptionModule = new AESFileDecryptionModule();
        aesFileDecryptionModule.decryptWithEcb(fileToDeCrypt, Password);
        if (new net.lingala.zip4j.ZipFile("tempDeCryptedFileToProcess").isValidZipFile()) {
            new File("tempDeCryptedFileToProcess").renameTo(new File("tempDeCryptedFileToUncompress.zip"));
            System.out.printf("Zip file is valid");
            //Decompressing file
            FileToDeCompress fileToDecompress = new FileToDeCompress("tempDeCryptedFileToUncompress.zip");//creating a file to decompress

            //module to remove last folder from destination path find the index of the last slash in the file path for decompressed files
            int lastSlashIndex = originFilePath.lastIndexOf("\\");
            // Extract the file path up to the last slash
            String newFilePath = originFilePath.substring(0, lastSlashIndex + 1);

            //decompressing files
            DeCompressionModule deCompressor = new DeCompressionModule();
            deCompressor.decompressZipWithPassword(fileToDecompress, newFilePath + "Unvaulted.jpg", Password);

            //Deleting Compressed File
            FileToProcess fileToDelete0 = new FileToProcess(filePathToUnVault);
            fileToDelete0.getFile().delete();

            //Deleting Encrypted File
            FileToProcess fileToDelete1 = new FileToProcess("tempDeCryptedFileToUncompress.zip");
            fileToDelete1.getFile().delete();
        } else {
            new File("tempDeCryptedFileToProcess").renameTo(new File(originFilePath));
            System.out.println("Unvaulted file is not a zip file");
        }
        //Deleting Encrypted File
        FileToProcess fileToDelete1 = new FileToProcess("tempDeCryptedFileToProcess");
        fileToDelete1.getFile().delete();
        //Deleting Compressed File
        FileToProcess fileToDelete0 = new FileToProcess(filePathToUnVault);
        fileToDelete0.getFile().delete();
    }

    public void unVault(int index, String password) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InterruptedException, ClassNotFoundException {
        LogFile fileFromLogs = filesInVaultManager.getFileAtIndex(index);
        unVault(fileFromLogs.getFileDestinationPath(), fileFromLogs.getFileOriginPath(), password);
        filesInVaultManager.removeFileFromVault(index);
    }

    public String getEncryptedDestinationPath() {
        return encryptedDestinationPath;
    }
}
