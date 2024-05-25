package com.ceng316.ceng316_oims_backend.MailSender;

import com.ceng316.ceng316_oims_backend.Company.Company;
import com.ceng316.ceng316_oims_backend.Documents.Document;
import com.ceng316.ceng316_oims_backend.InternshipApplication.InternshipApplication;
import com.ceng316.ceng316_oims_backend.IztechUser.IztechUser;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailSenderService {

    private final JavaMailSender mailSender;
    private final Environment env;

    public void sendResetTokenEmail(HttpServletRequest request, String token, Company company) {
        mailSender.send(constructResetTokenEmail(getAppUrl(request), token, company));
    }

    public void sendInternshipApplicationEmail(InternshipApplication application) {
        IztechUser sender = application.getStudent();
        Company receiver = application.getAnnouncement().getCompany();

        Document applicationLetter = application.getApplicationLetter();
        mailSender.send(constructInternshipApplicationEmail(sender, receiver, applicationLetter));
    }

    private SimpleMailMessage constructResetTokenEmail(
            String contextPath, String token, Company company) {
        String url = contextPath + "/security/company/changePassword?token=" + token;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject("Reset Password");
        email.setText("Reset password \r\n" + url);
        email.setTo(company.getEmail());
        email.setFrom(env.getProperty("support.email"));

        return email;
    }

    private MimeMessage constructInternshipApplicationEmail(IztechUser sender, Company receiver, Document applicationLetter) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(receiver.getEmail());
            helper.setSubject("Internship Application");
            helper.setText("Internship application from " + sender.getFullName());
            helper.addAttachment("InternshipApplicationLetter.docx", new ByteArrayResource(applicationLetter.getContent()));
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return message;
    }


    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
