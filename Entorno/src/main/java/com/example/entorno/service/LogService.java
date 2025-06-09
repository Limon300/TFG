/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.entorno.service;

import com.example.entorno.dto.LogRequest;
import com.example.entorno.dto.LogResponse;
import com.example.entorno.dto.TelegramRequest;
import com.example.entorno.dto.UsuarioDataResponse;
import com.example.entorno.repositories.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author usuario
 */
@Service
class LogService {
    @Value("${telegram}")
    private String telegram_adress;
    
    @Autowired
    LogRepository Local;
    
    @Autowired
    RestTemplate restTemplate;
    
    @Autowired
    UsuarioService ServicioUsuario;
    

    public ResponseEntity sendMessage(TelegramRequest peticion){
        String usrUrl = "http://"+telegram_adress+"/api/telegram";
        try{
            return restTemplate.postForEntity(usrUrl, peticion, ResponseEntity.class);
        }
        catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    void deleteLog(Long sessionId) {
        Local.deleteSesionLogs(sessionId);
    }

    void addLogHabilidad(Long id, Timestamp fecha, Long id_agente, String nombre, String categoria,Long x,Long y) {
        long nuevo_id = 0;
        Random generador = new Random();
        while(Local.findById(nuevo_id).isPresent()){
            nuevo_id = generador.nextLong(Long.MAX_VALUE);
        }
        String entrada = "";
        entrada = entrada
                .concat("El agente ")
                .concat(id_agente.toString())
                .concat(" uso la habilidad ")
                .concat(nombre)
                .concat(" (")
                .concat(categoria)
                .concat(")");
        Local.insertar(nuevo_id,id,fecha,entrada,categoria,id_agente,x,y);
    }
    
    void addLogEntrada(Long id, Timestamp fecha, Long id_agente,Long x,Long y) {
        long nuevo_id = 0;
        Random generador = new Random();
        while(Local.findById(nuevo_id).isPresent()){
            nuevo_id = generador.nextLong(Long.MAX_VALUE);
        }
        String entrada = "";
        entrada = entrada
                .concat("El agente ")
                .concat(id_agente.toString())
                .concat(" se unio a la sesion ")
                .concat(id.toString());
        Local.insertar(nuevo_id,id,fecha,entrada,"ENTER",id_agente,x,y);
    }
    
    void addLogSalida(Long id, Timestamp fecha, Long id_agente,Long x,Long y) {
        long nuevo_id = 0;
        Random generador = new Random();
        while(Local.findById(nuevo_id).isPresent()){
            nuevo_id = generador.nextLong(Long.MAX_VALUE);
        }
        String entrada = "";
        entrada = entrada
                .concat("El agente ")
                .concat(id_agente.toString())
                .concat(" abandono la sesion ")
                .concat(id.toString());
        Local.insertar(nuevo_id,id,fecha,entrada,"EXIT",id_agente,x,y);
    }

    void addLogObjetivo(Long id_user,Long id, Timestamp fecha, Long id_agente, String nombre, String categoria) {
        long nuevo_id = 0;
        Random generador = new Random();
        while(Local.findById(nuevo_id).isPresent()){
            nuevo_id = generador.nextLong(Long.MAX_VALUE);
        }
        String entrada = "";
        entrada = entrada
                .concat("El agente ")
                .concat(id_agente.toString())
                .concat(" logro el objetivo ")
                .concat(nombre)
                .concat(" (")
                .concat(categoria+"_OBJ")
                .concat(")");
        Optional<UsuarioDataResponse> data = this.ServicioUsuario.getDataFromStore(id_user);
        if(data.isPresent() && data.get().getTelegram() != null){
            TelegramRequest peticion = new TelegramRequest(Long.valueOf(data.get().getTelegram()),entrada);
            this.sendMessage(peticion);
        }
        
        Local.insertar(nuevo_id,id,fecha,entrada,categoria+"_OBJ",id_agente,null,null);
    }

    void addLogPOI(Long sesion_id, Timestamp fecha, Long agente_id, String nombre, String categoria) {
        long nuevo_id = 0;
        Random generador = new Random();
        while(Local.findById(nuevo_id).isPresent()){
            nuevo_id = generador.nextLong(Long.MAX_VALUE);
        }
        String entrada = "";
        entrada = entrada
                .concat("El agente ")
                .concat(agente_id.toString())
                .concat(" alcanzo el POI ")
                .concat(nombre)
                .concat(" (")
                .concat(categoria+"_POI")
                .concat(")");
        Local.insertar(nuevo_id,sesion_id,fecha,entrada,categoria+"_POI",agente_id,null,null);
    }

    List<LogResponse> consultarLogs(Long sessionId, LogRequest peticion) {
        List<LogResponse> salida = new ArrayList();
        List<Map<String,Object>> datos;
        if(peticion.getFecha_min() == null){
            if(peticion.getFecha_max() == null){
                datos = Local.getLogsSesion(sessionId);
            }
            else{
                datos = Local.getLogsUntil(sessionId,peticion.getFecha_max());
            }
        }
        else{
            if(peticion.getFecha_max() == null){
                datos = Local.getLogsFrom(sessionId,peticion.getFecha_min());
            }
            else{
                datos = Local.getLogsFromTo(sessionId,peticion.getFecha_min(),peticion.getFecha_max());
            }
        }
        for(int i = 0;i<datos.size();i++){
            salida.add(new LogResponse(datos.get(i)));
        }
        return salida;
    }
    
}
