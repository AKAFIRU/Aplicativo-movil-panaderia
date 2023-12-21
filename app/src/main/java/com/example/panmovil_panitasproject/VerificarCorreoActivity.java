package com.example.panmovil_panitasproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class VerificarCorreoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificar_correo);

        // Genera un número aleatorio de 4 dígitos
        int verificationCode = generateRandomCode(1000, 9999);

        // Muestra el número aleatorio en el TextView
        /*TextView textViewVerificationCode = findViewById(R.id.textViewVerificationCode);
        textViewVerificationCode.setText("Código de verificación: " + verificationCode);*/

        // Envía el código por correo electrónico
        sendEmail("destinatario@example.com", "Código de verificación", "Tu código de verificación es: " + verificationCode);

    }

    private int generateRandomCode(int min, int max) {
        // Genera un número aleatorio entre min y max
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    private void sendEmail(String to, String subject, String body) {
        // Configuración de propiedades para el servidor SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.example.com"); // Reemplaza con tu servidor SMTP
        props.put("mail.smtp.port", "587"); // Puerto del servidor SMTP

        // Credenciales de autenticación
        final String username = "manapandelcielo2023@gmail.com";
        final String password = "mlsb ssmi xsqc zhyc";

        // Se crea una nueva sesión de correo
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        // Se envía el correo electrónico en un hilo AsyncTask
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    // Crear un objeto de mensaje MIME
                    Message message = new MimeMessage(session);
                    // Configurar la dirección del remitente
                    message.setFrom(new InternetAddress(username));
                    // Configurar la dirección del destinatario
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                    // Configurar el asunto del correo electrónico
                    message.setSubject(subject);
                    // Configurar el cuerpo del correo electrónico
                    message.setText(body);

                    // Enviar el mensaje
                    Transport.send(message);

                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
                return null;
            }
        }.execute();
    }

}