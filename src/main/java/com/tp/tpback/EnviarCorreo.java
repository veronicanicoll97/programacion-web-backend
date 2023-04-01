package com.tp.tpback;

import model.Cabecera;
import model.Cliente;
import model.Detalle;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class EnviarCorreo {

    public static void main(String[] args) throws FileNotFoundException {
        File file2 = new File("notificacion.html");
        System.out.println(file2.getAbsolutePath());
        System.out.println(file2.exists());
        //System.out.println(new File("main/PlantillaCorreos/notificacion.html").getAbsolutePath());
        String texto_html = PlantillasHTML.getCorreoHTML(file2.getAbsolutePath());
        System.out.println(texto_html);

        // Recipient's email ID needs to be mentioned.
        String to = "jesusg2kk@gmail.com";

        // Sender's email ID needs to be mentioned
        String from = "jesuscaceres2020@gmail.com";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("jesuscaceres2020@gmail.com", "axopynraluusmppp");

            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("This is the Subject Line!");

            // Now set the actual message
            //message.setText("This is actual message");
            message.setContent(
                    texto_html,
                    "text/html");
            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }


    public static int daysDifferenteBetween(long t1, long t2) {
        long dif = t1 - t2;
        return Math.abs((int) (dif / (1000 * 60 * 60 * 24)));
    }

    public static void enviar_correo(Cliente cliente, Cabecera cab, ArrayList<Detalle> detalles) {
        File file2 = new File("PlantillaCorreos/notificacion.html");
        String fecha_hoy = new SimpleDateFormat("dd/MM/yyyy").format(new Timestamp(System.currentTimeMillis()));
        System.out.println(file2.getAbsolutePath());
        System.out.println(file2.exists());
        //System.out.println(new File("main/PlantillaCorreos/notificacion.html").getAbsolutePath());
        String texto_html = PlantillasHTML.getCorreoHTML(file2.getAbsolutePath());
        System.out.println(texto_html);

        // Recipient's email ID needs to be mentioned.
        String from = "pruebaback23@gmail.com";

        // Sender's email ID needs to be mentioned
        String to = cliente.getEmail();

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("pruebaback23@gmail.com", "botscvmqtpgytmen");
            }
        });

        String lista_detalles_html = "";

        String text = "<tr>" +
                "<th style=\"border: solid grey 1px; padding: 5px 10px 5px 10px;\">%s</th>" +
                "<th style=\"border: solid grey 1px; padding: 5px 10px 5px 10px;\">%s</th>" +
                "<th style=\"border: solid grey 1px; padding: 5px 10px 5px 10px;\">%s</th>" +
                "</tr>";
        for (Detalle det : detalles) {
            lista_detalles_html += String.format(text, det.getIdCabecera(), det.getPuntajeUtilizado(), det.getIdBolsa());
        }

        texto_html = texto_html.replaceAll("#NOMBRE_CLIENTE#", cliente.getNombre());
        texto_html  = texto_html.replaceAll("#LISTA_DETALLES#", lista_detalles_html);
        texto_html  = texto_html.replaceAll("#HOY#", fecha_hoy);
        texto_html  = texto_html.replaceAll("#CANTIDAD_PUNTOS_USADOS#", cab.getPuntajeUtilizado()+"");

        // Used to debug SMTP issues
        session.setDebug(true);
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Notificación de Uso de Puntos: "+fecha_hoy);

            // Now set the actual message
            //message.setText("This is actual message");
            message.setContent(
                    texto_html,
                    "text/html");
            System.out.println("enviando...");
            // Send message
            Transport.send(message);
            System.out.println("enviado exitosamente....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }


}