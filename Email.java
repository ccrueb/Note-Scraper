import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.Scanner;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Email {
	private static Scanner in = null;
	private String pdf;
	private static String username;
	private static String password;
	private static String printer;
	
	public Email(String pdf) {
		
		
		this.pdf = pdf;
	}
	
	public boolean send() {
		
		  Properties props = new Properties();
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.host", "smtp.gmail.com");
	        props.put("mail.smtp.port", "587");

	        Session session = Session.getInstance(props,
	          new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(username, password);
	            }
	          });

	        try {
	            
	            Message message = new MimeMessage(session);
	            Multipart multipart = new MimeMultipart();
	            
	            MimeBodyPart messageBodyPart = new MimeBodyPart();
	            messageBodyPart.setContent(message, "text/html");
	            
	            MimeBodyPart attachPart = new MimeBodyPart();
	            try {
	                attachPart.attachFile(this.pdf);
	            } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	            multipart.addBodyPart(attachPart);
	            message.setFrom(new InternetAddress(username));
	            message.setRecipients(Message.RecipientType.TO,
	                InternetAddress.parse(printer));
	            message.setSubject("Attachment Test");
	            message.setText("Dear Mail Crawler,"
	                + "\n\n No spam to my email, please!");
	            
	            //Attachment
	          
	            message.setContent(multipart);
	            Transport.send(message);

	            System.out.println("Done");

	        } catch (MessagingException e) {
	            throw new RuntimeException(e);
	        }
		
		return true;	
	}
	
	//for added security email credentials are stored in seperate txt file
	public static void readInEmailInfo() {
		File file = new File("credentials.txt");
		try {
			in = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		username = in.nextLine();
		password = in.nextLine();
		printer = in.nextLine();
	}
}
