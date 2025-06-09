/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.entorno.repositories;

import com.example.entorno.models.SesionModelo;
import jakarta.transaction.Transactional;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author usuario
 */

/*
create table sesion (
  id_sesion BIGINT,
  id_usuario BIGINT,
  constraint claves_su primary key (id_sesion,id_usuario),
  constraint clave_sus foreign key (id_sesion) references sesion(id_sesion),
  constraint clave_suu foreign key (id_usuario) references usuarios(id_usuario)
);
*/
@Repository
public interface SesionRepository extends JpaRepository<SesionModelo, Long>{
    
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO sesion (id_sesion, id_mapa, t_inicio, t_ultimo, t_espera, t_ciclo) VALUES (?1, ?2, ?3, ?4, ?5, ?6)", nativeQuery = true)
    int insertar(Long IdSes, Long IdMap, Timestamp t_in, Timestamp t_u, Long t_esp, Long t_ciclo);
    
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO sesion_usuarios (id_sesion, id_usuario) VALUES (?1, ?2)", nativeQuery = true)
    int insertar_usr(Long IdSes, Long IdUsr);
    
    @Query(value = "SELECT id_usuario FROM sesion_usuarios WHERE id_sesion = ?1", nativeQuery = true)
    List<String> obtener_usuarios(Long IdSes);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM sesion_usuarios WHERE id_sesion = ?1", nativeQuery = true)
    public void borrarUsr(Long IdSes);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM sesion WHERE id_sesion = ?1", nativeQuery = true)
    public void borrarSesion(Long sessionId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM sesion_usuarios WHERE id_sesion = ?1 AND id_usuario = ?2", nativeQuery = true)
    public void deleteSesionUserByUserIdAndSesionId(Long sessionId, Long userId);

    @Query(value = "SELECT id_sesion FROM sesion_usuarios WHERE id_usuario = ?1", nativeQuery = true)
    public List<Long> findByUsuario(Long agente_id);

}