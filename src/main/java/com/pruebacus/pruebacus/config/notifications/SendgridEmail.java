package com.pruebacus.pruebacus.config.notifications;
import com.sendgrid.*;
import java.io.IOException;

import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SendgridEmail {

    @Value("${sendgrid.SENDGRID_API_KEY}")
    private String sengridApiKey;

    @Value("${sendgrid.from-address}")
    private String fromAddress;


    public void sendEmail (String subject, String toAdress, String message) throws IOException {
        Email from = new Email(fromAddress);
        Email to = new Email(toAdress);
        Content content = new Content("text/plain", message);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(sengridApiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
        } catch (IOException ex) {
            throw ex;
        }

    }


}
