package utils;



import java.util.ArrayList;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author DELL
 */
public class EmailSender {

    static Session newSession = null;
    static MimeMessage mimeMessage = null;
    static final String fromEmail = "swpacckhoi@gmail.com";
    static final String fromPassword = "Swpacc1!";

    private static void SetupServerProperties() {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "mail.smtp.host");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        newSession = Session.getDefaultInstance(properties, null);
    }

    private static MimeMessage DraftEmail(ArrayList<String> receipientEmails, 
            String emailSubject, String emailBody) throws MessagingException {
        mimeMessage = new MimeMessage(newSession);
        for (String receipientEmail : receipientEmails) {
            mimeMessage.addRecipient(Message.RecipientType.TO, 
                                    new InternetAddress(receipientEmail));
//            System.err.println("DraftEmail "+ receipientEmail);
        }
        mimeMessage.setSubject(emailSubject);
        MimeMultipart multiPart = new MimeMultipart();
        //text part of the email body
        MimeBodyPart textBodyPart = new MimeBodyPart();
        textBodyPart.setText(emailBody);
        //attachment part of the email body
//        MimeBodyPart attachmentPart= new MimeBodyPart();
//        attachmentPart.attachFile("// file address //");
//        multiPart.addBodyPart(attachmentPart);
        
        multiPart.addBodyPart(textBodyPart);
        mimeMessage.setContent(multiPart);
        return mimeMessage;
    }

    private static void SendEmail() throws NoSuchProviderException, MessagingException {
        String emailHost = "smtp.gmail.com";
        Transport transport = newSession.getTransport("smtp");
        transport.connect(emailHost, fromEmail, fromPassword);
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        transport.close();
    }

    public static void SendEmail(ArrayList<String> receipientEmails, 
            String emailSubject, String emailBody) throws MessagingException{
        SetupServerProperties();
        DraftEmail(receipientEmails, emailSubject, emailBody);
        SendEmail();
    }
}

    
    

    
    
    
  
