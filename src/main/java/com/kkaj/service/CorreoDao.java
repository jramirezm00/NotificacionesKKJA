package com.kkaj.service;

import com.kkaj.model.Correo;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kenneth Parrales
 */
public class CorreoDao implements IDao<Correo>, Serializable {

    private static final long serialVersionUID = 1L;

    private final Conector conectorJDBC = Conector.getConector();

    @Override
    public List<Correo> buscar() {
        Connection connectionDB = conectorJDBC.conectar();
        List<Correo> correos = new ArrayList<>();
        Correo correo = null;
        try {
            String sql;
            sql = "SELECT * FROM correo correos WHERE correos.idUsuario;";
            PreparedStatement stmt = connectionDB.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                correo = new Correo();
                correo.setAsunto(rs.getString("asunto"));
                correo.setCuerpo(rs.getString("cuerpo"));
                correo.setDestinatario(rs.getString("destinatario"));
                correo.setIdUsuario(rs.getInt("idUsuario"));
                correo.setIdCorreo(rs.getInt("idCorreo"));
                correos.add(correo);
            }
            conectorJDBC.cerrarConexion(connectionDB, stmt, rs);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error, no se pudo completar la conexion a la Base de Datos");
        }
        return correos;
    }

    @Override
    public void insert(Correo data) {
        Connection connectionDB = conectorJDBC.conectar();
        PreparedStatement ps = null;
        try {
            ps = connectionDB.prepareStatement("insert into notificaciones.correo(idUsuario,asunto,cuerpo,destinatario) values (?,?,?,?);");
            ps.setInt(1, data.getIdUsuario());
            ps.setString(2, data.getAsunto());
            ps.setString(3, data.getCuerpo());
            ps.setString(4, data.getDestinatario());
            ps.executeUpdate();
            ps.close();
            connectionDB.close();
            System.out.println("Correo creado con exito!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error a la hora de crear el correo, hubo un problema con la Base de Datos");
        }
    }

    @Override
    public void delete(Correo data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Correo data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Correo> correosUsuario(int idUsuario) throws SQLException, ClassNotFoundException {
        Connection conn = conectorJDBC.conectar();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Correo> correos = new ArrayList<>();
        Correo correo = null;
        try {
            ps = conn.prepareStatement("SELECT usuario.correo AS usuario, correo.asunto, correo.cuerpo, correo.destinatario "
                    + "FROM NOTIFICACIONES.Correo correo INNER JOIN NOTIFICACIONES.Usuario usuario ON correo.idUsuario = usuario.idUsuario "
                    + "WHERE correo.idUsuario = ?;");
            ps.setInt(1, idUsuario);
            rs = ps.executeQuery();
            while (rs.next()) {
                correo = new Correo();
                correo.setUsuario(rs.getString("usuario"));
                correo.setAsunto(rs.getString("asunto"));
                correo.setCuerpo(rs.getString("cuerpo"));
                correo.setDestinatario(rs.getString("destinatario"));
                correos.add(correo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error, no se pudo completar la conexion a la Base de Datos");
        } finally {
            conectorJDBC.cerrarConexion(conn, ps, rs);
        }
        return correos;
    }
}
