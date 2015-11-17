/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main;

import com.JDBC.GestorReplicacion;

/**
 *
 * @author juandiego
 */
public class Run {
     public static void main(String[] args) {
         GestorReplicacion gestor= new GestorReplicacion();
         System.out.println(gestor.showMasterOnlineDatabase1());
         System.out.println(gestor.showMasterOnlineDatabase2());
         while(true){
             if(gestor.getMasterConectNow()==1&&gestor.showMasterOnlineDatabase2()==true){
                 gestor.changeMasterTo2();
             }
             else if(gestor.getMasterConectNow()==2&&gestor.showMasterOnlineDatabase2()==false){
                 gestor.changeMasterTo1();
             }
             if(gestor.showMasterOnlineDatabase1()==false&&gestor.showMasterOnlineDatabase2()==false){
                 gestor.setMasterConectNow(0);
             }
             if(gestor.getMasterConectNow()==0){
                 if(gestor.showMasterOnlineDatabase2()==true){
                     gestor.changeMasterTo2();
                 }
                 else if(gestor.showMasterOnlineDatabase1()==true){
                     gestor.changeMasterTo1();
                 }
             }
         }
    }
}
