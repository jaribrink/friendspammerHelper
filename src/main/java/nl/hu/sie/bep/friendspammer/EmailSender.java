package nl.hu.sie.bep.friendspammer;

import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
    private static Logger logger = Logger.getLogger(EmailSender.class.getName());

    private EmailSender() {
    }

    public static void sendEmail(String subject, String to, String messageBody, boolean asHtml, String username, String password) throws MessagingException {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.mailtrap.io");
        props.put("mail.smtp.port", "2525");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("spammer@spammer.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(subject);

            if (asHtml) {
                message.setContent(messageBody, "text/html; charset=utf-8");
            } else {
                message.setText(messageBody);
            }
            Transport.send(message);

            MongoSaver.saveEmail(to, "spammer@spamer.com", subject, messageBody, asHtml, username, password);


        } catch (MessagingException e) {
            throw new MessagingException(e.toString());
        }
    }

    public static void sendEmail(String subject, String[] toList, String messageBody, boolean asHtml, String username, String password) throws MessagingException {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.mailtrap.io");
        props.put("mail.smtp.port", "2525");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {

            for (int index = 0; index < toList.length; index++) {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("spammer@spammer.com"));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(toList[index]));
                message.setSubject(subject);

                if (asHtml) {
                    message.setContent(messageBody, "text/html; charset=utf-8");
                } else {
                    message.setText(messageBody);
                }
                Transport.send(message);

                logger.config("Done");
            }

        } catch (MessagingException e) {
            throw new MessagingException(e.toString());
        }
    }
}
