/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package com.example.store.service;



import com.example.store.dto.Casilla;
import com.example.store.dto.CasillaRequest;
import com.example.store.dto.MapaRequest;
import com.example.store.dto.MapaResponse;
import com.example.store.models.CasillaModelo;
import com.example.store.models.MapaModelo;
import com.example.store.repositories.MapaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author usuario
 */
@Service
public class MapaService {
    
    @Autowired
    MapaRepository repositorio;
    
    @Autowired
    ObjetivoService servicioObjetivos;
    
    @Autowired
    POIService servicioPOI;

    public Optional<MapaResponse> getData(Long Id) {
        Optional<MapaModelo> dato = repositorio.findById(Id);
        if(dato.isEmpty()){
            return Optional.empty();
        }
        else{
            return Optional.of(new MapaResponse(dato.get()));
        }
    }
    
    public Optional<Casilla> getDataCasilla(Long Id, Long X, Long Y) {
        List<Map<String, Object>> dato = repositorio.findCasilla(Id,X,Y);
        if(dato.isEmpty()){
            return Optional.empty();
        }
        else{
            return Optional.of(new Casilla(dato.get(0),repositorio.findObjetivos(Id,X,Y),repositorio.findPOIs(Id,X,Y)));
        }
    }
    
    public Optional<List<Casilla>> getDataCasillas(Long Id) {
        List<Map<String, Object>> dato = repositorio.findCasillas(Id);
        if(dato.isEmpty()){
            return Optional.empty();
        }
        else{
            List<Casilla> salida = new ArrayList<>();
            for(int i=0;i<dato.size();i++){
                salida.add(new Casilla(
                        dato.get(i),
                        repositorio.findObjetivos(Id,((Integer) dato.get(i).get("X")).longValue(),((Integer) dato.get(i).get("Y")).longValue()),
                        repositorio.findPOIs(Id,((Integer) dato.get(i).get("X")).longValue(),((Integer) dato.get(i).get("Y")).longValue())
                ));
            }
            return Optional.of(salida);
        }
    }
    
    public Optional<Long> addData(MapaRequest peticion) {
        for(int i=0; i < peticion.getCasillas().size();i++){
            List<Long> obj = peticion.getCasillas().get(i).getObjetivos();
            List<Long> poi = peticion.getCasillas().get(i).getPOIs();
            if(obj != null){
                for(int j = 0;j < obj.size();j++){
                    if(servicioObjetivos.getData(obj.get(j)).isEmpty()){
                        System.out.println(obj.get(j));
                        return Optional.empty();
                    }
                }
            }
            if(poi != null){
                for(int j = 0;j < poi.size();j++){
                    if(servicioPOI.getData(poi.get(j)).isEmpty()){
                        System.out.println(poi.get(j));
                        return Optional.empty();
                    }
                }
            }
        }
        Random generador = new Random();
        int nuevo_id = 0;
        while(repositorio.findById(Long.valueOf(nuevo_id)).isPresent()){
            nuevo_id = generador.nextInt(1000000000);
        }
        //Se crea el mapa
        repositorio.insertar(
                Long.valueOf(nuevo_id),
                peticion.getNombre(),
                peticion.getEnergia_in(),
                peticion.getX_inicio(),
                peticion.getY_inicio(),
                peticion.getDescripcion(),
                peticion.getX_tam(),
                peticion.getY_tam()
        );
        //Se crean las casillas
        for(int i=0;i < peticion.getX_tam();i++ ){
            for(int j=0;j < peticion.getY_tam();j++){
                repositorio.insertar_casilla(
                    Long.valueOf(i),
                    Long.valueOf(j),
                    Long.valueOf(nuevo_id), 
                    Long.valueOf(1),
                    Long.valueOf(1),
                    "default"
                );
            }
        }
        //Detalles
        for(int i=0; i < peticion.getCasillas().size();i++){
            if(
                peticion.getCasillas().get(i).getX() < peticion.getX_tam() &&
                peticion.getCasillas().get(i).getY() < peticion.getY_tam() &&
                0 <= peticion.getCasillas().get(i).getX() &&
                0 <= peticion.getCasillas().get(i).getY()
            )
            {
                this.alterDataCasilla(
                        Long.valueOf(nuevo_id),
                        peticion.getCasillas().get(i).getX(),
                        peticion.getCasillas().get(i).getY(), 
                        peticion.getCasillas().get(i));
                //alterDataCasilla(Long Id, Long X, Long Y, CasillaRequest peticion)
            }
        }
        return Optional.of(Long.valueOf(nuevo_id));
    }

