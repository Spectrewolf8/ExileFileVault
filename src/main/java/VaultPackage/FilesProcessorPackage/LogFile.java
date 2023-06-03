package VaultPackage.FilesProcessorPackage;

import FilesPackage.FileToProcess;

import java.io.Serial;
import java.io.Serializable;

public class LogFile implements Serializable {
    LogFile(FileToProcess fileToProcess, String fileOriginPath, String fileDestinationPath, String passwordHash) {
        this.fileToProcess = fileToProcess;
        this.fileOriginPath = fileOriginPath;
        this.fileDestinationPath = fileDestinationPath;
        this.passwordHash = passwordHash;
    }

    @Serial
    private static final long serialVersionUID = 1545032115648844594L;

    private FileToProcess fileToProcess;
    private String fileOriginPath;
    private String fileDestinationPath;
    private String passwordHash;

    public FileToProcess getFileToProcess() {
        return fileToProcess;
    }

    public String getFileDestinationPath() {
        return fileDestinationPath;
    }

    public String getFileOriginPath() {
        return fileOriginPath;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void printFileDetails() {
        System.out.println("File name: " + fileToProcess.getFileName());
        System.out.println("File type: " + fileToProcess.getFileType());
        System.out.println("File size: " + fileToProcess.getFileSize());
        System.out.printf("File extension: %s\n", fileToProcess.getFileExtension());
        System.out.println("File creation time: " + fileToProcess.getStringFileCreationTime());
        System.out.println("File modified time: " + fileToProcess.getStringFileModifiedTime());
        System.out.println("File origin path: " + fileOriginPath);
        System.out.println("File destination path: " + fileDestinationPath);
        System.out.println("File password hash: " + passwordHash);

    }
}
