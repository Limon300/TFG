/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.telegram.bot.dto;

/**
 *
 * @author usuario
 */
public class Registro {
    
    String estado;
    Long telegram;
    Long id_usuario;

    public Registro(String estado, Long telegram) {
        this.estado = estado;
        this.telegram = telegram;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getTelegram() {
        return telegram;
    }

    public void setTelegram(Long telegram) {
        this.telegram = telegram;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }
    
    

    
}
