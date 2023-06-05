package BatchFileRenamerPackage;

import FilesPackage.FileToProcess;
import VaultPackage.FilesProcessorPackage.FilesBrowser;
import VaultPackage.HashPackage.SHA512_HashGenerator;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Scanner;

public class BatchFileRenamer {

    public void test() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Batch File Renamer");
//        System.out.println("==================");
//        System.out.println("Enter the directory path:");
//        String directoryPath = scanner.nextLine();
//
//        File directory = new File(directoryPath);
//        if (!directory.isDirectory()) {
//            System.out.println("Invalid directory path.");
//            return;
//        }

        FilesBrowser fileBrowser = new FilesBrowser();
        String[] filePaths = fileBrowser.browseFilesOnly();
        if (filePaths.length == 0) {
            System.out.println("No files selected.");
            return;
        }

        File[] files = new File[filePaths.length];
        for (int i = 0; i < filePaths.length; i++) {
            files[i] = new File(filePaths[i]);
        }

        System.out.println("\nAvailable naming schemes:");
        System.out.println("1. Numbered (1, 2, 3, ...)");
        System.out.println("2. File Creation Date");
        System.out.println("3. File Size");
        System.out.println("4. File Type");
        System.out.println("5. File Name SHA-512 Hash");
        System.out.println("\nEnter the naming scheme number:");
        Scanner scanner = new Scanner(System.in);
        int schemeNumber = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        String[] renamedFiles = new String[files.length];

        switch (schemeNumber) {
            case 1:
                renamedFiles = renameNumbered(files);
                break;
            case 2:
                renamedFiles = renameByCreationDate(files);
                break;
            case 3:
                renamedFiles = renameBySize(files);
                break;
            case 4:
                renamedFiles = renameByType(files);
                break;
            case 5:
                renamedFiles = renameBySHA512(files);
                break;
            default:
                System.out.println("Invalid naming scheme number.");
                return;
        }

        System.out.println("\nRenamed files:");
        for (int i = 0; i < files.length; i++) {
            System.out.println(files[i].getName() + " -> " + renamedFiles[i]);
        }
    }

    private static String[] renameNumbered(File[] files) {
        String[] renamedFiles = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            FileToProcess fileToProcess = null;
            try {
                fileToProcess = new FileToProcess(files[i].getAbsolutePath());
            } catch (IOException e) {
                System.out.println("Error reading file: " + files[i].getName());
                e.printStackTrace();
                continue;
            }
            String extension = fileToProcess.getFileExtension();
            renamedFiles[i] = getUniqueFileName(String.valueOf(i + 1), extension, files[i].getParent());
            boolean success = files[i].renameTo(new File(files[i].getParent(), renamedFiles[i]));
            if (!success) {
                System.out.println("Error renaming file: " + files[i].getName());
            }
        }
        return renamedFiles;
    }

    private static String[] renameByCreationDate(File[] files) {
        String[] renamedFiles = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            FileToProcess fileToProcess = null;
            try {
                fileToProcess = new FileToProcess(files[i].getAbsolutePath());
            } catch (IOException e) {
                System.out.println("Error reading file: " + files[i].getName());
                e.printStackTrace();
                continue;
            }
            String creationTime = fileToProcess.getStringFileCreationTime();
            String extension = fileToProcess.getFileExtension();
            renamedFiles[i] = getUniqueFileName(creationTime, extension, files[i].getParent());
            boolean success = files[i].renameTo(new File(files[i].getParent(), renamedFiles[i]));
            if (!success) {
                System.out.println("Error renaming file: " + files[i].getName());
            }
        }
        return renamedFiles;
    }

    private static String[] renameBySize(File[] files) {
        String[] renamedFiles = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            FileToProcess fileToProcess = null;
            try {
                fileToProcess = new FileToProcess(files[i].getAbsolutePath());
            } catch (IOException e) {
                System.out.println("Error reading file: " + files[i].getName());
                e.printStackTrace();
                continue;
            }
            String fileSize = fileToProcess.getFileSize();
            String extension = fileToProcess.getFileExtension();
            renamedFiles[i] = getUniqueFileName(fileSize, extension, files[i].getParent());
            boolean success = files[i].renameTo(new File(files[i].getParent(), renamedFiles[i]));
            if (!success) {
                System.out.println("Error renaming file: " + files[i].getName());
            }
        }
        return renamedFiles;
    }

    private static String[] renameByType(File[] files) {
        String[] renamedFiles = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            FileToProcess fileToProcess = null;
            try {
                fileToProcess = new FileToProcess(files[i].getAbsolutePath());
            } catch (IOException e) {
                System.out.println("Error reading file: " + files[i].getName());
                e.printStackTrace();
                continue;
            }
            String fileType = fileToProcess.getFileType();
            String extension = fileToProcess.getFileExtension();
            String sanitizedFileType = fileType.replaceAll("[\\\\/:*?\"<>|]", ",");
            renamedFiles[i] = getUniqueFileName(sanitizedFileType, extension, files[i].getParent());
            boolean success = files[i].renameTo(new File(files[i].getParent(), renamedFiles[i]));
            if (!success) {
                System.out.println("Error renaming file: " + files[i].getName());
            }
        }
        return renamedFiles;
    }

    private static String[] renameBySHA512(File[] files) {
        String[] renamedFiles = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            FileToProcess fileToProcess = null;
            try {
                fileToProcess = new FileToProcess(files[i].getAbsolutePath());
            } catch (IOException e) {
                System.out.println("Error reading file: " + files[i].getName());
                e.printStackTrace();
                continue;
            }
            String fileExtension = fileToProcess.getFileExtension();
            String fileName = fileToProcess.getFileName();
            String sha512Hash = SHA512_HashGenerator.generateHash(fileName);
            renamedFiles[i] = getUniqueFileName(sha512Hash, fileExtension, files[i].getParent());
            boolean success = files[i].renameTo(new File(files[i].getParent(), renamedFiles[i]));
            if (!success) {
                System.out.println("Error renaming file: " + files[i].getName());
            }
        }
        return renamedFiles;
    }

    // Utility method to extract the file extension from the file name
    private static String getFileExtensionFromString(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        return (lastDotIndex != -1) ? fileName.substring(lastDotIndex) : "";
    }

    // Utility method to generate a unique file name by adding a suffix (n) if necessary
    private static String getUniqueFileName(String baseName, String extension, String directoryPath) {
        String uniqueName = baseName+"."+extension;
        int counter = 1;
        System.out.println(baseName+"."+extension);
        while (new File(directoryPath, uniqueName).exists()) {
            uniqueName = baseName + "(" + counter + ")" + "." + extension;
            counter++;
            System.out.println(counter);
        }

        return uniqueName;
    }

}
