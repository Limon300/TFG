/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.entorno.repositories;

import com.example.entorno.models.UsuariosModelo;
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
public interface UsuarioRepository extends JpaRepository<UsuariosModelo, Long>{
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO usuarios (id_usuario, jwt, jwt_estado) VALUES (?1, ?2, ?3)", nativeQuery = true)
    int insertar(Long agentId, String a,String b);
    @Transactional
    @Modifying
    @Query(value = "UPDATE usuarios SET jwt = ?2, jwt_estado = ?3 WHERE id_usuario = ?1", nativeQuery = true)
    int actualizar(Long agentId, String a,String b);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM usuarios WHERE id_usuario = ?1", nativeQuery = true)
    public void deleteUserById(Long user_id);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM sesion_usuarios WHERE id_usuario = ?1", nativeQuery = true)
    public void deleteSesionUserByUserId(Long userId);
}