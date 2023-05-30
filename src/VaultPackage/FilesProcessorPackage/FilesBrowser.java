package VaultPackage.FilesProcessorPackage;

import javax.swing.*;
import java.io.File;

public class FilesBrowser {
    public String browseFile() {
        JFrame frame = new JFrame();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(frame);
        File selectedFile = null;
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            //System.out.println("Selected file: " + selectedFile.getAbsolutePath());
        }
        if (selectedFile != null) {
            return selectedFile.getAbsolutePath();
        } else {
            return "";
        }
    }

    public String[] browseFiles() {
        JFrame frame = new JFrame();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(frame);
        File[] selectedFiles = null;
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFiles = fileChooser.getSelectedFiles();
            //System.out.println("Selected files: " + Arrays.toString(selectedFiles));
        }
        if (selectedFiles != null && selectedFiles.length > 0) {
            String[] filePaths = new String[selectedFiles.length];
            for (int i = 0; i < selectedFiles.length; i++) {
                filePaths[i] = selectedFiles[i].getAbsolutePath();
            }
            return filePaths;
        } else {
            return new String[0];
        }
    }

}


