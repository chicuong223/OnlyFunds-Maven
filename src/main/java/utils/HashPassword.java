/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author chiuy
 */
public class HashPassword {

    public static String HashPassword(String password) {
        String hashedPassword = "";
        try {
            //create message digest instance of sha256
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            //get bytes of password, add them to md
            md.update(password.getBytes());
            //get hash's bytes
            byte[] bytes = md.digest();
            //convert to hex
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            hashedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashedPassword;
    }
   
}
