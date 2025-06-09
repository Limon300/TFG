/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.store.repositories;

import com.example.store.models.UsuarioModelo;
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
public interface UsuarioRepository extends JpaRepository<UsuarioModelo, Long>{
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO usuario (id, nombre, apellidos, telegram, dni, status, grupo, password,token) VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8,?9)", nativeQuery = true)
    int insertar(int id_usuario, String nombre, String apellidos, String telegram, String DNI, String status, String grupo, String password,String token);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE usuario SET nombre = ?2 WHERE id = ?1", nativeQuery = true)
    int updateNombre(Long id_usuario,String nuevo);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE usuario SET apellidos = ?2 WHERE id = ?1", nativeQuery = true)
    int updateApellidos(Long id_usuario,String nuevo);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE usuario SET telegram = ?2 WHERE id = ?1", nativeQuery = true)
    int updateTelegram(Long id_usuario,String nuevo);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE usuario SET dni = ?2 WHERE id = ?1", nativeQuery = true)
    int updateDNI(Long id_usuario,String nuevo);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE usuario SET status = ?2 WHERE id = ?1", nativeQuery = true)
    int updateStatus(Long id_usuario,String nuevo);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE usuario SET grupo = ?2 WHERE id = ?1", nativeQuery = true)
    int updateGrupo(Long id_usuario,String nuevo);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE usuario SET password = ?2 WHERE id = ?1", nativeQuery = true)
    int updatePassword(Long id_usuario,String nuevo);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE usuario SET token = ?2 WHERE id = ?1", nativeQuery = true)
    int updateToken(Long id_usuario,String nuevo);
}