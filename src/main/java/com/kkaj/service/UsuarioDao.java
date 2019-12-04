package com.kkaj.service;

import com.kkaj.model.Usuario;
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
public class UsuarioDao implements IDao<Usuario>, Serializable {

    private static final long serialVersionUID = 1L;

    private final Conector conectorJDBC = Conector.getConector();

    @Override
    public List<Usuario> buscar() {
        Connection connectionDB = conectorJDBC.conectar();
        List<Usuario> usuarios = new ArrayList<>();
        try {
            String sql;
            sql = "SELECT * FROM usuario user WHERE user.idUsuario;";
            PreparedStatement stmt = connectionDB.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                usuarios.add(new Usuario(rs.getString("nombre"),
                        rs.getString("apellido"), rs.getString("correo"),
                        rs.getString("contrasena")));
            }
            conectorJDBC.cerrarConexion(connectionDB, stmt, rs);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error, no se pudo completar la conexion a la Base de Datos");
        }
        return usuarios;
    }

    @Override
    public void insert(Usuario data) {

    }

    @Override
    public void delete(Usuario data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Usuario data) {
        Connection conn = conectorJDBC.conectar();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("update notificaciones.usuario set nombre = ?, apellido = ?, contrasena = ?, correo = ? where idUsuario = ?;");
            ps.setString(1, data.getNombre());
            ps.setString(2, data.getApellido());
            ps.setString(3, data.getContrasena());
            ps.setString(4, data.getCorreo());
            ps.setInt(5, data.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error, no se pudo modificar el usuario seleccionado!");
        } finally {
            conectorJDBC.cerrarConexion(conn, ps, rs);
        }
    }

    public void eliminarUsuario(int id) throws SQLException, ClassNotFoundException {
        Connection connectionDB = conectorJDBC.conectar();
        PreparedStatement ps = null;
        try {
            ps = connectionDB.prepareStatement("delete from notificaciones.usuario where idUsuario = ?;");
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            connectionDB.close();
            System.out.println("Usuario Eliminado con Exito!");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error a la hora de eliminar el Usuario");
        }
    }

    public Usuario login(String user, String pass) throws SQLException, ClassNotFoundException {
        Connection conn = conectorJDBC.conectar();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Usuario userLogged = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM notificaciones.usuario WHERE correo = ? and contrasena = ?;");
            ps.setString(1, user);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            while (rs.next()) {
                userLogged = new Usuario();
                userLogged.setNombre(rs.getString("nombre"));
                userLogged.setApellido(rs.getString("apellido"));
                userLogged.setContrasena(rs.getString("contrasena"));
                userLogged.setCorreo(rs.getString("correo"));
                userLogged.setId(rs.getInt("idUsuario"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error, no se pudo completar la conexion a la Base de Datos");
        } finally {
            conectorJDBC.cerrarConexion(conn, ps, rs);
        }
        return userLogged;
    }

    public Usuario getUser(String user) throws SQLException, ClassNotFoundException {
        Connection conn = conectorJDBC.conectar();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Usuario userLogged = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM notificaciones.usuario WHERE correo = ?");
            ps.setString(1, user);
            rs = ps.executeQuery();
            while (rs.next()) {
                userLogged = new Usuario();
                userLogged.setNombre(rs.getString("nombre"));
                userLogged.setApellido(rs.getString("apellido"));
                userLogged.setContrasena(rs.getString("contrasena"));
                userLogged.setCorreo(rs.getString("correo"));
                userLogged.setId(rs.getInt("idUsuario"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error, no se pudo completar la conexion a la Base de Datos");
        } finally {
            conectorJDBC.cerrarConexion(conn, ps, rs);
        }
        return userLogged;
    }

    public int signUp(Usuario data) {
        Connection connectionDB = conectorJDBC.conectar();
        PreparedStatement ps = null;
        try {
            ps = connectionDB.prepareStatement("insert into notificaciones.usuario(nombre,apellido,correo,contrasena) values (?,?,?,?);");
            ps.setString(1, data.getNombre());
            ps.setString(2, data.getApellido());
            ps.setString(3, data.getCorreo());
            ps.setString(4, data.getContrasena());
            ps.executeUpdate();
            ps.close();
            connectionDB.close();
            System.out.println("Cuenta creada con exito!");
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error a la hora de crear tu cuenta, hubo un problema con la Base de Datos");
        }
        return 0;
    }

}
