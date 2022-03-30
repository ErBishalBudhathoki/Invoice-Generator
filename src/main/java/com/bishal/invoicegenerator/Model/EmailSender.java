package com.bishal.invoicegenerator.Model;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

/**
 * This class is used to send email with attachment.
 * @author w3spoint
 */
public class EmailSender {
    //    final String senderEmailId = "deverbishal331@gmail.com";
//    final String senderPassword = "bishal.xiomi";
//    final String emailSMTPserver = "smtp.gmail.com";
//    final String emailSMTPPort = "587";
    public void emailSender(String pswd, String updatedInvID) {

        String to = "bishalkc331@gmail.com";//change accordingly
        final String user = "deverbishal331@gmail.com";//change accordingly
        final String password = pswd;//change accordingly

        //1) get the session object
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, password);
                    }
                });
        String subject = "Invoice Number: " + updatedInvID;
        String bodyMsg = "Hello,\n" +
                "\n" +
                "I have attached the latest invoice for the pay period ending " + updatedInvID + ".\n" +
                "\n" +
                "Thank you,\n" +
                "\n" +
                "Regards,\n" +
                "Pratiksha Tiwari";
        //2) compose message
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);

            //3) create MimeBodyPart object and set your message text
            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setText(bodyMsg);

            //4) create new MimeBodyPart object and set DataHandler object to this object
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();

            String filename = "Invoice.pdf";//change accordingly
            DataSource source = new FileDataSource(filename);
            messageBodyPart2.setDataHandler(new DataHandler(source));
            messageBodyPart2.setFileName(filename);


            //5) create Multipart object and add MimeBodyPart objects to this object
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart1);
            multipart.addBodyPart(messageBodyPart2);

            //6) set the multiplart object to the message object
            message.setContent(multipart);

            //7) send message
            Transport.send(message);


        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }
}