/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.store.repositories;

import com.example.store.models.SensorModelo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author usuario
 */
@Repository
public interface SensorRepository extends JpaRepository<SensorModelo, Long>{
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO sensor (id_sensor, nombre, descripcion, radio,categoria) VALUES (?1, ?2, ?3, ?4,?5)", nativeQuery = true)
    int insertar(Long id, String nombre, String descripcion, Long radio,String categoria);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE sensor SET nombre = ?2 WHERE id_sensor = ?1", nativeQuery = true)
    int updateNombre(Long id_usuario,String nuevo);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE sensor SET descripcion = ?2 WHERE id_sensor = ?1", nativeQuery = true)
    int updateDescripcion(Long id_usuario,String nuevo);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE sensor SET radio = ?2 WHERE id_sensor = ?1", nativeQuery = true)
    int updateRadio(Long id_usuario,Long nuevo);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE sensor SET categoria = ?2 WHERE id_sensor = ?1", nativeQuery = true)
    int updateCategoria(Long id_usuario,String nuevo);


}