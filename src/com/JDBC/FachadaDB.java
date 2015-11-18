/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.JDBC;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juandiego
 */
public class FachadaDB {
    
    /**
     * Atributo que almacena el nombre de la base de datos
     */
    private String baseDeDatos;
    
    
    /**
     * Atributo que almacena la contraseña de la conexión
     */
    private String clave;    
    
    /**
     * Atributo que almacena la dirección de conexión con la base de datos.
     */
    private String url;    
    
    
    /**
     * Atributo que almacena el nombre de ususario para el acceso a la conexión.
     */
    private String usuario;
    
    
    
    /**
     * Constructor de la clase FachadaDB
     */
    public FachadaDB(){
        baseDeDatos = "transportadora";
        clave = "admin";
        usuario = "root";
        url = "jdbc:mysql://localhost/"+baseDeDatos;
    }
    
  
    
    /**
     * Metodo que crea la conexión con la base de datos.
     * @return Retorna un objeto de tipo Connection
     */
    public Connection crearConexion() {
        Connection conexion=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = (Connection) DriverManager.getConnection(url, usuario, clave);
            System.out.println("Conexión realizada correctamente");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FachadaDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FachadaDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conexion;
    }
    
    
    /**
     * metodo para cerrar la conexion a la base de datos
     */
    public void cerrarConexion(Connection conexion){
        try {
            conexion.close();
            System.out.println("conexion cerrada conrrectamente");
        } catch (SQLException ex) {
            Logger.getLogger(FachadaDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void agergarEliminarModificar(String query){
        Connection con = null;
        Statement st = null;
        try {
            con = crearConexion();
            st = con.createStatement();
            st.execute(query);
            st.close();
            cerrarConexion(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    public ResultSet seleccionar(String query){
        Connection con = crearConexion();
        Statement st = null;
        ResultSet res = null;
        try {
            st = con.createStatement();
            res = st.executeQuery(query);
            st.close();
            cerrarConexion(con);
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
