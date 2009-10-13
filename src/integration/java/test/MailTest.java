package test;

import java.util.Iterator;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;

public class MailTest extends BaseTransactionalTests {
	
	private static final String EMAIL_FROM = "noreply@truedolist.com";
	private static final String EMAIL_TO = "test@test.com";
	private static final String EMAIL_SUBJECT = "The subject of the e-mail";
	private static final int SMTP_PORT = 2000;
	MailSender mailSender;
	
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	@Override
	protected void onSetUp() throws Exception {
		((JavaMailSenderImpl) mailSender).setPort(SMTP_PORT);
		super.onSetUp();
	}
	
	@SuppressWarnings("unchecked")
	public void testSendingMail() {
		SimpleSmtpServer server = SimpleSmtpServer.start(SMTP_PORT);
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(EMAIL_TO);
		message.setFrom(EMAIL_FROM);
		message.setSubject(EMAIL_SUBJECT);
		message.setText("Mail text - this is the body!");
		
		mailSender.send(message);
		
		server.stop();
		
		Iterator<SmtpMessage> receivedEmail = server.getReceivedEmail();
		SmtpMessage smtpMessage = (SmtpMessage) receivedEmail.next();
		assertTrue(smtpMessage.getHeaderValue("Subject").equals(EMAIL_SUBJECT));
		assertTrue(smtpMessage.getHeaderValue("From").equals(EMAIL_FROM));
		assertTrue(smtpMessage.getHeaderValue("To").equals(EMAIL_TO));
		System.out.println(smtpMessage.getBody());
	}
	
}
