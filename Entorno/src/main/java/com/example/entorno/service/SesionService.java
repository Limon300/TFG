/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.entorno.service;

import com.example.entorno.dto.ActionRequest;
import com.example.entorno.dto.ActionResponse;
import com.example.entorno.dto.AgenteStoreResponse;
import com.example.entorno.dto.Casilla;
import com.example.entorno.dto.LogRequest;
import com.example.entorno.dto.LogResponse;
import com.example.entorno.dto.MapaResponse;
import com.example.entorno.dto.ObjetivoSesionResponse;
import com.example.entorno.dto.SensorRequest;
import com.example.entorno.dto.SensorResponse;
import com.example.entorno.dto.SesionData;
import com.example.entorno.dto.SesionPostRequest;
import com.example.entorno.dto.SesionStart;
import com.example.entorno.dto.SesionUserRequest;
import com.example.entorno.dto.SesionUserStart;
import com.example.entorno.dto.UsuarioSesionData;
import com.example.entorno.models.AgenteModelo;
import com.example.entorno.models.ObjetivoModelo;
import com.example.entorno.models.SesionModelo;
import com.example.entorno.repositories.SesionRepository;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Stack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author usuario
 */
@Service
public class SesionService {
    @Autowired
    AgenteService servicioAgente;
    
    @Autowired
    MapaService servicioMapa;
    
    @Autowired
    UsuarioService servicioUsuario;
    
    @Autowired
    POIService servicioPOI;
    
    @Autowired
    ObjetivoService servicioObjetivos;
    
    @Autowired
    HabilidadService servicioHabilidad;
    
    @Autowired
    SensorService servicioSensor;
    
    
    @Autowired
    SesionRepository Local;

    @Autowired
    LogService servicioLog;
    
    public Optional<SesionStart> inicio(SesionPostRequest peticion,Long Id) {
        List<Long> agentesId = peticion.getAgentes();
        List<AgenteStoreResponse> agentes = new ArrayList();
        List<Casilla> casillas;
        SesionStart salida = new SesionStart();
        Random generador = new Random();
        long nuevo_id = 0;
        Optional<MapaResponse> mapa = servicioMapa.getDataFromStore(peticion.getMapa());
        //comprobar que los datos de la peticion sean correctos
        if(peticion.getT_ciclo() == null 
                || peticion.getT_ciclo() <= 0
                || peticion.getT_espera() == null
                || peticion.getT_espera() <= 0){
            return Optional.empty();
        }
        //comprobar que el mapa es correcto
        if(mapa.isPresent()){
            casillas = servicioMapa.getCasillasFromStore(peticion.getMapa()).orElseThrow();
        }
        else{
            return Optional.empty();
        }
        //comprobar que los agentes son correctos
        for(int i=0;i < agentesId.size();i++){
            Optional<AgenteStoreResponse> nuevo = servicioAgente.getDataFromStore(agentesId.get(i));
            if(nuevo.isPresent()){
                agentes.add(nuevo.get());
            }
            else{
                return Optional.empty();
            }
        }
        //generar id de sesion
        while(Local.findById(nuevo_id).isPresent()){
            nuevo_id = generador.nextLong(Long.MAX_VALUE);
        }
        Timestamp fecha = Timestamp.valueOf(LocalDateTime.now());
        Local.insertar(nuevo_id, peticion.getMapa(), fecha, fecha, peticion.getT_espera(), peticion.getT_ciclo());
        Local.insertar_usr(nuevo_id, Id);
        salida.setId_sesion(nuevo_id);
        //Insertar agentes
        salida.setId_agentes(servicioAgente.insertarAgentes(agentes,nuevo_id,Id,mapa.get()));
        //Log de entrada
        List<AgenteModelo> agentes_insertados = this.servicioAgente.getAgentesFull(nuevo_id,Id);
        for(int i=0;i<agentes.size();i++){
            this.servicioLog.addLogEntrada(
                    nuevo_id, Timestamp.valueOf(LocalDateTime.now()), agentes_insertados.get(i).getId_agente(),
                    agentes_insertados.get(i).getX(), agentes_insertados.get(i).getY()
            );
        }
        
        servicioPOI.insertarPOI(casillas,nuevo_id);
        //los sensores se insertan con los agentes
        servicioObjetivos.insertarObjetivos(casillas,nuevo_id);
        //ahora la parte de insertar sesion, agente, poi, objetivos, sensor y relaciones
        return Optional.of(salida);
    }

