package com.Klaus.be_service.controller;

import com.Klaus.be_service.model.Request;
import com.Klaus.be_service.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


@RestController()
@RequestMapping("/client")
@CrossOrigin
public class RequestController {
    @Autowired
    EmailService emailService;
    @Value("${email.to}")
    String to;
    @Value("${email.benz}")
    String tobenz;

    @PostMapping("/enquiry")
    public String enquiry(
            @RequestBody(required = true) Request request) throws IOException {
        try {

//            ClassPathResource imageResource = new ClassPathResource(logoPath);
//
//            // Read image bytes using plain Java
//            byte[] imageBytes = readImageAsBytes(imageResource.getInputStream());
//            String base64Logo = Base64.getEncoder().encodeToString(imageBytes);
            Map<String, Object> templateModel = new HashMap<>();
            templateModel.put("name", request.getName());
            templateModel.put("phone", request.getPhoneNumber());
            templateModel.put("email", request.getEmailId());
            templateModel.put("model", request.getModel());
            templateModel.put("price", request.getPrice());
//            templateModel.put("base64Logo", base64Logo);

            emailService.sendHtmlMessage(
                    to, // Admin email
                    "New Car Inquiry",
                    "email-template",  // Thymeleaf template name (without .html)
                    templateModel
            );
            return "Inquiry sent!\n" +
                    "You’ll hear from us soon!";
        } catch (MessagingException E) {
            return "Failed to send inquiry: " + E.getLocalizedMessage();
        }

    }

    public byte[] readImageAsBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return buffer.toByteArray();
    }

    @PostMapping("/enquiry2")
    public String enquiry2(
            @RequestBody(required = true) Request request) throws IOException {
        try {

//            ClassPathResource imageResource = new ClassPathResource(logoPath);
//
//            // Read image bytes using plain Java
//            byte[] imageBytes = readImageAsBytes(imageResource.getInputStream());
//            String base64Logo = Base64.getEncoder().encodeToString(imageBytes);
            Map<String, Object> templateModel = new HashMap<>();
            templateModel.put("name", request.getName());
            templateModel.put("phone", request.getPhoneNumber());
            templateModel.put("email", request.getEmailId());
            templateModel.put("model", request.getModel());
            templateModel.put("price", request.getPrice());
//            templateModel.put("base64Logo", base64Logo);

            emailService.sendHtmlMessage(
                    tobenz, // Admin email
                    "New Car Inquiry",
                    "email-template1",  // Thymeleaf template name (without .html)
                    templateModel
            );
            return "Inquiry sent!\n" +
                    "You’ll hear from us soon!";
        } catch (MessagingException E) {
            return "Failed to send inquiry: " + E.getLocalizedMessage();
        }

    }

}
