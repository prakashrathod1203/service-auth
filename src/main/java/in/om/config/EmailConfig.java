package in.om.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {

    @Value("${mail.host}")
    private String host;
    @Value("${mail.port}")
    private Integer port;
    @Value("${mail.username}")
    private String userName;
    @Value("${mail.password}")
    private String password;
    @Value("${mail.smtp.auth:true}")
    private Boolean isSmtpAuth;
    @Value("${mail.smtp.starttls.enable:true}")
    private Boolean isStarttlsEnable;
    @Value("${mail.debug:false}")
    private Boolean isDebugEnable;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(userName);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", isSmtpAuth);
        props.put("mail.smtp.starttls.enable", isStarttlsEnable);
        props.put("mail.debug", isDebugEnable);
        mailSender.setJavaMailProperties(props);

        return mailSender;
    }
}
