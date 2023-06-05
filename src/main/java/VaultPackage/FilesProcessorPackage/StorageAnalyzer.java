package VaultPackage.FilesProcessorPackage;

import java.io.IOException;

public class StorageAnalyzer {

    private FilesInVaultManager filesInVaultManager;

    public StorageAnalyzer() {
        try {
            filesInVaultManager = new FilesInVaultManager();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error initializing FilesInVaultManager: " + e.getMessage());
        }
    }

    public void analyzeStorage() {
        try {
            System.out.println("Storage Analysis:");
            System.out.println("Total files in vault: " + filesInVaultManager.getFilesInVault().size());
            calculateTotalFileSize();
            calculateFileTypeDistribution();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error analyzing storage: " + e.getMessage());
        }
    }

    private void calculateTotalFileSize() throws IOException, ClassNotFoundException {
        long totalSize = 0;
        for (LogFile logFile : filesInVaultManager.getFilesInVault()) {
            totalSize += Integer.parseInt(logFile.getFileToProcess().getFileSize());
        }
        System.out.println("Total size of all files: " + totalSize + " bytes");
    }

    private void calculateFileTypeDistribution() throws IOException, ClassNotFoundException {
        FileTypeCounter fileTypeCounter = new FileTypeCounter();
        for (LogFile logFile : filesInVaultManager.getFilesInVault()) {
            fileTypeCounter.incrementFileTypeCount(logFile.getFileToProcess().getFileType());
        }
        System.out.println("File Type Distribution:");
        fileTypeCounter.printFileTypeCounts();
    }

    private static class FileTypeCounter {
        private java.util.Map<String, Integer> fileTypeCounts;

        public FileTypeCounter() {
            fileTypeCounts = new java.util.HashMap<>();
        }

        public void incrementFileTypeCount(String fileType) {
            fileTypeCounts.put(fileType, fileTypeCounts.getOrDefault(fileType, 0) + 1);
        }

        public void printFileTypeCounts() {
            for (java.util.Map.Entry<String, Integer> entry : fileTypeCounts.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }
    }

    public void printStorageStats() {
        try {
            System.out.println("----- Storage Statistics -----");
            System.out.println("Total files in vault: " + filesInVaultManager.getFilesInVault().size());
            calculateTotalFileSize();
            calculateFileTypeDistribution();
            System.out.println("--------------------------------");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error printing storage statistics: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        StorageAnalyzer storageAnalyzer = new StorageAnalyzer();
        storageAnalyzer.printStorageStats();
    }
}
