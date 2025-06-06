/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.entorno.repositories;

import com.example.entorno.models.LogModelo;
import jakarta.transaction.Transactional;
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
create table logs (
  id_entrada BIGINT,
  id_sesion BIGINT,
  fecha timestamp not null,
  entrada VARCHAR(255),
  constraint claves_l primary key (id_entrada),
  constraint clave_ls foreign key (id_sesion) references sesion(id_sesion)
);
*/
@Repository
public interface LogRepository extends JpaRepository<LogModelo, Long>{
    
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM logs WHERE id_sesion = ?1", nativeQuery = true)
    public void deleteSesionLogs(Long sessionId);
    /*
    @Column(name = "evento")
    
    @Column(name = "id_sujeto")
    
    @Column(name = "val_1")
    
    @Column(name = "val_2")
    */
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO logs "
            + "(id_entrada, id_sesion, fecha, entrada,evento,id_sujeto,val_1,val_2) "
            + "VALUES (?1, ?2, ?3, ?4,?5,?6,?7,?8)", nativeQuery = true)
    public void insertar(
        long nuevo_id, Long id, Timestamp fecha, String entrada,
        String evento,Long actor,Long val_1,Long val_2);
    
    @Query(value = "SELECT * FROM logs WHERE id_sesion = ?1 AND fecha >= ?2", nativeQuery = true)
    public List<Map<String, Object>> getLogsFrom(Long sessionId, Timestamp fecha_min);

    @Query(value = "SELECT * FROM logs WHERE id_sesion = ?1 AND fecha >= ?2 AND fecha <= ?3", nativeQuery = true)
    public List<Map<String, Object>> getLogsFromTo(Long sessionId, Timestamp fecha_min, Timestamp fecha_max);

    @Query(value = "SELECT * FROM logs WHERE id_sesion = ?1", nativeQuery = true)
    public List<Map<String, Object>> getLogsSesion(Long sessionId);

    @Query(value = "SELECT * FROM logs WHERE id_sesion = ?1 AND fecha <= ?2", nativeQuery = true)
    public List<Map<String, Object>> getLogsUntil(Long sessionId, Timestamp fecha_max);
    


}