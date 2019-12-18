/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkaj.controller;

import com.kkaj.model.Usuario;
import com.kkaj.service.UsuarioDao;
import java.io.Serializable;
import java.util.regex.Pattern;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author kenneth Parrales
 */
@ManagedBean(name = "signUpController")
@SessionScoped
public class SignUpController implements Serializable {

    private static final long serialVersionUID = 1L;

    private String contra, correo, nombre, apellido;

    private String errorMessage;

    public SignUpController() {

    }

    public String signUp() {
        UsuarioDao userDao = new UsuarioDao();
        String correo = this.correo;
        //EXPRESION REGULAR PARA QUE EL CORREO SEA DE GMAIL
        String regex = "^[a-z0-9](\\.?[a-z0-9]){5,}@g(oogle)?mail\\.com$";

        try {
            Usuario usuario = new Usuario();
            usuario.setNombre(this.nombre);
            usuario.setApellido(this.apellido);
            usuario.setContrasena(this.contra);
            if (correo.matches(regex)) {
                usuario.setCorreo(this.correo);
            } else {
                this.errorMessage = "There was a problem while creating your account, remember to use a your Gmail account!";
                return "errorSignup?faces-redirect=true";
            }
            Integer validar = userDao.signUp(usuario);
            if (validar == 1) {
                this.nombre = null;
                this.apellido = null;
                this.contra = null;
                this.correo = null;
                return "index?faces-redirect=true";
            } else {
                this.errorMessage = "There was a internal problem, try again later!";
                return "errorSignup?faces-redirect=true";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
