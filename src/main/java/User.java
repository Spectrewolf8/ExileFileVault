import VaultPackage.FilesProcessorPackage.LogFile;
import VaultPackage.HashPackage.SHA512_HashGenerator;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private String username;
    private String passwordHash;

    private List<LogFile> userFilesInVault;

    public User(String username, String password) {
        this.username = username;
        this.passwordHash = SHA512_HashGenerator.generateHash(password);
    }

    public void addFileToVault(LogFile file) {
        userFilesInVault.add(file);
    }

    public void removeFileFromVault(int index) {
        userFilesInVault.remove(index);
    }


    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}
