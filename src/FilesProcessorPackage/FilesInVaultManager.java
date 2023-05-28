package FilesProcessorPackage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import FilesPackage.FileToProcess;

class LogFile implements Serializable {
    LogFile(FileToProcess fileToProcess, String fileOriginPath, String fileDestinationPath, String passwordHash) {
        this.fileToProcess = fileToProcess;
        this.fileOriginPath = fileOriginPath;
        this.fileDestinationPath = fileDestinationPath;
        this.passwordHash = passwordHash;
    }

    private transient FileToProcess fileToProcess;
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
}

public class FilesInVaultManager {
    String logFilePath = "bin\\inventory.bin";
    List<LogFile> filesInVault = new ArrayList<>();


    public void addFileToVault(FileToProcess fileToProcess, String fileOriginPath, String fileDestinationPath, String passwordHash) {
        readObjectsFromFile();
        LogFile fileToAdd = new LogFile(fileToProcess, fileOriginPath, fileDestinationPath, passwordHash);
        filesInVault.add(fileToAdd);
        writeObjectsToFile();
    }

    public void removeFileFromVault(int index) {
        readObjectsFromFile();
        filesInVault.remove(index);
        writeObjectsToFile();
    }

    public LogFile getFileAtIndex(int index) {
        readObjectsFromFile();
        return filesInVault.get(index);
    }

    public List<LogFile> getFilesInVault() {
        readObjectsFromFile();
        return filesInVault;
    }
    public void listFilesInVault() {
        readObjectsFromFile();
        for (int i = 0; i < filesInVault.size(); i++) {
            System.out.println(i + ". " + filesInVault.get(i).getFileToProcess().getFileName());
        }
    }

    public void writeObjectsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(logFilePath))) {
            oos.writeObject(filesInVault);
            System.out.println("Objects written to the file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readObjectsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(logFilePath))) {
            System.out.println("Reading objects from the file:");

            // Read and display objects from the file
            while (true) {
                try {
                    List<LogFile> readLogFiles = (List<LogFile>) ois.readObject();
                    System.out.println(readLogFiles);
                } catch (EOFException e) {
                    break;  // Reached end of file
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
