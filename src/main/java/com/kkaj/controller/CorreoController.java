/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkaj.controller;

import com.kkaj.job.Emailjob;
import com.kkaj.model.Correo;
import com.kkaj.service.CorreoDao;
import com.kkaj.servicio.ServicioCorreo;
import java.io.Serializable;
import static java.lang.Integer.parseInt;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

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

    private static String destinatario, asunto, cuerpo;

    private List<Correo> correos = new ArrayList<>();

    private CorreoDao correoDao = new CorreoDao();

    private String tiempoSeleccionado;

    private List<String> tiempos = new ArrayList<String>();

    private static String linkReturn;

    @ManagedProperty("#{loginController}")
    private static LoginController loginController = new LoginController();

    public CorreoController() {

    }

    @PostConstruct
    public void init() {
        try {
            tiempos.add("30s");
            tiempos.add("1min");
            tiempos.add("5min");
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            idUsuario = parseInt(request.getParameter("idUsuario"));
            this.all(idUsuario);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void all(int id) throws SQLException, ClassNotFoundException {
        this.correos = correoDao.correosUsuario(id);
    }

    public String crearCorreo() {
        return "crearCorreo?faces-redirect=true&idUsuario= " + loginController.getUsuario().getId();
    }

    public String verCorreos() {
        return "correos?faces-redirect=true&idUsuario=" + loginController.getUsuario().getId();
    }

    //Metodo para enviar un correo
    public static String enviarCorreo() {
        ServicioCorreo servicio = new ServicioCorreo();
        if (CorreoController.asunto.equals("") || CorreoController.cuerpo.equals("") || CorreoController.destinatario.equals("")) {
            String link = "crearCorreo.xhtml?faces-redirect=true&idUsuario=" + loginController.getUsuario().getId();
            CorreoController.linkReturn = link;
            return "failure?faces-redirect=true";
        } else {
            servicio.enviarCorreo(loginController.getUsuario(), CorreoController.asunto, CorreoController.cuerpo, CorreoController.destinatario);
            String link = "correos.xhtml?faces-redirect=true&idUsuario=" + loginController.getUsuario().getId();
            CorreoController.linkReturn = link;
            return "success?faces-redirect=true";
        }

    }

    public void correoProgramado() {
        String cron = "";
        System.out.println("SOY UN CORREO PROGRAMADO");
        try {
            if (this.tiempoSeleccionado.equalsIgnoreCase("30s")) {
                System.out.println("ME VOY A EJECUTAR DENTRO DE 30s");
                cron = "0/30 * * * * ?";
            } else if (this.tiempoSeleccionado.equalsIgnoreCase("1min")) {
                cron = "0 */1 * ? * *";
            } else if (this.tiempoSeleccionado.equalsIgnoreCase("5min")) {
                cron = "0 */5 * ? * *";
            }

            System.out.println(cron);
            System.out.println(new Date());

            JobDetail job1 = JobBuilder.newJob(Emailjob.class).withIdentity("job1", "group1").build();

            Trigger trigger1 = TriggerBuilder.newTrigger().withIdentity("cronTrigger1", "group1").withSchedule(CronScheduleBuilder.cronSchedule(cron))
                    .build();

            Scheduler scheduler1 = new StdSchedulerFactory().getScheduler();
            scheduler1.start();
            scheduler1.scheduleJob(job1, trigger1);

            Thread.sleep(25000);

            scheduler1.shutdown();

            System.out.println("YA TERMINE! " + new Date());

        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public String getTiempoSeleccionado() {
        return tiempoSeleccionado;
    }

    public void setTiempoSeleccionado(String tiempoSeleccionado) {
        this.tiempoSeleccionado = tiempoSeleccionado;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public List<String> getTiempos() {
        return tiempos;
    }

    public void setTiempos(List<String> tiempos) {
        this.tiempos = tiempos;
    }

    public String getLinkReturn() {
        return linkReturn;
    }

    public void setLinkReturn(String linkReturn) {
        this.linkReturn = linkReturn;
    }

}