    public ResponseEntity<SesionStart> fin(Long sessionId) {
        List<String> usr = this.GetUsuarios(sessionId);
        if(usr.size() > 1){
            return ResponseEntity.badRequest().build();
        }
        this.servicioPOI.deletePOI(sessionId);
        this.servicioAgente.deleteAgentes(sessionId);
        this.servicioObjetivos.deleteObjetivos(sessionId);
        this.servicioLog.deleteLog(sessionId);
        Local.borrarUsr(sessionId);
        Local.borrarSesion(sessionId);
        return ResponseEntity.ok().build();
    }
    
    public List<String> GetUsuarios(Long sesion){
        List<String> salida = Local.obtener_usuarios(sesion);
        return salida;
    }

    public ResponseEntity<SesionStart> salir(Long sessionId, Long userId) {
        Local.deleteSesionUserByUserIdAndSesionId(sessionId,userId);
        if(Local.obtener_usuarios(sessionId).isEmpty()){
            return this.fin(sessionId);
        }
        else{
            List<AgenteModelo> agentes = this.servicioAgente.getAgentesFull(sessionId,userId);
            for(int i=0;i<agentes.size();i++){
                this.servicioLog.addLogSalida(
                        sessionId, Timestamp.valueOf(LocalDateTime.now()), agentes.get(i).getId_agente(),
                        agentes.get(i).getX(), agentes.get(i).getY()
                );
                
            }
            this.servicioAgente.deleteAgentesByUser(userId);
            return ResponseEntity.ok().build();
        }
    }
    


    public Optional<SesionData> consultar(Long sessionId) {
        Optional<SesionModelo> sesion = Local.findById(sessionId);
        List<UsuarioSesionData> usr_ag = new ArrayList();
        List<String> usuarios = Local.obtener_usuarios(sessionId);
        for(int i=0;i<usuarios.size();i++){
            List<Map<String,Long>> agentes = this.servicioAgente.getAgentes(sessionId,Long.decode(usuarios.get(i)));
            usr_ag.add(new UsuarioSesionData(Long.decode(usuarios.get(i)),agentes));
        }
        if(sesion.isPresent()){
            return Optional.of(new SesionData(sesion.get(),usr_ag));
        }
        else{
            return Optional.empty();
        }
    }
    //Probablemente esto sea horriblemente ineficente y tenga que cambiarse
    public List<SesionData> consultarUsuario(String extractId) {
        List<Long> ids = Local.findByUsuario(Long.valueOf(extractId));
        List<SesionData> salida = new ArrayList();
        for(int i=0;i< ids.size();i++){
            Optional<SesionData> nuevo = consultar(ids.get(i));
            if(nuevo.isPresent()){
                salida.add(nuevo.get());
            }
        }
        return salida;
    }

