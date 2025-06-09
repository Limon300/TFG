/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.entorno.repositories;

import com.example.entorno.dto.SesionData;
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
create table sesion (
id_poi BIGINT,
  id_sesion BIGINT,
  x BIGINT not null,
  y BIGINT not null,
  id_poi_store BIGINT not null,
);
*/
@Repository
public interface POIRepository extends JpaRepository<POIModelo, Long>{
    
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO poi (id_poi, id_sesion, X, Y, id_poi_store) VALUES (?1, ?2, ?3, ?4, ?5)", nativeQuery = true)
    int insertar(Long id_poi, Long id_sesion, Long X, Long Y, Long id_poi_store);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM poi WHERE id_sesion = ?1", nativeQuery = true)
    public void deleteSesion(Long sessionId);

    @Transactional
    @Modifying
    @Query(value = "SELECT id_poi,id_poi_store FROM poi WHERE id_sesion = ?1 AND X=?2 AND Y=?3", nativeQuery = true)
    public List<Map<String, Long>> findByPosicion(Long sesion, Long x, Long y);

}