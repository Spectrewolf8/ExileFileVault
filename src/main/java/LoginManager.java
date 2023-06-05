import VaultPackage.HashPackage.SHA512_HashGenerator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LoginManager {
    private static final String USERS_FILE = "bin/users.bin";

    private List<User> users;
    private User currentUser;
    private String currentUserPassword;

    public LoginManager() {
        users = new ArrayList<>();
        currentUser = null;
        loadUsersFromFile();
    }

    public boolean login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPasswordHash().equals(SHA512_HashGenerator.generateHash(password))) {
                currentUser = user;
                return true;
            }
        }
        return false;
    }

    public void logout() {
        currentUser = null;
    }

    public boolean createUser(String username, String password) {
        if (getUserByUsername(username) == null) {
            User newUser = new User(username, password);
            users.add(newUser);
            saveUsersToFile();
            return true;
        }
        return false;
    }

    public boolean deleteUser(String username) {
        User userToDelete = getUserByUsername(username);
        if (userToDelete != null) {
            users.remove(userToDelete);
            saveUsersToFile();
            return true;
        }
        return false;
    }

    private User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    private void loadUsersFromFile() {
        try {
            FileInputStream fileIn = new FileInputStream(USERS_FILE);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            users = (List<User>) in.readObject();

            in.close();
            fileIn.close();
        } catch (EOFException e){
            System.out.println("Skipping reading since file is empty");
        }catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading users from file: " + e.getMessage());
        }
    }

    private void saveUsersToFile() {
        try {
            FileOutputStream fileOut = new FileOutputStream(USERS_FILE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(users);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            System.out.println("Error saving users to file: " + e.getMessage());
        }
    }
}
