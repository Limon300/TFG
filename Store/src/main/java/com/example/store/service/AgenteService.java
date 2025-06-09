/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package com.example.store.service;


import com.example.store.dto.AgenteRequest;
import com.example.store.dto.AgenteResponse;
import com.example.store.models.AgenteModelo;
import com.example.store.models.HabilidadModelo;
import com.example.store.repositories.AgenteRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author usuario
 */
@Service
public class AgenteService {
    
    @Autowired
    AgenteRepository repositorio;

    public Optional<AgenteResponse> getData(Long Id) {
        Optional<AgenteModelo> dato = repositorio.findById(Id);
        if(dato.isEmpty()){
            return Optional.empty();
        }
        else{
            return Optional.of(new AgenteResponse(dato.get()));
        }
    }
    
    public Optional<Long> addData(AgenteRequest peticion) {
        Random generador = new Random();
        int nuevo_id = 0;
        while(repositorio.findById(Long.valueOf(nuevo_id)).isPresent()){
            nuevo_id = generador.nextInt(1000000000);
        }
        repositorio.insertar(Long.valueOf(nuevo_id), peticion.getNombre(), peticion.getDescripcion(),peticion.getEnergia_max());
        for(int i=0; i < peticion.getHabilidades().size();i++){
            repositorio.insertar_habilidad(Long.valueOf(nuevo_id), peticion.getHabilidades().get(i));
        }
        for(int i=0; i < peticion.getSensores().size();i++){
            repositorio.insertar_sensor(Long.valueOf(nuevo_id), peticion.getSensores().get(i));
        }
        return Optional.of(Long.valueOf(nuevo_id));
    }

    public boolean alterData(Long Id, AgenteRequest peticion) {
        if(repositorio.existsById(Id)){          
            if(peticion.getNombre() != null) repositorio.updateNombre(Id, peticion.getNombre());
            if(peticion.getEnergia_max() != null) repositorio.updateEnergia_max(Id, peticion.getEnergia_max());
            if(peticion.getDescripcion() != null) repositorio.updateDescripcion(Id, peticion.getDescripcion());
            return true;
        }
        else{
            return false;  
        }
    }

    public boolean deleteData(Long Id) {
        if(repositorio.existsById(Id)){
            repositorio.eliminar_habilidades(Id);
            repositorio.eliminar_sensores(Id);
            repositorio.deleteById(Id);
            return true;
        }
        else{
            return false;  
        }
    }
    
    public List<AgenteResponse> getAllData() {
        List<AgenteModelo> aux = repositorio.findAll();
        List<AgenteResponse> salida = new ArrayList();
        for(int i = 0; i<aux.size();i++){
            salida.add(new AgenteResponse(aux.get(i)));
        }
        return salida;
    }

    public boolean addSensor(Long Id, AgenteRequest peticion) {
        if(repositorio.existsById(Id)){
            List<Long>  aux = repositorio.findById(Id).get().getIdSensores();
            for(int i=0;i<peticion.getSensores().size();i++){
                if(!aux.contains(peticion.getSensores().get(i))){
                    repositorio.insertar_sensor(Id, peticion.getSensores().get(i));
                }
            }
            return true;
        }
        else{
            return false;  
        }
    }

    public boolean addHabilidad(Long Id, AgenteRequest peticion) {
        if(repositorio.existsById(Id)){
            List<Long>  aux = repositorio.findById(Id).get().getIdHabilidades();
            for(int i=0;i<peticion.getHabilidades().size();i++){
                if(!aux.contains(peticion.getHabilidades().get(i))){
                    repositorio.insertar_habilidad(Id, peticion.getHabilidades().get(i));
                }
            }
            return true;
        }
        else{
            return false;  
        }
    }

    public boolean removeSensor(Long Id, AgenteRequest peticion) {
        if(repositorio.existsById(Id)){
            List<Long>  aux = repositorio.findById(Id).get().getIdSensores();
            for(int i=0;i<peticion.getSensores().size();i++){
                if(aux.contains(peticion.getSensores().get(i))){
                    repositorio.quitar_sensor(Id, peticion.getSensores().get(i));
                }
            }
            return true;
        }
        else{
            return false;  
        }
    }

    public boolean removeHabilidad(Long Id, AgenteRequest peticion) {
        if(repositorio.existsById(Id)){
            List<Long>  aux = repositorio.findById(Id).get().getIdHabilidades();
            for(int i=0;i<peticion.getHabilidades().size();i++){
                if(aux.contains(peticion.getHabilidades().get(i))){
                    repositorio.quitar_habilidad(Id, peticion.getHabilidades().get(i));
                }
            }
            return true;
        }
        else{
            return false;  
        }
    }

    
}
