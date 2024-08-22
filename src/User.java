import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class User implements Serializable {

    private String username;
    private String password;
    private static List<User> registeredUsers = new ArrayList<>();

    // Constructor
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        registeredUsers.add(this); // Add the newly created user to the list
    }

    public static User findUser(String username) {
        for (User user : registeredUsers) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    // Call this method when the user completes their first purchase
    public void setHasPurchased(boolean hasPurchased) {
    }

    public static boolean isUserRegistered(String username) {
        return findUser(username) != null;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Save registered users to a file
    public static void saveUsersToFile(String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(registeredUsers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load registered users from a file
    @SuppressWarnings("unchecked")
    public static void loadUsersFromFile(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            registeredUsers = (List<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

