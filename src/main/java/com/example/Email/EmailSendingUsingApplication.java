package com.example.Email;

import java.io.File;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmailSendingUsingApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailSendingUsingApplication.class, args);

		System.out.println("Hello World");

		String Message = " Hi Sreenivas,\n" + "\n"
				+ "We would like to inform ,charans's Purchase Order End Date is going to Complete on (poedate)\n"
				+ "Thank you,\n" + "Team Lancesoft";

		String Subject = "Remainder of Purchace Order End Date ";

		String to = "kondabala.s@lancesoft.com";

		String from = "sudheer.beesamalla@gmail.com";

		// sendEmail(Message, Subject, to, from);
		sendAttach(Message, Subject, to, from);

	}

//This is responsible to send mail
	private static void sendEmail(String message, String subject, String to, String from) {

		// variable
		String host = "smtp.gmail.com"; // This is a server

		Properties properties = System.getProperties();
		System.out.println("properties" + properties);

		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

		// step1 get the session Object

		Session session = Session.getDefaultInstance(properties, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, "hlhvbsjfkqspkyqf");
			}
		});

		// session.setDebug(true);

		// step2 compose text,multi-media(audio,image,attachment..etc)
		MimeMessage mimeMessage = new MimeMessage(session);
		try {
			mimeMessage.setFrom(from);
			mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			mimeMessage.setSubject(subject);
			mimeMessage.setText(message);
			Transport.send(mimeMessage);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void sendAttach(String message, String subject, String to, String from) {

		// Variable for gmail
		String host = "smtp.gmail.com";

		// get the system properties
		Properties properties = System.getProperties();
		System.out.println("PROPERTIES " + properties);

		// setting important information to properties object

		// host set
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

		// Step 1: to get the session object..
		Session session = Session.getDefaultInstance(properties, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, "hlhvbsjfkqspkyqf");
			}
		});

		session.setDebug(true);

		// Step 2 : compose the message [text,multi media]
		MimeMessage m = new MimeMessage(session);

		try {

			// from email
			m.setFrom(from);

			// adding recipient to message
			m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// adding subject to message
			m.setSubject(subject);

			// attachement..

			// file path

			String path = "C:\\Users\\Sudheer\\OneDrive - LanceSoft\\Pictures\\et12.png";

			MimeMultipart mimeMultipart = new MimeMultipart();
			// text
			// file

			MimeBodyPart textMime = new MimeBodyPart();

			MimeBodyPart fileMime = new MimeBodyPart();

			try {

				textMime.setText(message);

				File file = new File(path);
				fileMime.attachFile(file);

				mimeMultipart.addBodyPart(textMime);
				mimeMultipart.addBodyPart(fileMime);

			} catch (Exception e) {

				e.printStackTrace();
			}

			m.setContent(mimeMultipart);

			// send

			// Step 3 : send the message using Transport class
			Transport.send(m);

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Sent success...................");

	}

}
