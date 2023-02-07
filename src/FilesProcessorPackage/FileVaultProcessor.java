package FilesProcessorPackage;

import CompressionDecompressionPackage.CompressionModule;
import CompressionDecompressionPackage.DeCompressionModule;
import EncryptionDecryptionPackage.AESFileDecryptionModule;
import EncryptionDecryptionPackage.AESFileEncryptionModule;

import FilesPackage.*;
import HashPackage.SHA512_HashGenerator;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;

public class FileVaultProcessor {

    private String encryptedDestinationPath;

    public void vault(String filePathToVault, String Password) throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InterruptedException {

        //compressing file
        FileToCompress fileToCompress = new FileToCompress(filePathToVault, "tempCompressedFileToEncrypt.zip");
        System.out.println("tempCompressedFileToEncrypt.zip created");
        CompressionModule compressor = new CompressionModule();
        compressor.compressToZip(fileToCompress, Password);
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
        //needs logging, postponed for now
    }

    //Path path = FileSystems.getDefault().getPath("/j", "sa");
//Files.setAttribute(path, "dos:hidden", true);
    public void unVault(String filePathToUnVault, String originFilePath, String Password) throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InterruptedException {
        //Unhiding file and making it re-writeable again
        FileToProcess fileToProcess_UnHide = new FileToProcess(filePathToUnVault);
        //Files.setAttribute(fileToProcess_UnHide.getAbsoluteFilePath(), "dos:hidden", false);
        fileToProcess_UnHide.getFile().setWritable(true);
        //Decrypting file
        FileToDeCrypt fileToDeCrypt = new FileToDeCrypt(filePathToUnVault, "tempDeCryptedFileToUncompress.zip");
        AESFileDecryptionModule aesFileDecryptionModule = new AESFileDecryptionModule();
        aesFileDecryptionModule.decryptWithEcb(fileToDeCrypt, Password);

        //Decompressing file
        FileToDeCompress fileToDecompress = new FileToDeCompress("tempDeCryptedFileToUncompress.zip");//creating a file to decompress

        //module to remove last folder from destination path find the index of the last slash in the file path for decompressed files
        int lastSlashIndex = originFilePath.lastIndexOf("/");
        // Extract the file path up to the last slash
        String newFilePath = originFilePath.substring(0, lastSlashIndex + 1);

        //decompressing files
        DeCompressionModule deCompressor = new DeCompressionModule();
        deCompressor.decompressZip(fileToDecompress, newFilePath, Password);

        //Deleting Compressed File
        FileToProcess fileToDelete0 = new FileToProcess(filePathToUnVault);
        fileToDelete0.getFile().delete();

        //Deleting Encrypted File
        FileToProcess fileToDelete1 = new FileToProcess("tempDeCryptedFileToUncompress.zip");
        fileToDelete1.getFile().delete();


    }

    public String getEncryptedDestinationPath() {
        return encryptedDestinationPath;
    }
}
