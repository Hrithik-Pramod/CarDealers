package com.Klaus.be_service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;

@Service
public class MailSchedulerService {
    @Value("${jasper.filePath}")
    private String jasperFilePath;

    @Autowired
    private JavaMailSender mailSender;


    // Limit to avoid exceeding Gmail's sending limit
    private static final int EMAILS_PER_BATCH = 100;
    private static final long DELAY_BETWEEN_EMAILS_MS = 300000; // Delay between each email (e.g., 2 seconds)
    private static final long DELAY_BETWEEN_BATCHES_MS = 24 * 60 * 60 * 1000;


    // One-time scheduler: Schedule the task to run once on application startup
    @Scheduled(initialDelay = 5000, fixedRate = Long.MAX_VALUE)
    public void sendEmails() {
        try (BufferedReader br = new BufferedReader(new FileReader(jasperFilePath))) {
            String email;
            int emailsSent = 0;

            while ((email = br.readLine()) != null) {
                try {
                    sendHtmlEmail(email);
                    System.out.println("Email sent to: " + email);
                    emailsSent++;

                    // Throttle emails to avoid Gmail limits
                    Thread.sleep(DELAY_BETWEEN_EMAILS_MS);

                    // Limit emails per batch
                    if (emailsSent % EMAILS_PER_BATCH == 0) {
                        System.out.println("Batch limit reached, waiting before sending next batch...");
                        Thread.sleep(DELAY_BETWEEN_BATCHES_MS);
                    }

                } catch (Exception e) {
                    System.out.println("Failed to send email to: " + email + ". Error: " + e.getMessage());
                    // Implement retry with backoff if required
                    handleFailedEmail(email, e);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading email file or interruption: " + e.getMessage());
        }
    }

    private void sendHtmlEmail(String toEmail) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(toEmail);
        helper.setSubject("Besonderes Angebot für Geschäftskunden");

        helper.setFrom(new InternetAddress("kundendienst@mercedes-krg.de", "no-reply@mercedes-krg.de"));

        // Set the HTML content
        String htmlContent = "<style>\n" +
                "    @media (min-width: 768px) {\n" +
                "        .car-info-container {\n" +
                "            display: flex;\n" +
                "            justify-content: space-between;\n" +
                "            align-items: center;\n" +
                "        }\n" +
                "        .car-info-text {\n" +
                "            flex: 1;\n" +
                "            padding-right: 20px;\n" +
                "        }\n" +
                "        .car-info-image {\n" +
                "            flex: 0 0 125px; /* Set width to a small size for desktop */\n" +
                "            text-align: right;\n" +
                "        }\n" +
                "        .car-info-image img {\n" +
                "            width: 125px; /* Small image size for desktop */\n" +
                "            border-radius: 8px;\n" +
                "        }\n" +
                "    }\n" +
                "</style>\n" +
                "\n" +
                "<div style=\"font-family: Arial, sans-serif;\">\n" +
                "    <div style=\"background-color: #333; color: #fff; padding: 20px; text-align: center;\">\n" +
                "        <h2>Besonderes Angebot für Geschäftskunden</h2>\n" +
                "    </div>\n" +
                "    <div style=\"padding: 20px;\">\n" +
                "        <p>Sehr geehrte Damen und Herren,</p>\n" +
                "        <p>wir freuen uns, Ihnen ein spezielles Angebot für Geschäftskunden anzukündigen. Profitieren Sie von günstigen Konditionen auf unser Sortiment an hochwertigen Gebrauchtwagen.</p>\n" +
                "        <p>Unser Angebot umfasst eine breite Auswahl an Marken und Modellen, die ideal für den Wiederverkauf geeignet sind.</p>\n" +
                "        <p>Sehen Sie sich unsere aktuellen Fahrzeuge an – klicken Sie auf den Anhang, um den aktuellen Fahrzeugkatalog herunterzuladen und Ihr Wunschauto zu finden!</p>\n" +
                "        \n" +
                "        <div class=\"car-info-container\" style=\"background-color: #444; color: #fff; padding: 10px; margin: 20px 0;\">\n" +
                "            <div class=\"car-info-text\">\n" +
                "                <h3>Mercedes-Benz CLA 35 AMG</h3>\n" +
                "                <p>Erstzulassung: 02/2022<br>\n" +
                "                   Kilometerstand: 41.100 km<br>\n" +
                "                   Leistung: 135 kW / 184 PS<br>\n" +
                "                   Getriebe: Automatik<br>\n" +
                "            </div>\n" +
                "            <div class=\"car-info-image\">\n" +
                "                <img src=\"cid:carImage\" alt=\"Mercedes-Benz CLA 35 AMG\">\n" +
                "                <h2 style=\"color: yellow; margin-top: 10px;\">52.789,00 €</h2>\n" +
                "                <p>inklusive Mehrwertsteuer</p>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "\n" +
                "        <p>Haben Sie bereits das geeignete Fahrzeug entdeckt? Bitte senden Sie Ihre Antwort mit der entsprechenden Angebotsnummer an <a href=\"mailto:kundendienst@mercedes-krg.de\" style=\"color: #ccc; text-decoration: none;\">kundendienst@mercedes-krg.de</a>.</p>\n" +
                "        <p>For foreign customers, we offer the same service in English. If you are interested, please reply to <a href=\"mailto:kundendienst@mercedes-krg.de\" style=\"color: #ccc; text-decoration: none;\">kundendienst@mercedes-krg.de</a>, and a service representative will contact you as soon as possible.</p>\n" +
                "    </div>\n" +
                "    <footer style=\"background-color: #333; color: #fff; padding: 10px; text-align: center;\">\n" +
                "        <p><a href=\"mailto:kundendienst@mercedes-krg.de\" style=\"color: #ccc; text-decoration: none;\">kundendienst@mercedes-krg.de</a> | <a href=\"https://mercedes-krg.de\" style=\"color: #ccc; text-decoration: none;\">mercedes-krg.de</a></p>\n" +
                "        <p>© 2024 KRG</p>\n" +
                "    </footer>\n" +
                "</div>\n";


        helper.setText(htmlContent, true);

        // Attach an image if needed
        helper.addInline("carImage", new File("D:/CarDealers/email/car.png"));

        helper.setReplyTo("kundendienst@mercedes-krg.de");

        // Replace with the actual image path

        //helper.addAttachment("krg catalogue.pdf", new File("D:/CarDealers/email/krg catalogue.pdf")); // Replace with the actual PDF path

        mailSender.send(message);
    }

    private void handleFailedEmail(String email, Exception e) {
        // Handle failed email logic here
        // Implement retry with exponential backoff if needed
        System.out.println("Handling failed email: " + email + ". Error: " + e.getMessage());
    }
}
