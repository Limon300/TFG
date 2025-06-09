/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.store.repositories;

import com.example.store.dto.Casilla;
import com.example.store.models.CasillaModelo;
import com.example.store.models.MapaModelo;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
public interface MapaRepository extends JpaRepository<MapaModelo, Long>{
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO mapa (ID_MAPA, nombre, ENERGIA_IN, X_INICIO, Y_INICIO,DESCRIPCION,X_TAM,Y_TAM) VALUES (?1, ?2, ?3, ?4, ?5,?6,?7,?8)", nativeQuery = true)
    int insertar(Long id, String nombre, Long en,Long X_I,Long Y_I,String descripcion, Long X_T,Long Y_T);
    
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO casilla (X, Y, ID_MAPA, Z, COSTE,APARIENCIA) VALUES (?1, ?2, ?3, ?4, ?5,?6)", nativeQuery = true)
    int insertar_casilla(Long x, Long Y, Long ID, Long Z, Long coste, String ap);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE mapa SET nombre = ?2 WHERE ID_MAPA = ?1", nativeQuery = true)
    public void updateNombre(Long Id, String nombre);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE mapa SET ENERGIA_IN = ?2 WHERE ID_MAPA = ?1", nativeQuery = true)
    public void updateEnergia_in(Long Id, Long energia_in);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE mapa SET DESCRIPCION = ?2 WHERE ID_MAPA = ?1", nativeQuery = true)
    public void updateDescripcion(Long Id, String descripcion);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE mapa SET X_INICIO = ?2 WHERE ID_MAPA = ?1", nativeQuery = true)
    public void updateX_inicio(Long Id, Long x_inicio);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE mapa SET Y_INICIO = ?2 WHERE ID_MAPA = ?1", nativeQuery = true)
    public void updateY_inicio(Long Id, Long y_inicio);

    @Transactional
    @Modifying
    @Query(value = "UPDATE mapa SET X_TAM = ?2 WHERE ID_MAPA = ?1", nativeQuery = true)
    public void updateX_tam(Long Id, Long x_tam);

    @Transactional
    @Modifying
    @Query(value = "UPDATE mapa SET Y_TAM = ?2 WHERE ID_MAPA = ?1", nativeQuery = true)
    public void updateY_tam(Long Id, Long y_tam);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM casilla WHERE ID_MAPA = ?1", nativeQuery = true)
    public void eliminar_casillas(Long Id);
    
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM cas_obj WHERE ID_MAPA = ?1", nativeQuery = true)
    public void eliminar_objetivos(Long Id);
    
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM cas_poi WHERE ID_MAPA = ?1", nativeQuery = true)
    public void eliminar_POIs(Long Id);
    
    @Query(value = "SELECT * FROM casilla WHERE ID_MAPA = ?1 AND X = ?2 AND Y = ?3", nativeQuery = true)
    public List<Map<String, Object>> findCasilla(Long Id, Long X, Long Y);
    
    @Query(value = "SELECT * FROM casilla WHERE ID_MAPA = ?1", nativeQuery = true)
    public List<Map<String, Object>> findCasillas(Long Id);
    
    @Query(value = "SELECT * FROM cas_obj as a join objetivos as b on a.id_obj = b.id_obj WHERE ID_MAPA = ?1 AND X = ?2 AND Y = ?3", nativeQuery = true)
    public List<Map<String, Object>> findObjetivos(Long Id, Long X, Long Y);
    
    @Query(value = "SELECT * FROM cas_poi as a join poi as b on a.id_poi = b.id_poi WHERE ID_MAPA = ?1 AND X = ?2 AND Y = ?3", nativeQuery = true)
    public List<Map<String, Object>> findPOIs(Long Id, Long X, Long Y);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE casilla SET Z = ?4 WHERE ID_MAPA = ?1 AND X = ?2 AND Y = ?3", nativeQuery = true)
    public void updateCasillaZ(Long Id, Long X, Long Y, Long z);

    @Transactional
    @Modifying
    @Query(value = "UPDATE casilla SET COSTE = ?4 WHERE ID_MAPA = ?1 AND X = ?2 AND Y = ?3", nativeQuery = true)
    public void updateCasillaCoste(Long Id, Long X, Long Y, Long coste);

    @Transactional
    @Modifying
    @Query(value = "UPDATE casilla SET APARIENCIA = ?4 WHERE ID_MAPA = ?1 AND X = ?2 AND Y = ?3", nativeQuery = true)
    public void updateCasillaApariencia(Long Id, Long X, Long Y, String apariencia);
    
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO cas_obj (X, Y, ID_MAPA, id_obj) VALUES (?1, ?2, ?3, ?4)", nativeQuery = true)
    public void updateCasillaObjetivo(Long X, Long Y, Long ID_m,Long ID_e);
    
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO cas_poi (X, Y, ID_MAPA, id_poi) VALUES (?1, ?2, ?3, ?4)", nativeQuery = true)
    public void updateCasillaPOIs(Long X, Long Y, Long ID_m,Long ID_e);

    @Query(value = "SELECT * FROM cas_obj WHERE ID_MAPA = ?3 AND X = ?1 AND Y = ?2 AND id_obj = ?4", nativeQuery = true)
    public List<Map<String, Object>> findObjetivo(Long X, Long Y, Long ID_m,Long ID_e);
    
    @Query(value = "SELECT * FROM cas_poi WHERE ID_MAPA = ?3 AND X = ?1 AND Y = ?2 AND id_poi = ?4", nativeQuery = true)
    public List<Map<String, Object>> findPOI(Long X, Long Y, Long ID_m,Long ID_e);
    
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM cas_obj WHERE ID_MAPA = ?3 AND X = ?1 AND Y = ?2 AND id_obj = ?4", nativeQuery = true)
    public void RemoveObjetivo(Long X, Long Y, Long ID_m,Long ID_e);
    
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM cas_poi WHERE ID_MAPA = ?3 AND X = ?1 AND Y = ?2 AND id_poi = ?4", nativeQuery = true)
    public void RemovePOI(Long X, Long Y, Long ID_m,Long ID_e);
}