    public boolean alterData(Long Id, MapaRequest peticion) {
        if(repositorio.existsById(Id)){          
            if(peticion.getNombre() != null) repositorio.updateNombre(Id, peticion.getNombre());
            if(peticion.getEnergia_in() != null) repositorio.updateEnergia_in(Id, peticion.getEnergia_in());
            if(peticion.getDescripcion() != null) repositorio.updateDescripcion(Id, peticion.getDescripcion());
            if(peticion.getX_inicio() != null) repositorio.updateX_inicio(Id, peticion.getX_inicio());
            if(peticion.getY_inicio() != null) repositorio.updateY_inicio(Id, peticion.getY_inicio());
            if(peticion.getX_tam() != null) repositorio.updateX_tam(Id, peticion.getX_tam());
            if(peticion.getY_tam() != null) repositorio.updateY_tam(Id, peticion.getY_tam());
            return true;
        }
        else{
            return false;  
        }
    }
    
    public boolean alterDataCasilla(Long Id, Long X, Long Y, CasillaRequest peticion) {
        if(this.getDataCasilla(Id, X, Y).isPresent()){          
            if(peticion.getZ() != null) repositorio.updateCasillaZ(Id,X,Y, peticion.getZ());
            if(peticion.getCoste() != null) repositorio.updateCasillaCoste(Id,X,Y, peticion.getCoste());
            if(peticion.getApariencia() != null) repositorio.updateCasillaApariencia(Id,X,Y, peticion.getApariencia());
            if(peticion.getObjetivos() != null) this.addObjetivos(Id,X,Y,peticion.getObjetivos());
            if(peticion.getPOIs() != null) this.addPOIs(Id,X,Y,peticion.getPOIs());
            return true;
        }
        else{
            return false;  
        }
    }
    
    public void addObjetivos(Long Id, Long X, Long Y, List<Long> objetivos){
        if(objetivos != null){
            for(int i=0;i<objetivos.size();i++){
                if(repositorio.findObjetivo(X, Y, Id, objetivos.get(i)).isEmpty()){
                    repositorio.updateCasillaObjetivo(X, Y, Id, objetivos.get(i));
                }
            }
        }
    }
    
    public void addPOIs(Long Id, Long X, Long Y, List<Long> POIs){
        if(POIs != null)
        for(int i=0;i<POIs.size();i++){
            if(repositorio.findPOI(X, Y, Id, POIs.get(i)).isEmpty()){
                repositorio.updateCasillaPOIs(X, Y, Id, POIs.get(i));
            }
        }
    }

    public boolean deleteData(Long Id) {
        if(repositorio.existsById(Id)){
            repositorio.eliminar_POIs(Id);
            repositorio.eliminar_objetivos(Id);
            repositorio.eliminar_casillas(Id);
            repositorio.deleteById(Id);
            return true;
        }
        else{
            return false;  
        }
    }
    
    public void deleteObjetivos(Long Id, Long X, Long Y, List<Long> objetivos){
        for(int i=0;i<objetivos.size();i++){
            if(!repositorio.findObjetivo(X, Y, Id, objetivos.get(i)).isEmpty()){
                repositorio.RemoveObjetivo(X, Y, Id, objetivos.get(i));
            }
        }
    }
    
    public void deletePOIs(Long Id, Long X, Long Y, List<Long> POIs){
        for(int i=0;i<POIs.size();i++){
            if(!repositorio.findPOI(X, Y, Id, POIs.get(i)).isEmpty()){
                repositorio.RemovePOI(X, Y, Id, POIs.get(i));
            }
        }
    }
    
    public List<MapaResponse> getAllData() {
        List<MapaModelo> aux = repositorio.findAll();
        List<MapaResponse> salida = new ArrayList();
        for(int i = 0; i<aux.size();i++){
            salida.add(new MapaResponse(aux.get(i)));
        }
        return salida;
    }
    
    
}