    public Optional<ActionResponse> habilidad(Long sesion,ActionRequest peticion,Long usuario) {
        
        if(peticion == null){
            return Optional.empty();
        }
        else{
            AgenteModelo agenteLocal = this.servicioAgente.getAgente(peticion.getId_agente()).orElseThrow();
            if(!Objects.equals(agenteLocal.getId_usuario(), usuario)||
                    !Objects.equals(agenteLocal.getId_sesion(), sesion)){
                return Optional.empty();
            }
            AgenteStoreResponse agenteStore = this.servicioAgente.getDataFromStore(agenteLocal.getId_agente_store()).orElseThrow();
            for(int i=0;i<agenteStore.getHabilidades().size();i++){
                if(agenteStore.getHabilidades().get(i).getId_habilidad() == peticion.getId_habilidad()){
                    //Gestion del tiempo de espera de habilidades
                    synchronized(this){
                        SesionModelo m = this.Local.findById(sesion).get();
                        Timestamp proximo = this.servicioAgente.getProximaAccion(peticion.getId_agente()).orElse(null);
                        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
                        //Puede actuar
                        if(
                                (proximo == null || proximo.before(now)) &&
                                (m.getT_espera() == null || m.getT_inicio().getTime() + m.getT_espera() <= now.getTime())
                        ){
                            
                            if(m.getT_ciclo() != null && m.getT_ciclo() > 0){
                                proximo = new Timestamp(0);
                                proximo.setTime(now.getTime()+ m.getT_ciclo());
                                this.servicioAgente.setProximaAccion(peticion.getId_agente(),proximo);
                            }
                        }
                        //No puede actuar
                        else{
                            if(proximo == null){
                                proximo = new Timestamp(0);
                                proximo.setTime(m.getT_inicio().getTime() + m.getT_espera());
                                this.servicioAgente.setProximaAccion(peticion.getId_agente(),proximo);
                            }
                            return Optional.of(new ActionResponse(false,proximo));
                        }
                    }
                    switch(agenteStore.getHabilidades().get(i).getCategoria()){
                        case "MOV_UP" -> {
                            return Optional.of(this.servicioHabilidad.mov_basic(
                                    sesion,this.consultar(sesion).orElseThrow(),agenteLocal,agenteStore.getHabilidades().get(i),
                                    Long.valueOf(0),Long.valueOf(1))
                            );
                        }
                        case "MOV_DOWN" -> {
                            return Optional.of(this.servicioHabilidad.mov_basic(
                                    sesion,this.consultar(sesion).orElseThrow(),agenteLocal,agenteStore.getHabilidades().get(i),
                                    Long.valueOf(0),Long.valueOf(-1))
                            );
                        }
                        case "MOV_LEFT" -> {
                            return Optional.of(this.servicioHabilidad.mov_basic(
                                    sesion,this.consultar(sesion).orElseThrow(),agenteLocal,agenteStore.getHabilidades().get(i),
                                    Long.valueOf(-1),Long.valueOf(0))
                            );
                        }
                        case "MOV_RIGHT" -> {
                            return Optional.of(this.servicioHabilidad.mov_basic(
                                    sesion,this.consultar(sesion).orElseThrow(),agenteLocal,agenteStore.getHabilidades().get(i),
                                    Long.valueOf(1),Long.valueOf(0))
                            );
                        }
                        case "MOV_UP_RIGHT" -> {
                            return Optional.of(this.servicioHabilidad.mov_basic(
                                    sesion,this.consultar(sesion).orElseThrow(),agenteLocal,agenteStore.getHabilidades().get(i),
                                    Long.valueOf(1),Long.valueOf(1))
                            );
                        }
                        case "MOV_UP_LEFT" -> {
                            return Optional.of(this.servicioHabilidad.mov_basic(
                                    sesion,this.consultar(sesion).orElseThrow(),agenteLocal,agenteStore.getHabilidades().get(i),
                                    Long.valueOf(-1),Long.valueOf(1))
                            );
                        }
                        case "MOV_DOWN_RIGHT" -> {
                            return Optional.of(this.servicioHabilidad.mov_basic(
                                    sesion,this.consultar(sesion).orElseThrow(),agenteLocal,agenteStore.getHabilidades().get(i),
                                    Long.valueOf(1),Long.valueOf(-1))
                            );
                        }
                        case "MOV_DOWN_LEFT" -> {
                            return Optional.of(this.servicioHabilidad.mov_basic(
                                    sesion,this.consultar(sesion).orElseThrow(),agenteLocal,agenteStore.getHabilidades().get(i),
                                    Long.valueOf(-1),Long.valueOf(-1))
                            );
                        }
                        default -> {
                            return Optional.empty();
                        }
                        
                    }   
                }
            }
            return Optional.empty();
            
        }
        
    }

