/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkaj.controller;

import com.kkaj.model.Correo;
import com.kkaj.service.CorreoDao;
import java.io.Serializable;
import static java.lang.Integer.parseInt;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author kenneth
 */
@ManagedBean(name = "correoController")
@SessionScoped
public class CorreoController implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idUsuario;

    private int idCorreo;

    private String destinatario, asunto, cuerpo;

    private List<Correo> correos = new ArrayList<>();

    private CorreoDao correoDao = new CorreoDao();

    public CorreoController() throws SQLException, ClassNotFoundException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        idUsuario = parseInt(request.getParameter("idUsuario"));
        this.all(idUsuario);

    }

    public void all(int id) throws SQLException, ClassNotFoundException {
        this.correos = correoDao.correosUsuario(id);
    }

    //ESTO NO DEBE DE SER UNA PANTALLA DEBE DE SER UN MENSAJE QUE DIGA SI SE ENVIO EL CORREO O NO!!!!
    public String CrearCorreo() {
        Correo correo = new Correo(this.asunto, this.cuerpo, this.destinatario, this.idUsuario, this.idCorreo);
        correoDao.insert(correo);
        //ESTO NO 
        return "Enviado?faces-redirect=true";
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdCorreo() {
        return idCorreo;
    }

    public void setIdCorreo(int idCorreo) {
        this.idCorreo = idCorreo;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public List<Correo> getCorreos() {
        return correos;
    }

    public void setCorreos(List<Correo> correos) {
        this.correos = correos;
    }

    public CorreoDao getCorreoDao() {
        return correoDao;
    }

    public void setCorreoDao(CorreoDao correoDao) {
        this.correoDao = correoDao;
    }

}
