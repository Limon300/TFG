/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.store.repositories;

import com.example.store.models.POIModelo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/*
CREATE TABLE objetivos (
  ID_OBJ INTEGER PRIMARY KEY,
  nombre TEXT NOT NULL,
  DESCRIPCION TEXT,
  VALOR INTEGER
);

*/
@Repository
public interface POIRepository extends JpaRepository<POIModelo, Long>{
    
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO poi (id_poi, nombre, valor, descripcion,categoria) VALUES (?1, ?2, ?3, ?4, ?5)", nativeQuery = true)
    int insertar(Long id, String nombre, Long valor, String descripcion,String categoria);
    
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE poi SET nombre = ?2 WHERE id_poi = ?1", nativeQuery = true)
    int updateNombre(Long id_usuario,String nuevo);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE poi SET valor = ?2 WHERE id_poi = ?1", nativeQuery = true)
    int updateValor(Long id_usuario,Long nuevo);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE poi SET descripcion = ?2 WHERE id_poi = ?1", nativeQuery = true)
    int updateDescripcion(Long id_usuario,String nuevo);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE poi SET categoria = ?2 WHERE id_poi = ?1", nativeQuery = true)
    int updateCategoria(Long id_usuario,String nuevo);



}