    public List<LogResponse> consultarLogs(Long sessionId, LogRequest peticion) {
        List<LogResponse> salida = this.servicioLog.consultarLogs(sessionId,peticion);
        return salida;
    }
    
    public List<ObjetivoSesionResponse> consultarObjetivos(Long sessionId) {
        List<ObjetivoSesionResponse> salida = this.servicioObjetivos.consultarObjetivos(sessionId);
        return salida;
    }
    
    public List<ObjetivoSesionResponse> consultarActuales(Long sessionId) {
        List<ObjetivoSesionResponse> salida = new ArrayList();
        Optional<ObjetivoModelo> actual = this.servicioObjetivos.getActual(sessionId);
        if(actual.isPresent()){
            salida.add(new ObjetivoSesionResponse(actual.get(),new ArrayList()));
        }
        return salida;
    }

    public Optional<SesionUserStart> invitar_usuario(Long sesionId, SesionUserRequest peticion) {
        if(this.servicioUsuario.isLocal(peticion.getId_usuario())){
            if(this.GetUsuarios(sesionId).contains(peticion.getId_usuario().toString())){
                return Optional.empty();
            }
            else{
                //Agentes
                List<AgenteStoreResponse> agentes = new ArrayList();
                //System.out.println(peticion.getId_usuario());
                for(int i=0;i < peticion.getAgentes().size();i++){
                    Optional<AgenteStoreResponse> nuevo = servicioAgente.getDataFromStore(peticion.getAgentes().get(i));
                    if(nuevo.isPresent()){
                        agentes.add(nuevo.get());
                    }
                    else{
                        return Optional.empty();
                    }
                }
                MapaResponse mapa = this.servicioMapa.getDataFromStore(this.Local.findById(sesionId).orElseThrow().getId_mapa()).orElseThrow();
                //Insertar usuario en la sesion
                Local.insertar_usr(sesionId, peticion.getId_usuario());
                //Insertar agentes
                Map<Long, Stack<Long>> respuesta = this.servicioAgente.insertarAgentes(
                        agentes, 
                        sesionId,
                        peticion.getId_usuario(),
                        mapa
                );
                //Logs de entrada
                List<AgenteModelo> agentes_insertados = this.servicioAgente.getAgentesFull(sesionId,peticion.getId_usuario());
                for(int i=0;i<agentes.size();i++){
                    this.servicioLog.addLogEntrada(
                            sesionId, Timestamp.valueOf(LocalDateTime.now()), agentes_insertados.get(i).getId_agente(),
                            agentes_insertados.get(i).getX(), agentes_insertados.get(i).getY()
                    );
                }
                return Optional.of(new SesionUserStart(respuesta));
            }
        }
        else{
            return Optional.empty();
        }
    }

    public Optional<SensorResponse> sensor(Long sessionId, SensorRequest peticion, Long usuario) {
        if(peticion == null){
            return Optional.empty();
        }
        else{
            AgenteModelo agenteLocal = this.servicioAgente.getAgente(peticion.getId_agente()).orElseThrow();
            if(!Objects.equals(agenteLocal.getId_usuario(), usuario)||
                    !Objects.equals(agenteLocal.getId_sesion(), sessionId)){
                return Optional.empty();
            }

            AgenteStoreResponse agenteStore = this.servicioAgente.getDataFromStore(agenteLocal.getId_agente_store()).orElseThrow();

            //Comprobamos que tenemos el sensor
            for(int i=0;i<agenteStore.getSensores().size();i++){
                if(Objects.equals(agenteStore.getSensores().get(i).getId_sensor(), peticion.getId_sensor())){
                    return this.servicioSensor.usarSensor(sessionId,this.consultar(sessionId).orElseThrow(),agenteLocal,agenteStore.getSensores().get(i));
                }
            }
            
            return Optional.empty();
        }
    }

    
    
}
