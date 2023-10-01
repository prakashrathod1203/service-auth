package in.om.component;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import in.om.payload.Mail;
import in.om.security.JwtUtil;
import in.om.services.OLDUserService;
import in.om.socket.config.SocketSessionRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class Notification {


	@Value("${mail.username}")
	private  String mailSmtpUserName;
	@Value("${app.currentAppUrl}")
	private  String appURL;
	private final JavaMailSender mailSender;
	private final Configuration freeMarkerConfiguration;
	private final JwtUtil tokenProvider;
	private final SocketSessionRegistry webAgentSessionRegistry;
	private final SimpMessagingTemplate simpMessagingTemplate;
	private final OLDUserService userService;

    public void sendMail(SimpleMailMessage simpleMailMessage) throws MailException {
        mailSender.send(simpleMailMessage);
    }
    
    private void sendMail(Mail mail) throws MessagingException, IOException, TemplateException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        //helper.addAttachment("logo.png", new ClassPathResource("memorynotfound-logo.png"));

        Template t = freeMarkerConfiguration.getTemplate(mail.getTemplateName());
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, mail.getModel());
        helper.setTo(mail.getTo());
        helper.setText(html, true);
        helper.setSubject(mail.getSubject());
        helper.setFrom(mailSmtpUserName);
        mailSender.send(message);
    }
    
    public void sendChangePasswordMail(String userName) {
		//Create reset URL
		StringBuilder resetUrl = new StringBuilder(appURL);
		String token = tokenProvider.generateToken(null);
		resetUrl.append("?v=").append(token);
		
		//Mail Content
		String subject = "Reset your password";
		Map<String, Object> model = new HashMap<>();
        model.put("fromMail", mailSmtpUserName);
        model.put("userName", userName);
        model.put("resetUrl", resetUrl.toString());
        model.put("subject", subject);
        model.put("toMail", userName);
		Mail mail = new Mail(userName, subject, "reset-password-template.ftl", model);
		try {
			sendMail(mail);
			log.info("Reset Password mail send successfully");
		} catch (MessagingException | IOException | TemplateException e) {
			log.info("Reset Password mail send fail");
		}
	}

	@Async
    public void sendNewRegistrationMail(String userName, String fullName) {
		//Create reset URL
		StringBuilder resetUrl = new StringBuilder(appURL);
		String token = tokenProvider.generateToken(null);
		resetUrl.append("?v=").append(token);
		
		//Mail Content
		String subject = "Invited to new registration";
		Map<String, Object> model = new HashMap<>();
        model.put("fromMail", mailSmtpUserName);
        model.put("userName", userName);
        model.put("resetUrl", resetUrl.toString());
        model.put("subject", subject);
        model.put("toMail", userName);
        model.put("fullName", fullName);
		Mail mail = new Mail(userName, subject, "new-registration-template.ftl", model);
		try {
			sendMail(mail);
			log.info("New Registration mail send successfully");
		} catch (MessagingException | IOException | TemplateException e) {
			log.info("New Registration mail send fail");
		}
	}
}
