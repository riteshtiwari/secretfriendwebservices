package com.cgs.ws.rest;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class SendEmail {

	public static void main(String z[])
	{
		
		sendMail();
	}

	private static void sendMail() {

		System.out.println("called java mail");
		String host = "smtp.gmail.com";
	    String from = "s.mdasiq@gmail.com";
	    String to = "asiq.s@constient.com";
		try {
			System.out.println("properties");
	         // Get system properties
	         Properties props = System.getProperties();
	     
	         System.out.println("Authendication");
	         // Setup mail server
	         Authenticator auth = new PopupAuthenticator();
//	         props.put("mail.smtp.host", host);
	         
	         props.put("mail.smtp.auth", "true");
	 		 props.put("mail.smtp.starttls.enable", "true");
	 		 props.put("mail.smtp.host", "smtp.gmail.com");
	 		 props.put("mail.smtp.port", "587");
	     
	         System.out.println("session");
	         // Get session
	         Session session = Session.getInstance(props, auth);
	         //session.setDebug(true);
	     
	         System.out.println("Message");
	         // Define message
	         MimeMessage message = new MimeMessage(session);
	     
	         // Set the from address
	         message.setFrom(new InternetAddress(from,"FromName"));
	     
	         System.out.println("add receipient");
	         // Set the to address
	         message.addRecipient(Message.RecipientType.TO,new InternetAddress(to,"ToName"));
	     
	         // Set the subject
	         message.setSubject("Hello JavaMail");
	     
	         // Set the content
	         message.setText("Welcome to JavaMail");
	     
	         // Send message
	         System.out.println("proceed to send");
	         Transport.send(message);
	         System.out.println("Send Successfully");
	      }
	      catch (MessagingException e) {
	    	  e.toString();
	    	  }
	     catch (UnsupportedEncodingException e) {
	    	 e.toString();
	    	 }
	  }
	  
	  static class PopupAuthenticator extends Authenticator {
	        public PasswordAuthentication getPasswordAuthentication() {
	        	System.out.println("called Password Authendication");
	            return new PasswordAuthentication("s.mdasiq@gmail.com", "");
	        }
	    }
	}
