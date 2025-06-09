/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package com.telegram.bot.service;

import com.telegram.bot.dto.Registro;
import com.telegram.bot.dto.UsuarioDataResponse;
import com.telegram.bot.dto.UsuarioPostRequest;
import com.telegram.bot.dto.UsuarioResponse;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author usuario
 */
@Service
public class SecurityService {
    @Autowired
    RestTemplate restTemplate;
    
    @Autowired
    UsuarioService servicioUsuario;
    
    
    public boolean Token_Verify(String raw,UsuarioDataResponse userInfo){
        if(userInfo.getToken() == null){
            return false;
        }
        else{
            return (userInfo.getToken() == null ? raw == null : userInfo.getToken().equals(raw));
        }
    }
    
    public boolean checkUser(Registro data,String pass){
        Optional<UsuarioDataResponse> Store = servicioUsuario.getFromStore(data.getId_usuario());
        //Hemos recibido algo?
        if(Store.isEmpty()){
            return false;
        }
        //Contraseña
        if(!this.Token_Verify(pass, Store.get())){
            return false;
        }
        else{
        
            UsuarioPostRequest peticion = new UsuarioPostRequest(null,null,data.getTelegram().toString(),null,null,null,null,"CHECKED");

            return servicioUsuario.alterDataFromStore(data.getId_usuario(), peticion).getStatusCode().is2xxSuccessful();
            
        }
        
    }
}
