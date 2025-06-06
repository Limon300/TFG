/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.entorno.dto;

/**
 *
 * @author usuario
 */
public class TelegramRequest {
    Long chat_id;
    String mensaje;

    public TelegramRequest() {
    }

    public TelegramRequest(Long chat_id, String mensaje) {
        this.chat_id = chat_id;
        this.mensaje = mensaje;
    }

    public Long getChat_id() {
        return chat_id;
    }

    public void setChat_id(Long chat_id) {
        this.chat_id = chat_id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    
}
