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
            if (correo.matches(regex)) {
                usuario.setCorreo(this.correo);
            } else {
                //SE OCUPA CREAR PANTALLA DE ERRORES!
                return "error";
            }
            Integer validar = userDao.signUp(usuario);
            if (validar == 1) {
                return "Login?faces-redirect=true";
            } else {
                return "error";
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

}
