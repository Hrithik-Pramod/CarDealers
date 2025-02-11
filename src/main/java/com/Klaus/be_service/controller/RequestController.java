package com.Klaus.be_service.controller;

import com.Klaus.be_service.model.Request;
import com.Klaus.be_service.model.Request2;
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
import java.util.Objects;


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
    @Value("${email.const}")
    String toconst;

    @Value("${email.post}")
    String toPost;

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


    @PostMapping("/enquiry3")
    public String enquiry3(
            @RequestBody(required = true) Request2 request) throws IOException {
        try {
            String temp = "email-templateBuy";


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
            templateModel.put("type", request.getType());
            if (Objects.equals(request.getType(), "Rent")) {
                templateModel.put("postalCode", request.getPostalCode());
                templateModel.put("startDate", request.getStartDate());
                templateModel.put("endDate", request.getEndDate());
                temp = "email-templateRent";
            }

//            templateModel.put("base64Logo", base64Logo);

            emailService.sendHtmlMessage(
                    toconst, // Admin email
                    "Inquiry!",
                    temp,  // Thymeleaf template name (without .html)
                    templateModel
            );
            return "Inquiry sent!\n" +
                    "You’ll hear from us soon!";
        } catch (MessagingException E) {
            return "Failed to send inquiry: " + E.getLocalizedMessage();
        }

    }

    @PostMapping("/enquiry4")
    public String enquiry4(
            @RequestBody(required = true) Request2 request) throws IOException {
        try {
            String temp = "email-templatePost";


//            ClassPathResource imageResource = new ClassPathResource(logoPath);
//
//            // Read image bytes using plain Java
//            byte[] imageBytes = readImageAsBytes(imageResource.getInputStream());
//            String base64Logo = Base64.getEncoder().encodeToString(imageBytes);
            Map<String, Object> templateModel = new HashMap<>();
            templateModel.put("name", request.getName());
            templateModel.put("phone", request.getPhoneNumber());
            templateModel.put("email", request.getEmailId());
            templateModel.put("post", request.getPost());
            templateModel.put("salary", request.getSalary());

//            templateModel.put("base64Logo", base64Logo);

            emailService.sendHtmlMessage(
                    toPost, // Admin email
                    "Inquiry!",
                    temp,  // Thymeleaf template name (without .html)
                    templateModel
            );
            return "Inquiry sent!\n" +
                    "You’ll hear from us soon!";
        } catch (MessagingException E) {
            return "Failed to send inquiry: " + E.getLocalizedMessage();
        }

    }

    @PostMapping("/enquiry5")
    public String enquiry5(
            @RequestBody(required = true) Request2 request) throws IOException {
        try {
            String temp = "email-templateService";


//            ClassPathResource imageResource = new ClassPathResource(logoPath);
//
//            // Read image bytes using plain Java
//            byte[] imageBytes = readImageAsBytes(imageResource.getInputStream());
//            String base64Logo = Base64.getEncoder().encodeToString(imageBytes);
            Map<String, Object> templateModel = new HashMap<>();
            templateModel.put("name", request.getName());
            templateModel.put("phone", request.getPhoneNumber());
            templateModel.put("email", request.getEmailId());
            templateModel.put("reason", request.getReason());
            templateModel.put("description", request.getDescription());

//            templateModel.put("base64Logo", base64Logo);

            emailService.sendHtmlMessage(
                    toPost, // Admin email
                    "Inquiry!",
                    temp,  // Thymeleaf template name (without .html)
                    templateModel
            );
            return "Inquiry sent!\n" +
                    "You’ll hear from us soon!";
        } catch (MessagingException E) {
            return "Failed to send inquiry: " + E.getLocalizedMessage();
        }

    }
}
