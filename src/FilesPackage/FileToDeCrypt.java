package FilesPackage;

import java.io.IOException;

public class FileToDeCrypt extends FileToProcess {
    private String destinationPath;

    public FileToDeCrypt(String filePath, String destinationPath) throws IOException {
        super(filePath);
        this.destinationPath = destinationPath;
    }

    public String getDestinationPath() {
        return destinationPath;
    }
}
