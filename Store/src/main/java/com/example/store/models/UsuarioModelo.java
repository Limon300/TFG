/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Component.java to edit this template
 */
package com.example.store.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/*
    ID INTEGER PRIMARY KEY,
    NOMBRE TEXT NOT NULL,
    APELLIDOS TEXT,
    TELEGRAM TEXT,
    TOKEN TEXT,
    DNI TEXT NOT NULL,
    STATUS TEXT NOT NULL constraint u_priv CHECK (STATUS IN ('ADM', 'USR')),
    GRUPO TEXT,
    PASSWORD TEXT NOT NULL
*/

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "usuario")
public class UsuarioModelo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id_usuario;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "telegram")
    private String telegram;
    @Column(name = "token")
    private String token;
    @Column(name = "DNI")
    private String DNI;
    @Column(name = "status")
    private String status;
    @Column(name = "grupo")
    private String grupo;
    @Column(name = "password")
    private String password;

    public UsuarioModelo() {

    }

    public UsuarioModelo(int id_usuario, String nombre, String apellidos, String telegram, String DNI, String status, String grupo, String password,String token) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telegram = telegram;
        this.DNI = DNI;
        this.status = status;
        this.grupo = grupo;
        this.password = password;
        this.token = token;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelegram() {
        return telegram;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    
}
