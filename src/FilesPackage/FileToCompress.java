package FilesPackage;

import java.io.IOException;

public class FileToCompress extends FileToProcess {
    private String destinationPath;

    public FileToCompress(String filePathFromUser, String destinationPath) throws IOException {
        super(filePathFromUser);
        this.destinationPath = destinationPath;
    }

    public String getDestinationPath() {
        return destinationPath;
    }

}
