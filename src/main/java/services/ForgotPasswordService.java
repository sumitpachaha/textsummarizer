package services;

import java.awt.Color;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

import org.springframework.stereotype.Service;

@Service
public class ForgotPasswordService {

	public String sendMail(String email,String pass)
	{
		String to=email;
		String from="diplove32928@gmail.com";
		
		try{
				String fetchedPassword =pass;  
				final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
				
				Properties props = System.getProperties();
				props.setProperty("mail.smtp.host", "smtp.gmail.com");
				props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
			    props.setProperty("mail.smtp.socketFactory.fallback", "false");
			    props.setProperty("mail.smtp.port", "465");
			    props.setProperty("mail.smtp.socketFactory.port", "465");
			    props.put("mail.smtp.auth", "true");
			   //  props.put("mail.debug", "true");
			    props.put("mail.store.protocol", "pop3");
			    props.put("mail.transport.protocol", "smtp");
			    final String username = "diplove32928@gmail.com";//
			    final String password = "dahachour";
			    Session session = Session.getDefaultInstance(props, 
			                  new Authenticator(){
			                     protected PasswordAuthentication getPasswordAuthentication() {
			                     return new PasswordAuthentication(username, password);
					              }});

			   // -- Create a new message --
			     Message msg = new MimeMessage(session);

			  // -- Set the FROM and TO fields --
			     msg.setFrom(new InternetAddress(from));
			     msg.setRecipients(Message.RecipientType.TO, 
			                      InternetAddress.parse(to,false));
			     msg.setSubject("Password for your account is");
			     msg.setText(fetchedPassword);
			     msg.setSentDate(new Date());
			     Transport.send(msg);
			     System.out.println("Message Send Successfully");
			     return "Password was send successfully, please check your email";
			}catch(Exception e)
			{
				 return ("Sorry email can't sent, Try Again: Error "+e);
			}
		  
	}
}

