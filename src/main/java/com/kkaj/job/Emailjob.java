/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkaj.job;

import com.kkaj.controller.CorreoController;
import java.util.Date;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author josepabloramirez
 */
public class Emailjob implements Job {

    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("EmailJob --->>> Time is " + new Date());
        CorreoController correo = new CorreoController();
        correo.enviarCorreo();
    }
}
