package FilesPackage;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;

public class FileToProcess implements Serializable {
    private BasicFileAttributes fileAttributes;
    private FileTime fileCreationTime;
    private FileTime fileModifiedTime;
    private String stringFileCreationTime;
    private String stringFileModifiedTime;
    private Path absoluteFilePath;
    private File file;
    private String fileSize;
    private String fileName;

    private String fileExtension;

    public FileToProcess(String filePathFromUser) throws IOException {
        file = new File(filePathFromUser);
        absoluteFilePath = Paths.get(file.getAbsolutePath());
        fileAttributes = Files.getFileAttributeView(absoluteFilePath, BasicFileAttributeView.class).readAttributes();
        fileCreationTime = fileAttributes.creationTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        stringFileCreationTime = simpleDateFormat.format((fileCreationTime.toMillis()));
        fileModifiedTime = fileAttributes.lastModifiedTime();
        stringFileModifiedTime = simpleDateFormat.format((fileModifiedTime.toMillis()));
        fileSize = String.valueOf(fileAttributes.size());
        fileName = file.getName();

    }

    public BasicFileAttributes getFileAttributes() {
        return fileAttributes;
    }

    public String getStringFileCreationTime() {
        return stringFileCreationTime;
    }
    public String getStringFileModifiedTime() {
        return stringFileModifiedTime;
    }
    public File getFile() {
        return file;
    }

    public Path getAbsoluteFilePath() {
        return absoluteFilePath;
    }

    public FileTime getFileCreationTime() {
        return fileCreationTime;
    }

    public FileTime getFileModifiedTime() {
        return fileModifiedTime;
    }

    public String getFileSize() {
        return fileSize;
    }

    public String getFileName() {
        return fileName;
    }
    public String getFileExtension(){
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1) {
            return "";
        } else {
            return fileName.substring(dotIndex + 1);
        }
    }
}
