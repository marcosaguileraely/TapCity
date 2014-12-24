package com.cool4code.tapcity.Utils;

import android.util.Log;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by marcosantonioaguilerely on 12/23/14.
 */
public class SendMail {

    public String sendEmailNotification(String correo, String nombrecompleto, String usuario, String clave, String fecha, String ciudad){
        try{
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.user", "maguilera@cool4code.com");
            props.setProperty("mail.smtp.auth", "true");

            Session session = Session.getDefaultInstance(props);

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("maguilera@cool4code.com"));
            message.addRecipient(
                    Message.RecipientType.TO, new InternetAddress(correo));

            message.setSubject("Bienvenido a TapCity");
            message.setText(
                    "<center>"+"<img src=\"http://oi61.tinypic.com/14llv6h.jpg\">"+"<br/><br/>"+
                            "<b>BIENVENIDO A TAPCITY</b><br/>"+
                            "<i>"+nombrecompleto+"</i><br/><br/>"+
                            "<br>Queremos agradecerte el hacer parte de nuestra comunidad de ciudadanos comprometidos con una ciudad en paz y segura.<br/><br/>"+
                            "Hemos creado una cuenta con los siguientes detalles:<br/><br/>"+
                            "<b>Usuario:</b> "+usuario+"<br/>"+
                            "<b>Clave: </b>"+clave+"<br/>"+
                            "<b>Ciudad: </b>"+ciudad+"<br/>"+
                            "<b>Fecha y hora: </b>"+fecha+"<br/>"+
                            "<br/>"+
                            "No olvides pasar por la sección de TIPS y conocer información sobre nuestra app y tu seguridad.<br/><br/>"+
                            "Agradecemos su atención,<br/><br/>"+
                            "@cool4code - @tapcity","ISO-8859-1","html");
            Transport t = session.getTransport("smtp");
            t.connect("maguilera@cool4code.com", "oxexam09");
            t.sendMessage(message, message.getAllRecipients());
            t.close();
            Log.d("//MAIL ", "Correo enviado");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "¡Correo enviado!";
    }
}
