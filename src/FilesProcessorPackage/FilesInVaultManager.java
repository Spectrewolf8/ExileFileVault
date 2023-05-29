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


//    public void addFileToVault(FileToProcess fileToProcess, String fileOriginPath, String fileDestinationPath, String passwordHash) {
//        readObjectsFromFile();
//        LogFile fileToAdd = new LogFile(fileToProcess, fileOriginPath, fileDestinationPath, passwordHash);
//        filesInVault.add(fileToAdd);
//        writeObjectsToFile();
//    }
public void addFileToVault(FileToProcess fileToProcess, String fileOriginPath, String fileDestinationPath, String passwordHash) {
    LogFile fileToAdd = new LogFile(fileToProcess, fileOriginPath, fileDestinationPath, passwordHash);
    filesInVault.add(fileToAdd);
    writeObjectsToFile();
    // No need to writeObjectsToFile() here
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
        System.out.println(filesInVault.size());

        for (int i = 0; i < filesInVault.size(); i++) {
            System.out.println(i + ". " + filesInVault.get(i).getFileToProcess().getFileName());
        }
    }

    public void writeObjectsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(logFilePath, true))) {
            for (LogFile logFile : filesInVault) {
                oos.writeObject(logFile);
            }
            System.out.println("Objects written to the file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //    public void readObjectsFromFile() {
//        filesInVault.clear(); // Clear the existing list before reading the objects
//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(logFilePath))) {
//            System.out.println("Reading objects from the file:");
//
//            while (true) {
//                try {
//                    LogFile logFile = (LogFile) ois.readObject();
//                    filesInVault.add(logFile);
//                    System.out.println(logFile);
//                } catch (EOFException e) {
//                    break; // Reached end of file
//                }
//            }
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
    public void readObjectsFromFile() {
        filesInVault.clear(); // Clear the existing list before reading the objects
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(logFilePath))) {
            System.out.println("Reading objects from the file:");

            while (true) {
                try {
                    LogFile logFile = (LogFile) ois.readObject();
                    filesInVault.add(logFile);
                } catch (EOFException e) {
                    break; // Reached end of file
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
