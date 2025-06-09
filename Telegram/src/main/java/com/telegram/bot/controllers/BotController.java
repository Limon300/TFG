/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package com.telegram.bot.controllers;

import com.telegram.bot.dto.TelegramRequest;
import com.telegram.bot.service.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author usuario
 */
@RestController
@RequestMapping("/api")
public class BotController {
    
    @Autowired
    BotService servicioBot;
    
    @PostMapping("/telegram")
    public ResponseEntity test(@RequestBody TelegramRequest peticion) {
        try {
            return servicioBot.Notificar(peticion);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
}
