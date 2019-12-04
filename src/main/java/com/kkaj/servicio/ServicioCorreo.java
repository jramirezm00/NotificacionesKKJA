/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkaj.servicio;


import com.kkaj.model.Correo;
import com.kkaj.model.Usuario;
import com.kkaj.service.CorreoDao;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author josepabloramirez
 */
public class ServicioCorreo {

    //Metodo para enviar un correo
    public void enviarCorreo(Usuario user, String asunto, String cuerpo, String destinatario) {
        CorreoDao correoDao = new CorreoDao();
        Correo correo = null;
        try {
            Email email = new SimpleEmail();
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            System.out.println(user.getCorreo());
            System.out.println(user.getContrasena());
            email.setAuthenticator(new DefaultAuthenticator(user.getCorreo(), user.getContrasena()));
            email.setSSLOnConnect(true);
            email.setFrom(user.getCorreo());
            email.setSubject(asunto);
            email.setMsg(cuerpo);
            email.addTo(destinatario);
            email.send();

            correo = new Correo();
            correo.setIdUsuario(user.getId());
            correo.setAsunto(asunto);
            correo.setCuerpo(cuerpo);
            correo.setDestinatario(destinatario);
            correoDao.insert(correo);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
