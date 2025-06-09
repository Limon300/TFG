/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.store.service;

import com.example.store.dto.UsuarioDataResponse;
import com.example.store.dto.UsuarioPostRequest;
import com.example.store.dto.UsuarioResponse;
import com.example.store.models.UsuarioModelo;
import com.example.store.repositories.UsuarioRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author usuario
 */
@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository repositorio;
    
    

    public UsuarioService() {
    }
    
    public Optional<UsuarioResponse> get(Long id){
        Optional<UsuarioModelo> auxiliar = repositorio.findById(id);
        if(auxiliar.isPresent()){
            UsuarioResponse salida = new UsuarioResponse(auxiliar.get().getId_usuario(),auxiliar.get().getPassword());
            return Optional.of(salida);
        }
        return Optional.empty();
    }
    
    public Optional<UsuarioDataResponse> getData(Long id){
        Optional<UsuarioModelo> auxiliar = repositorio.findById(id);
        if(auxiliar.isPresent()){
            UsuarioDataResponse salida = new UsuarioDataResponse(
                    auxiliar.get().getId_usuario(),
                    auxiliar.get().getNombre(),
                    auxiliar.get().getApellidos(),
                    auxiliar.get().getTelegram(),
                    auxiliar.get().getDNI(),
                    auxiliar.get().getStatus(),
                    auxiliar.get().getGrupo(),
                    auxiliar.get().getToken()
            );
            return Optional.of(salida);
        }
        return Optional.empty();
    }
    
    public Optional<Long> addData(UsuarioPostRequest datos){
        Random generador = new Random();
        int nuevo_id = 0;
        PasswordEncoder codificador = new BCryptPasswordEncoder();
        while(repositorio.findById(Long.valueOf(nuevo_id)).isPresent()){
            nuevo_id = generador.nextInt(1000000000);
        }
        repositorio.insertar(
                    nuevo_id,
                    datos.getNombre(),
                    datos.getApellidos(),
                    datos.getTelegram(),
                    datos.getDNI(),
                    datos.getStatus(),
                    datos.getGrupo(),
                    datos.getPassword(),
                    codificador.encode(LocalDateTime.now().toString())
        
        );
        return Optional.of(Long.valueOf(nuevo_id));
    }
    
    public boolean alterData(Long Id,UsuarioPostRequest datos){
        if(repositorio.existsById(Id)){
            if(datos.getNombre() != null) repositorio.updateNombre(Id, datos.getNombre());
            if(datos.getApellidos() != null) repositorio.updateApellidos(Id, datos.getApellidos());
            if(datos.getDNI() != null) repositorio.updateDNI(Id, datos.getDNI());
            if(datos.getGrupo() != null) repositorio.updateGrupo(Id, datos.getGrupo());
            if(datos.getStatus() != null) repositorio.updateStatus(Id, datos.getStatus());
            if(datos.getTelegram() != null) repositorio.updateTelegram(Id, datos.getTelegram());
            if(datos.getPassword() != null) repositorio.updatePassword(Id, datos.getPassword());
            if("CHECKED".equals(datos.getToken())) {
                repositorio.updateToken(Id, null);
            }
            else if(datos.getToken()!= null) {
                repositorio.updateToken(Id, datos.getToken());
            }
            return true;
        }
        else{
            return false;  
        }
    }
    
    public boolean deleteData(Long id){
        if(repositorio.existsById(id)){
            repositorio.deleteById(id);
            return true;
        }
        else{
            return false;  
        }
    }
    
    public List<UsuarioDataResponse> getAllData(){
        List<UsuarioModelo> aux = repositorio.findAll();
        List<UsuarioDataResponse> salida = new ArrayList();
        for(int i = 0; i<aux.size();i++){
            salida.add(new UsuarioDataResponse(
                    aux.get(i).getId_usuario(),
                    aux.get(i).getNombre(),
                    aux.get(i).getApellidos(),
                    aux.get(i).getTelegram(),
                    aux.get(i).getDNI(),
                    aux.get(i).getStatus(),
                    aux.get(i).getGrupo(),
                    aux.get(i).getToken()
            ));
        }
        return salida;
    }
}