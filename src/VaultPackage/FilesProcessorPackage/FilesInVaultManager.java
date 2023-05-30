package VaultPackage.FilesProcessorPackage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import FilesPackage.FileToProcess;

public class FilesInVaultManager {
    String logFilePath = "bin\\inventory.bin";
    List<LogFile> filesInVault = new ArrayList<>();


    //    public void addFileToVault(FileToProcess fileToProcess, String fileOriginPath, String fileDestinationPath, String passwordHash) {
//        readObjectsFromFile();
//        LogFile fileToAdd = new LogFile(fileToProcess, fileOriginPath, fileDestinationPath, passwordHash);
//        filesInVault.add(fileToAdd);
//        writeObjectsToFile();
//    }
    public void addFileToVault(FileToProcess fileToProcess, String fileOriginPath, String fileDestinationPath, String passwordHash) throws IOException, ClassNotFoundException {
        // Read the existing files from the file
        try {
            readFilesListFromFile();
        } catch (EOFException e) {
            System.out.println("Skipping reading since file is empty");
        }

        LogFile fileToAdd = new LogFile(fileToProcess, fileOriginPath, fileDestinationPath, passwordHash);
        filesInVault.add(fileToAdd); // Add the new file to the list

        writeFilesListToFile(); // Write the updated list back to the file
    }


    public void removeFileFromVault(int index) throws IOException, ClassNotFoundException {
        readFilesListFromFile();
        filesInVault.remove(index);
        writeFilesListToFile();
    }

    public LogFile getFileAtIndex(int index) throws IOException, ClassNotFoundException {
        readFilesListFromFile();
        return filesInVault.get(index);
    }

    public List<LogFile> getFilesInVault() throws IOException, ClassNotFoundException {
        readFilesListFromFile();
        return filesInVault;
    }

    public void listFilesInVault() throws IOException, ClassNotFoundException {
        readFilesListFromFile();
        System.out.println(filesInVault.size());
        for (int i = 0; i < filesInVault.size(); i++) {
            System.out.println(i + ". " + filesInVault.get(i).getFileOriginPath());
        }
    }

//    public void writeObjectsToFile() {
//        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(logFilePath, true))) {
//            for (LogFile logFile : filesInVault) {
//                oos.writeObject(logFile);
//            }
//            System.out.println("Objects written to the file successfully.");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void writeObjectsToFile() {
//        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(logFilePath))) {
//            for (LogFile logFile : filesInVault) {
//                oos.writeObject(logFile);
//            }
//            System.out.println("Objects written to the file successfully.");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    public void readObjectsFromFile() {
//        filesInVault.clear(); // Clear the existing list before reading the objects
//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(logFilePath))) {
//            System.out.println("Reading objects from the file:");
//
//            while (true) {
//                try {
//                    LogFile logFile = (LogFile) ois.readObject();
//                    filesInVault.add(logFile);
//                    System.out.println("File added to vault: " + logFile.getFileOriginPath());
//                } catch (EOFException e) {
//                    break; // Reached end of file
//                }
//            }
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
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

    public void deleteFileAtIndex(int index) {


        try {
            // Read the ArrayList from the binary file
            readFilesListFromFile();

            // Print the original ArrayList
            System.out.println("Original ArrayList:");
            printFilesList();

            // Index of the element to be deleted

            // Check if the index is within bounds
            if (index >= 0 && index < filesInVault.size()) {
                // Remove the element from the ArrayList
                filesInVault.remove(index);

                // Update the binary file with the modified ArrayList
                writeFilesListToFile();

                // Print the updated ArrayList
                System.out.println("Updated ArrayList:");
                printFilesList();
            } else {
                System.out.println("Invalid index to delete: " + index);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readFilesListFromFile() throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream(logFilePath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            filesInVault = (ArrayList<LogFile>) ois.readObject();
        }
    }


    private void writeFilesListToFile() throws IOException {
        try (FileOutputStream fos = new FileOutputStream(logFilePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(filesInVault);
        }
    }

    public void printFilesList() throws IOException, ClassNotFoundException {
        readFilesListFromFile();
        System.out.println(filesInVault.size());
        System.out.println(filesInVault);

        for (LogFile element : filesInVault) {
            //element.getFileToProcess().printAllProperties();
            System.out.println(element.getFileToProcess());
        }
    }

}
