package com.kkaj.model;

/**
 *
 * @author kenneth Parrales
 */
public class Correo {

    private int idCorreo;

    private int idUsuario;

    private String asunto;

    private String cuerpo;

    private String destinatario;

    private String usuario;

    public Correo() {
    }

    public Correo(String asunto, String cuerpo, String destinatario,
            int idUsuario, int idCorreo) {
        this.asunto = asunto;
        this.cuerpo = cuerpo;
        this.destinatario = destinatario;
        this.idCorreo = idCorreo;
        this.idUsuario = idUsuario;
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

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

}
