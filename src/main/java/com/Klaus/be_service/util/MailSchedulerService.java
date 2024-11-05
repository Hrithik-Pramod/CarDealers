package com.Klaus.be_service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Service
public class MailSchedulerService {
    @Value("${jasper.filePath}")
    private String jasperFilePath;

    @Autowired
    private JavaMailSender mailSender;

    // One-time scheduler: Schedule the task to run once on application startup
    @Scheduled(initialDelay = 5000, fixedRate = Long.MAX_VALUE)
    public void sendEmails() {
        try (BufferedReader br = new BufferedReader(new FileReader(jasperFilePath))) {
            String email;
            while ((email = br.readLine()) != null) {
                try {
                    sendHtmlEmail(email);
                    System.out.println("Email sent to: " + email);
                } catch (Exception e) {
                    System.out.println("Failed to send email to: " + email + ". Error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading email file: " + e.getMessage());
        }
    }

    private void sendHtmlEmail(String toEmail) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(toEmail);
        helper.setSubject("Besonderes Angebot für Geschäftskunden");

        // Set the HTML content
        String htmlContent = "<div style=\"font-family: Arial, sans-serif;\">\n" +
                "    <div style=\"background-color: #333; color: #fff; padding: 20px; text-align: center;\">\n" +
                "        <h2>Besonderes Angebot für Geschäftskunden</h2>\n" +
                "    </div>\n" +
                "    <div style=\"padding: 20px;\">\n" +
                "        <p>Sehr geehrte Damen und Herren,</p>\n" +
                "        <p>wir freuen uns, Ihnen ein spezielles Angebot für Geschäftskunden anzukündigen. Profitieren Sie von günstigen Konditionen auf unser Sortiment an hochwertigen Gebrauchtwagen.</p>\n" +
                "        <p>Unser Angebot umfasst eine breite Auswahl an Marken und Modellen, die ideal für den Wiederverkauf geeignet sind.</p>\n" +
                "        <p>Sehen Sie sich unsere aktuellen Fahrzeuge an – klicken Sie auf den Anhang, um den aktuellen Fahrzeugkatalog herunterzuladen und Ihr Wunschauto zu finden!</p>\n" +
                "        \n" +
                "        <div style=\"background-color: #444; color: #fff; padding: 10px; margin: 20px 0; display: flex; align-items: center;\">\n" +
                "            <div style=\"flex: 1;\">\n" +
                "                <h3>BMW 2er Coupé M</h3>\n" +
                "                <p>Erstzulassung: 02/2022<br>\n" +
                "                   Kilometerstand: 41.100 km<br>\n" +
                "                   Leistung: 135 kW / 184 PS<br>\n" +
                "                   Getriebe: Automatik<br>\n" +
                "                   Bestandsnummer: BMWS01</p>\n" +
                "            </div>\n" +
                "            <div style=\"text-align: right;\">\n" +
                "                <img src=\"cid:carImage\" alt=\"BMW 2er Coupé M\" style=\"width: 150px; border-radius: 8px;\">\n" +
                "                <h2 style=\"color: yellow;\">31.900,00 €</h2>\n" +
                "                <p>inklusive Mehrwertsteuer</p>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "\n" +
                "        <p>Haben Sie bereits das geeignete Fahrzeug entdeckt? Antworten Sie einfach auf diese E-Mail mit der entsprechenden Angebotsnummer.</p>\n" +
                "        <p>For foreign customers we offer the same service in English. Attached to the mail you will find our current vehicle catalog. If you are interested, please reply to this e-mail and a service representative will contact you as soon as possible.</p>\n" +
                "    </div>\n" +
                "    <footer style=\"background-color: #333; color: #fff; padding: 10px; text-align: center;\">\n" +
                "        <p>Besonderes Angebot für Gewerbliche Kunden</p>\n" +
                "        <p><a href=\"#\" style=\"color: #ccc; text-decoration: none;\">Datenschutz</a> | <a href=\"#\" style=\"color: #ccc; text-decoration: none;\">Impressum</a></p>\n" +
                "        <p>© 2024 KLAUS SCHELLER GMBH</p>\n" +
                "    </footer>\n" +
                "</div>\n";


        helper.setText(htmlContent, true);

        // Attach an image if needed
        helper.addInline("carImage", new File("D:/CarDealers/email/car.png"));

        // Replace with the actual image path

        helper.addAttachment("Klaus Scheller GmbH Katalog Herbst 2024 Ausgabe.pdf", new File("D:/CarDealers/email/Klaus Scheller GmbH Katalog Herbst 2024 Ausgabe.pdf")); // Replace with the actual PDF path

        mailSender.send(message);
    }
}
