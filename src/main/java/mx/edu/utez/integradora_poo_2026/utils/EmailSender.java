package mx.edu.utez.integradora_poo_2026.utils;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.InputStream;
import java.util.Properties;

public class EmailSender {

    public static void sendMail(String to, String subject, String body) {
        // 1. Configuración del servidor SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Variables temporales para la lógica
        String userTemp = System.getenv("SMTP_USER");
        String passTemp = System.getenv("SMTP_PASS");

        if (userTemp == null || passTemp == null) {
            System.err.println("Advertencia: Variables de entorno no encontradas. Buscando en credentials.properties...");
            Properties creds = new Properties();
            try (InputStream is = EmailSender.class.getClassLoader().getResourceAsStream("credentials.properties")) {
                if (is == null) {
                    throw new RuntimeException("No se encontró el archivo credentials.properties ni las variables de entorno.");
                }
                creds.load(is);
                userTemp = creds.getProperty("smtp.user");
                passTemp = creds.getProperty("smtp.pass");
            } catch (Exception e) {
                throw new RuntimeException("Error al cargar las credenciales: " + e.getMessage());
            }
        }

        // 2. Credenciales DEFINITIVAS y FINALES
        final String usuario = userTemp;
        final String contrasena = passTemp;

        // 3. Crear la sesión
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, contrasena);
            }
        });

        try {
            // 4. Crear el mensaje
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(usuario));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setContent(body, "text/html; charset=utf-8");

            // 5. Enviar
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al enviar el correo: " + e.getMessage());
        }
    }
}