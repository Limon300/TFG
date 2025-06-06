/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.entorno.repositories;

import com.example.entorno.models.SensorModelo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author usuario
 */

/*
create table sensor (
  id_agente BIGINT,
  id_sensor BIGINT,
  id_sensor_store BIGINT not null,
  valor BIGINT,
  constraint claves_s primary key (id_sensor),
  constraint clave_sa foreign key (id_agente) references agentes(id_agente)
);
*/
@Repository
public interface SensorRepository extends JpaRepository<SensorModelo, Long>{
    
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO sensor "
            + "(id_agente, id_sensor, id_sensor_store, valor) "
            + "VALUES (?1, ?2, ?3, ?4)", nativeQuery = true)
    int insertar(Long id_agente, Long id_sensor, Long id_sensor_store, Long valor);
    
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM sensor where id_agente in (select id_agente from agentes where id_sesion = ?1)", nativeQuery = true)
    public void deleteSensorBySesion(Long sessionId);
    
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM sensor where id_agente in (select id_agente from agentes where id_usuario = ?1)", nativeQuery = true)
    public void deleteSensorByUsuario(Long user_id);

}