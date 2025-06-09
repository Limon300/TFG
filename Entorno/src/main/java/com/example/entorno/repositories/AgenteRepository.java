/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.entorno.repositories;

import com.example.entorno.models.AgenteModelo;
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
create table sesion (
  id_agente BIGINT,
  id_sesion BIGINT,
  id_usuario BIGINT,
  id_agente_store BIGINT not null,
  x BIGINT not null,
  y BIGINT not null,
  energia BIGINT not null,
  constraint claves_a primary key (id_agente),
  constraint clave_as foreign key (id_sesion) references sesion(id_sesion),
  constraint clave_au foreign key (id_usuario) references usuarios(id_usuario)
);
*/
@Repository
public interface AgenteRepository extends JpaRepository<AgenteModelo, Long>{
    
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO agentes "
            + "(id_agente, id_sesion, id_usuario, id_agente_store, x, y, energia) "
            + "VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7)", nativeQuery = true)
    int insertar(Long id_agente, Long id_sesion, Long id_usuario, Long id_agente_store, Long x, Long y, Long energia);

    @Query(value = "SELECT id_agente_store,id_agente FROM agentes WHERE id_sesion = ?1 AND id_usuario = ?2", nativeQuery = true)
    public List<Map<String,Long>> obtenerAgente(Long sessionId,Long id_usuario);
    
    @Query(value = "SELECT * FROM agentes WHERE id_sesion = ?1 AND id_usuario = ?2", nativeQuery = true)
    public List<AgenteModelo> obtenerAgenteFull(Long sessionId,Long id_usuario);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM agentes WHERE id_sesion = ?1", nativeQuery = true)
    public void deleteAgenteBySesion(Long sessionId);
    
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM alcanzado WHERE id_agente in (select id_agente from agentes where id_sesion = ?1)", nativeQuery = true)
    public void deleteObjetivosBySesion(Long sessionId);
    
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO alcanzado (id_objetivo,id_agente) VALUES (?1,?2)", nativeQuery = true)
    public void insertAlcanzado(Long id_objetivo,Long id_agente);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE agentes SET x = ?2, y = ?3, energia = ?4 WHERE id_agente = ?1", nativeQuery = true)
    public void updatePos(Long id_agente, Long x, long y, long coste);

    @Transactional
    @Modifying
    @Query(value = "UPDATE agentes SET energia = ?2 WHERE id_agente = ?1", nativeQuery = true)
    public void updateEnergia(Long agente_id, Long energia);

    @Query(value = "SELECT energia FROM agentes WHERE id_agente = ?1", nativeQuery = true)
    public List<Long> getEnergia(Long agente_id);
    
    @Query(value = "SELECT t_ultimo FROM agentes WHERE id_agente = ?1", nativeQuery = true)
    public List<Timestamp> getUltimo(Long agente_id);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE agentes SET t_ultimo = ?2 WHERE id_agente = ?1", nativeQuery = true)
    public void setUltimo(Long agente_id,Timestamp nuevo);
    
    @Query(value = "SELECT id_agente FROM alcanzado WHERE id_objetivo = ?1", nativeQuery = true)
    public List<Long> findAlcanzadosByObjetivoId(Long objetivo_id);
    
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM alcanzado WHERE id_agente in (select id_agente from agentes where id_usuario = ?1)", nativeQuery = true)
    public void deleteObjetivosByUsuario(Long user_id);
    
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM agentes WHERE id_usuario = ?1", nativeQuery = true)
    public void deleteAgenteByUsuario(Long user_id);
    
    
}