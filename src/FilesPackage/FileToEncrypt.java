package FilesPackage;

import java.io.IOException;

public class FileToEncrypt extends FileToProcess {
    private String destinationPath;

    public FileToEncrypt(String filePathFromUser, String destinationPath) throws IOException {
        super(filePathFromUser);
        this.destinationPath = destinationPath;
    }

    public String getDestinationPath() {
        return destinationPath;
    }
}
