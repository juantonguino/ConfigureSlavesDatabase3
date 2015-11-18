/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.JDBC;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juandiego
 */
public class GestorReplicacion {
    
    private int masterConectNow;
    
    private FachadaDB fachadaDB;

    public GestorReplicacion() {
        fachadaDB= new FachadaDB();
        masterConectNow=0;
    }
    
    public String showMasterStatus(){
        try {
            ResultSet resultado = this.fachadaDB.seleccionar("show master status;");
            String file="";
            int position=0;
            while (resultado.next()) {                
                file = resultado.getString("File");
                position = resultado.getInt("Position");   
            }
            return file+"/"+position;
        } catch (SQLException ex) {
            Logger.getLogger(GestorReplicacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    public boolean changeMasterTo2() {
        boolean retorno = false;
        try {
            String[] resultado = showMasterStatusDataBase2().split("/");
            System.out.println("se esta conectand a la base de datos 2="+showMasterStatusDataBase2());
            String masterLogFile = resultado[0];
            String Pos = resultado[1];
            String stopSlave = "STOP SLAVE";
            String resertSlave="RESET SLAVE";
            String changeMasterTo = "CHANGE MASTER TO "
                    + "MASTER_HOST = '192.168.0.202', "
                    + "MASTER_USER = 'user23', "
                    + "MASTER_PASSWORD = 'admin', "
                    + "MASTER_LOG_FILE = '" + masterLogFile + "', "
                    + "MASTER_LOG_POS = " + Pos + ";";
            String startSlave = "START SLAVE";
            fachadaDB.agergarEliminarModificar(stopSlave);
            fachadaDB.agergarEliminarModificar(resertSlave);
            fachadaDB.agergarEliminarModificar(changeMasterTo);
            fachadaDB.agergarEliminarModificar(startSlave);
            masterConectNow=2;
            retorno = true;
        } catch (Exception e) {
            retorno = false;
        }
        return retorno;
    }
    
    public boolean changeMasterTo1(){
        boolean retorno = false;
        try {
            String[] resultado = showMasterStatusDataBase1().split("/");
            System.out.println("se esta conectandp a la base de datos 1="+showMasterStatusDataBase1());
            String masterLogFile = resultado[0];
            String Pos = resultado[1];
            String stopSlave = "STOP SLAVE";
            String resertSlave="RESET SLAVE";
            String changeMasterTo = "CHANGE MASTER TO "
                    + "MASTER_HOST = '192.168.0.201', "
                    + "MASTER_USER = 'user13', "
                    + "MASTER_PASSWORD = 'admin', "
                    + "MASTER_LOG_FILE = '" + masterLogFile + "', "
                    + "MASTER_LOG_POS = " + Pos + ";";
            String startSlave = "START SLAVE";
            fachadaDB.agergarEliminarModificar(stopSlave);
            fachadaDB.agergarEliminarModificar(resertSlave);
            fachadaDB.agergarEliminarModificar(changeMasterTo);
            fachadaDB.agergarEliminarModificar(startSlave);
            masterConectNow=1;
            retorno = true;
        } catch (Exception e) {
            retorno = false;
        }
        return retorno;
    }
    
    public boolean showMasterOnlineDatabase2(){
        boolean retorno=false;
        try{
            showMasterStatusDataBase2();
            retorno=true;
        }
        catch(Exception e){
            retorno=false;
        }
        return retorno;
    }
    public boolean showMasterOnlineDatabase1(){
        boolean retorno=false;
        try{
            showMasterStatusDataBase1();
            retorno=true;
        }
        catch(Exception e){
            retorno=false;
        }
        return retorno;
    }

    public int getMasterConectNow() {
        return masterConectNow;
    }

    public void setMasterConectNow(int masterConectNow) {
        this.masterConectNow = masterConectNow;
    }

    private static String showMasterStatusDataBase1() {
        com.database1.PublicacionMaestro_Service service = new com.database1.PublicacionMaestro_Service();
        com.database1.PublicacionMaestro port = service.getPublicacionMaestroPort();
        return port.showMasterStatus();
    }

    private static String showMasterStatusDataBase2() {
        com.database2.PublicacionMaestro_Service service = new com.database2.PublicacionMaestro_Service();
        com.database2.PublicacionMaestro port = service.getPublicacionMaestroPort();
        return port.showMasterStatus();
    }
}