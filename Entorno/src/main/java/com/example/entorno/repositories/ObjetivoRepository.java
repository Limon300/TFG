/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.entorno.repositories;

import com.example.entorno.models.ObjetivoModelo;
import com.example.entorno.models.POIModelo;
import jakarta.transaction.Transactional;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author usuario
 */

/*
create table actual (
  id_sesion BIGINT,
  id_objetivo BIGINT,
  constraint claves_ac primary key (id_sesion),
  constraint clave_aco foreign key (id_objetivo) references objetivos(id_objetivo),
  constraint clave_acs foreign key (id_sesion) references sesion(id_sesion)
);
*/
@Repository
public interface ObjetivoRepository extends JpaRepository<ObjetivoModelo, Long>{
    
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO objetivos (id_sesion, id_objetivo, estatus, compartido, id_objetivo_store, X, Y) "
            + "VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7)", nativeQuery = true)
    int insertar(Long id_sesion, Long id_objetivo, String estatus, Long compartido, Long id_objetivo_store, Long X, Long Y);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO actual (id_sesion, id_objetivo) "
            + "VALUES (?1, ?2)", nativeQuery = true)
    public void insertarActual(long sesion_id, Long nuevo_id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM objetivos WHERE id_sesion = ?1", nativeQuery = true)
    public void deleteSesion(Long sessionId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM actual WHERE id_sesion = ?1", nativeQuery = true)
    public void deleteActual(Long sessionId);
    
    @Query(value = "SELECT id_objetivo FROM objetivos WHERE id_sesion = ?1 AND estatus = 'on'", nativeQuery = true)
    public List<Long> findOn(Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE objetivos SET estatus = ?2 WHERE id_objetivo = ?1", nativeQuery = true)
    public void updateObjetivoStatus(Long anterior, String off);

    /**
     * Para cuando necesitamos los objetivos actuales de una sesion
     * @param sesion_id
     * @return los objetivos actuales
     */
    @Query(value = "SELECT id_objetivo FROM actual WHERE id_sesion = ?1", nativeQuery = true)
    public List<Long> getActual(Long sesion_id);
    
    /**
     * Para cuando necesitamos los objetivos actuales de una sesion
     * @param sesion_id
     * @return los objetivos actuales
     */
    @Query(value = "SELECT * FROM objetivos WHERE id_sesion = ?1", nativeQuery = true)
    public List<Map<String,Object>> findBySesion(Long sesion_id);


}