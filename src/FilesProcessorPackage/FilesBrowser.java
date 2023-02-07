package FilesProcessorPackage;

import javax.swing.*;
import java.io.File;

public class FilesBrowser {
    public String browseFile() {
        DialogB frame = new DialogB();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setFileSelectionMode(fileChooser.FILES_AND_DIRECTORIES);
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

    public class DialogB extends JFrame {
    }
}


