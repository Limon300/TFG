/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.example.store.repositories;

import com.example.store.models.AgenteModelo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author usuario
 */
@Repository
public interface AgenteRepository extends JpaRepository<AgenteModelo, Long> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO agente (id_agente, nombre, descripcion, energia_max) VALUES (?1, ?2, ?3, ?4)", nativeQuery = true)
    int insertar(Long id, String nombre, String descripcion, Long energia);
   
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO agente_habilidad (id_agente, id_habilidad) VALUES (?1, ?2)", nativeQuery = true)
    int insertar_habilidad(Long id, Long id_habilidad);
    
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO agente_sensor (id_agente, id_sensor) VALUES (?1, ?2)", nativeQuery = true)
    int insertar_sensor(Long id, Long id_sensor);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE agente SET nombre = ?2 WHERE id_agente = ?1", nativeQuery = true)
    int updateNombre(Long id,String nuevo);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE agente SET energia_max = ?2 WHERE id_agente = ?1", nativeQuery = true)
    int updateEnergia_max(Long id,Long nuevo);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE agente SET descripcion = ?2 WHERE id_agente = ?1", nativeQuery = true)
    int updateDescripcion(Long id,String nuevo);
    
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM agente_habilidad WHERE id_agente = ?1 AND id_habilidad = ?2", nativeQuery = true)
    int quitar_habilidad(Long id, Long id_habilidad);
    
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM agente_sensor WHERE id_agente = ?1 AND id_sensor = ?2", nativeQuery = true)
    int quitar_sensor(Long id, Long id_sensor);
    
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM agente_habilidad WHERE id_agente = ?1", nativeQuery = true)
    int eliminar_habilidades(Long id);
    
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM agente_sensor WHERE id_agente = ?1", nativeQuery = true)
    int eliminar_sensores(Long id);
    
    
}
