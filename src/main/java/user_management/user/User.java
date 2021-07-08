/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user_management.user;

/**
 *
 * @author chiuy
 */
public class User {
    private String username;
    private String password;
    private String lastName;
    private String firstName;
    private String email;
    private String bio;
    private String avatarURL;
    private boolean isBanned;
    
    public User(){}

    public User(String username, String password, String lastName, String firstName, String email, String bio, String avatarURL, boolean isBanned) {
        this.username = username;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.bio = bio;
        this.avatarURL = avatarURL;
        this.isBanned = isBanned;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public boolean isIsBanned() {
        return isBanned;
    }

    public void setIsBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
    

    @Override
    public String toString() {
        return "User{" + "username=" + username + ", password=" + password + ", lastName=" + lastName + ", firstName=" + firstName + ", email=" + email + ", avatarURL=" + avatarURL + ", isBanned=" + isBanned + '}';
    }
    
    
    
   
}
