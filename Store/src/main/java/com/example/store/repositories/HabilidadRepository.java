/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.store.repositories;

import com.example.store.models.HabilidadModelo;
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
public interface HabilidadRepository extends JpaRepository<HabilidadModelo, Long>{
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO habilidad (id_habilidad, nombre, v_up, v_down, descripcion,categoria) VALUES (?1, ?2, ?3, ?4, ?5, ?6)", nativeQuery = true)
    int insertar(Long id, String nombre, Long v_up, Long v_down, String descripcion,String categoria);
    
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE habilidad SET nombre = ?2 WHERE id_habilidad = ?1", nativeQuery = true)
    int updateNombre(Long id_usuario,String nuevo);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE habilidad SET categoria = ?2 WHERE id_habilidad = ?1", nativeQuery = true)
    int updateCategoria(Long id_usuario,String nuevo);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE habilidad SET v_up = ?2 WHERE id_habilidad = ?1", nativeQuery = true)
    int updateV_up(Long id_usuario,Long nuevo);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE habilidad SET v_down = ?2 WHERE id_habilidad = ?1", nativeQuery = true)
    int updateV_down(Long id_usuario,Long nuevo);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE habilidad SET descripcion = ?2 WHERE id_habilidad = ?1", nativeQuery = true)
    int updateDescripcion(Long id_usuario,String nuevo);
    
    


}