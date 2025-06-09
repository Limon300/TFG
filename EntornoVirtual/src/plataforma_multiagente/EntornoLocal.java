/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package plataforma_multiagente;

import com.fasterxml.jackson.core.type.TypeReference;
import plataforma_multiagente.datos.TokenResponse;
import plataforma_multiagente.datos.MapaResponse;
import plataforma_multiagente.GUI.GUI;

//import jackson-databind-2.18.0-rc1-javadoc.jar.com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import plataforma_multiagente.datos.ActionRequest;
import plataforma_multiagente.datos.ActionResponse;
import plataforma_multiagente.datos.Agente;
import plataforma_multiagente.datos.AgenteStoreResponse;
import plataforma_multiagente.datos.Casilla;
import plataforma_multiagente.datos.SesionUserStart;
import plataforma_multiagente.datos.LogResponse;
import plataforma_multiagente.datos.ObjetivoSesionResponse;
import plataforma_multiagente.datos.SensorResponse;
import plataforma_multiagente.datos.SesionData;
import plataforma_multiagente.datos.SesionPostRequest;
import plataforma_multiagente.datos.SesionStart;
import plataforma_multiagente.datos.SesionUserRequest;
import plataforma_multiagente.datos.UsuarioSesionData;
public class EntornoLocal {
    // constructor
    
    TokenResponse token;
    private List<MapaResponse> mapas;
    private MapaResponse mapa_actual;
    GUI ventana;
    String IP;
    //Lista de agentes
    private List<AgenteStoreResponse> agentes;
    //Nuestro agentes
    private List<Agente> agentes_actuales;
    //Otros agentes
    private List<Agente> otros_agentes;
    private int cont_agentes;
    private int ciclo;
    private int espera;
    private Long sesion_actual;
    private Long usuario;
    private List<SesionData> sesiones_actuales;
    

    public EntornoLocal() {
        sesion_actual = null;
        cont_agentes = 1;
        espera = -1;
        ciclo = -1;
        mapas = new ArrayList();
        mapa_actual = null;
        agentes_actuales = new ArrayList();
        otros_agentes = new ArrayList();
        IP = "localhost:9000";
        ventana = new GUI(this);
    }

    public EntornoLocal(String IP) {
        sesion_actual = null;
        cont_agentes = 1;
        espera = -1;
        ciclo = -1;
        mapas = new ArrayList();
        mapa_actual = null;
        agentes_actuales = new ArrayList();
        otros_agentes = new ArrayList();
        this.IP = IP;
        ventana = new GUI(this);
    }
    
    public EntornoLocal(String IP,Long id,String password) {
        sesion_actual = null;
        cont_agentes = 1;
        espera = -1;
        ciclo = -1;
        mapas = new ArrayList();
        mapa_actual = null;
        agentes_actuales = new ArrayList();
        otros_agentes = new ArrayList();
        this.IP = IP;
        ventana = new GUI(this,id,password);
    }
    
    //Se invoca cuando se quiere cambiar de sesion
    public void CambioSesion(){
        sesion_actual = null;
        cont_agentes = 1;
        espera = -1;
        ciclo = -1;
        mapas = new ArrayList();
        mapa_actual = null;
        agentes_actuales = new ArrayList();
        otros_agentes = new ArrayList();
        agentes = null;
        usuario = null;
        sesiones_actuales = null;
        
        token = null;
    }
    
    public boolean RefreshToken(){
        URL url;
        
        if(token != null && token.getExpiration().before(Timestamp.valueOf(LocalDateTime.now().plusMinutes(5)))){
            try {
                url = new URL("http://"+IP+"/api/refresh");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Authorization",token.getRefreshToken());
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                con.setDoOutput(true);
                String jsonInputString = "";
                try(OutputStream os = con.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);			
                }
                try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    ObjectMapper objectMapper = new ObjectMapper();                
                    token = objectMapper.readValue(response.toString(),TokenResponse.class);
                    return true;
                }
                catch(Exception e){
                    return false;
                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(EntornoLocal.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            } catch (IOException ex) {
                Logger.getLogger(EntornoLocal.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }

        }
        else{
            return true;
        }
    }
    
    
    public boolean LoginConection(Long id,String Password) {
        URL url;
        
        try {
            url = new URL("http://"+IP+"/api/login");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            String jsonInputString = "{\"id\": \""+id+"\", \"password\": \""+Password+"\"}";
            try(OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);			
            }
            try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                ObjectMapper objectMapper = new ObjectMapper();                
                token = objectMapper.readValue(response.toString(),TokenResponse.class);
                usuario = id;
                return true;
            }
            catch(Exception e){
                return false;
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(EntornoLocal.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(EntornoLocal.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }
    
    public Optional<MapaResponse> PedirMapa(Long i){
        if(this.RefreshToken()){
            URL url;
            try {
                url = new URL("http://"+IP+"/api/mapas/"+i);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Authorization",token.getAccessToken());
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    ObjectMapper objectMapper = new ObjectMapper();                
                    MapaResponse mapa = objectMapper.readValue(response.toString(),MapaResponse.class);

                    return Optional.of(mapa);

                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(EntornoLocal.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error_1");
                return Optional.empty();
            } catch (IOException ex) {
                Logger.getLogger(EntornoLocal.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error_2");
                return Optional.empty();
            }
        }
        else{
            return Optional.empty();
        }
    }
    
    public boolean PedirMapas(){
        if(this.RefreshToken()){
            URL url;
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                url = new URL("http://"+IP+"/api/mapas");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Authorization",token.getAccessToken());
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                //Respuesta
                try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    List<MapaResponse> mapa = objectMapper.readValue(response.toString(),new TypeReference<List<MapaResponse>>(){});
                    this.mapas = mapa;
                    return true;
                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(EntornoLocal.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error_1");
                return false;
            } catch (IOException ex) {
                Logger.getLogger(EntornoLocal.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error_2");
                return false;
            }
        }
        else{
            return false;
        }
    }
    
    public boolean PedirAgentes(){
        if(this.RefreshToken()){
            URL url;
            try {
                url = new URL("http://"+IP+"/api/agentes");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Authorization",token.getAccessToken());
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    ObjectMapper objectMapper = new ObjectMapper();       

                    List<AgenteStoreResponse> agente = objectMapper.readValue(response.toString(),new TypeReference<List<AgenteStoreResponse>>(){});
                    this.agentes = agente;
                    return true;
                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(EntornoLocal.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error_1");
                return false;
            } catch (IOException ex) {
                Logger.getLogger(EntornoLocal.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error_2");
                return false;
            }
        }
        else{
            return false;
        }
    }
    
    public Optional<List<Casilla>> PedirCasillas(){
        if(this.RefreshToken()){
            URL url;
            try {
                url = new URL("http://"+IP+"/api/mapas/"+this.mapa_actual.getId_mapa()+"/casillas");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Authorization",token.getAccessToken());
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    ObjectMapper objectMapper = new ObjectMapper();       

                    List<Casilla> mapa = objectMapper.readValue(response.toString(),new TypeReference<List<Casilla>>(){});

                    return Optional.of(mapa);
                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(EntornoLocal.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error_1");
                return Optional.empty();
            } catch (IOException ex) {
                Logger.getLogger(EntornoLocal.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error_2");
                return Optional.empty();
            }
        }
        else{
            return Optional.empty();
        }
    }
    
    public Optional<List<LogResponse>> PedirLogs(Timestamp since){
        if(this.RefreshToken()){
            ObjectMapper objectMapper = new ObjectMapper();  
            URL url;
            try {
                url = new URL("http://"+IP+"/api/sesion/"+this.sesion_actual+"/logs/"+since.getTime());
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Authorization",token.getAccessToken());
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");


                //Respuesta
                try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }     

                    List<LogResponse> cambios  = objectMapper.readValue(response.toString(),new TypeReference<List<LogResponse>>(){});

                    return Optional.of(cambios);
                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(EntornoLocal.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error_1");
                return Optional.empty();
            } catch (IOException ex) {
                Logger.getLogger(EntornoLocal.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error_2");
                return Optional.empty();
            }
        }
        else{
            return Optional.empty();
        }
    }
    
    public boolean PedirCrearSesion(){
        if(this.RefreshToken()){
            ObjectMapper objectMapper = new ObjectMapper();  
            URL url;
            try {
                url = new URL("http://"+IP+"/api/sesion");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Authorization",token.getAccessToken());
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                //Mensaje
                con.setDoOutput(true);
                String jsonInputString = objectMapper.writeValueAsString(
                        new SesionPostRequest(
                                this.agentes_actuales,
                                this.mapa_actual.getId_mapa(),
                                this.ciclo,
                                this.espera
                        )
                );
                try(OutputStream os = con.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);			
                }
                //Respuesta
                try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }     

                    SesionStart info_sesion = objectMapper.readValue(response.toString(),SesionStart.class);

                    for(int i=0;i<this.agentes_actuales.size();i++){
                       this.agentes_actuales.get(i).setId_juego(
                            info_sesion.getId_agentes_sesion().get(Long.valueOf(this.agentes_actuales.get(i).getId_store())).pop()
                       ); 
                    }
                    this.sesion_actual = info_sesion.getId_sesion();
                    /*
                    for(int i=0;i<this.agentes_actuales.size();i++){
                        System.out.println(agentes_actuales.get(i).getNombre_juego() + " " + agentes_actuales.get(i).getId_store()
                        + " " + agentes_actuales.get(i).getId_juego());
                    }
                    System.out.println(this.sesion_actual);
                    */
                    return true;
                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(EntornoLocal.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error_1");
                return false;
            } catch (IOException ex) {
                Logger.getLogger(EntornoLocal.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error_2");
                return false;
            }
        }
        else{
            return false;
        }
    }
    
    public boolean PedirVerSesion(){
        if(this.RefreshToken()){
            ObjectMapper objectMapper = new ObjectMapper();  
            URL url;
            if(token != null){
                try {
                    url = new URL("http://"+IP+"/api/sesion");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("Authorization",token.getAccessToken());
                    con.setRequestProperty("Content-Type", "application/json");
                    con.setRequestProperty("Accept", "application/json");
                    //Respuesta
                    try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                        StringBuilder response = new StringBuilder();
                        String responseLine;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }     

                        List<SesionData> sesiones = objectMapper.readValue(response.toString(),new TypeReference<List<SesionData>>(){});

                        this.sesiones_actuales = sesiones;
                        /*
                        for(int i=0;i<this.agentes_actuales.size();i++){
                            System.out.println(agentes_actuales.get(i).getNombre_juego() + " " + agentes_actuales.get(i).getId_store()
                            + " " + agentes_actuales.get(i).getId_juego());
                        }
                        System.out.println(this.sesion_actual);
                        */
                        return true;
                    }
                } catch (MalformedURLException ex) {
                    Logger.getLogger(EntornoLocal.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Error_1");
                    return false;
                } catch (IOException ex) {
                    Logger.getLogger(EntornoLocal.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Error_2");
                    return false;
                }
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    public Long getSesion_actual() {
        return sesion_actual;
    }

    public void setSesion_actual(Long sesion_actual) {
        this.sesion_actual = sesion_actual;
    }

    public MapaResponse getMapa_actual() {
        return mapa_actual;
    }
    
    

    public List<SesionData> getSesiones_actuales() {
        return sesiones_actuales;
    }

    public void setSesiones_actuales(List<SesionData> sesiones_actuales) {
        this.sesiones_actuales = sesiones_actuales;
    }
    
    public void setTiempos(int ciclo,int espera){
        this.espera = espera;
        this.ciclo = ciclo;
    }
    
    public MapaResponse getMapa(int i){
        return mapas.get(i);
    }

    public boolean setMapa_actual(int mapa_actual) {
        if(mapa_actual >= 0 && mapa_actual < this.mapas.size()){
            this.mapa_actual = this.mapas.get(mapa_actual);
            return true;
        }
        else{
            return false;
        }
    }

    public List<MapaResponse> getMapas() {
        return mapas;
    }
    
    public AgenteStoreResponse getAgente(int i){
        return agentes.get(i);
    }
    
    public List<AgenteStoreResponse> getAgentes() {
        return agentes;
    }
    
    public void Reset(){
        this.mapa_actual = null;
        this.agentes_actuales = new ArrayList();
        this.otros_agentes = new ArrayList();
        this.cont_agentes = 1;
        this.sesion_actual = null;
        espera = -1;
        ciclo = -1;
    }
    
    
    
    public Optional<AgenteStoreResponse> PedirAgente(Long id){
        if(this.RefreshToken()){
            URL url;
            try {
                url = new URL("http://"+IP+"/api/agentes/"+id);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Authorization",token.getAccessToken());
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    ObjectMapper objectMapper = new ObjectMapper();       

                    AgenteStoreResponse agente = objectMapper.readValue(response.toString(),AgenteStoreResponse.class);
                    return Optional.of(agente);
                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(EntornoLocal.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error_1");
                return Optional.empty();
            } catch (IOException ex) {
                Logger.getLogger(EntornoLocal.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error_2");
                return Optional.empty();
            }
        }
        else{
            return Optional.empty();
        }
    }
    
    public boolean loadSesion_actual(int i){
        if(this.RefreshToken()){
            Optional<MapaResponse> sesion_mapa = this.PedirMapa(this.sesiones_actuales.get(i).getId_mapa());
            if(sesion_mapa.isPresent()){
                this.mapa_actual = sesion_mapa.get();
                this.agentes_actuales = new ArrayList();
                this.otros_agentes = new ArrayList();
                this.cont_agentes = 1;
                //i -> sesion
                //j -> bucle usuarios
                for(int j=0;j<this.sesiones_actuales.get(i).getId_agentes_sesion().size();j++){
                    //añadimos nuestros agentes, quizas necesitemos los demas
                    if(this.sesiones_actuales.get(i).getId_agentes_sesion().get(j).getId_usuario().equals(usuario)){
                        //usuario i j
                        UsuarioSesionData nuestro = this.sesiones_actuales.get(i).getId_agentes_sesion().get(j);
                        //k -> bucle agentes
                        for(int k=0;k<this.sesiones_actuales.get(i).getId_agentes_sesion().get(j).getId_agentes_sesion().size();k++){
                            Optional<AgenteStoreResponse> agente = this.PedirAgente(nuestro.getId_agentes_sesion().get(k).get("id_agente_store"));
                            if(agente.isPresent()){
                                this.agentes_actuales.add(
                                    new Agente(
                                            "Agente_"+this.cont_agentes,
                                            agente.get(),
                                            nuestro.getId_agentes_sesion().get(k).get("id_agente")
                                ));
                            }
                            else{
                                return false;
                            }
                        }
                    }
                    else{
                        UsuarioSesionData otro = this.sesiones_actuales.get(i).getId_agentes_sesion().get(j);
                        //k -> bucle agentes
                        for(int k=0;k<this.sesiones_actuales.get(i).getId_agentes_sesion().get(j).getId_agentes_sesion().size();k++){
                            Optional<AgenteStoreResponse> agente = this.PedirAgente(otro.getId_agentes_sesion().get(k).get("id_agente_store"));
                            if(agente.isPresent()){
                                this.otros_agentes.add(
                                    new Agente(
                                            "Agente_Otro_"+this.cont_agentes,
                                            agente.get(),
                                            otro.getId_agentes_sesion().get(k).get("id_agente")
                                ));
                            }
                            else{
                                return false;
                            }
                        }
                    }
                }
                //id sesion
                this.sesion_actual = this.sesiones_actuales.get(i).getId_sesion();
                //t espera
                espera = this.sesiones_actuales.get(i).getT_espera().intValue();
                //t ciclo
                ciclo = this.sesiones_actuales.get(i).getT_ciclo().intValue();
            }
            return true;
        }
        else{
            return false;
        }
    }
 
    
    
    public boolean addAgentesActuales(int agente_actual) {
        if(agente_actual >= 0 && agente_actual < this.agentes.size()){
            String nuevo = "Agente_" + cont_agentes;
            cont_agentes ++;
            this.agentes_actuales.add(new Agente(nuevo,this.agentes.get(agente_actual)));
            return true;
        }
        else{
            return false;
        }
    }
    
    public boolean removeAgentesActuales(int agente_actual) {
        if(agente_actual >= 0 && agente_actual < this.agentes_actuales.size()){
            this.agentes_actuales.remove(agente_actual);
            return true;
        }
        else{
            return false;
        }
    }
    
    public Agente getAgenteActual(int i) {
        return this.agentes_actuales.get(i);
    }

    public List<Agente> getAgentesActuales() {
        return this.agentes_actuales;
    }
    
    public Agente getOtroAgente(int i) {
        return this.otros_agentes.get(i);
    }

    public List<Agente> getOtrosAgentes() {
        return this.otros_agentes;
    }
//@DeleteMapping("/sesion/{id}")
    public boolean PedirEliminarSesion() {
        if(this.RefreshToken()){
            URL url;
            try {
                url = new URL("http://"+IP+"/api/sesion/"+this.sesion_actual);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("DELETE");
                con.setRequestProperty("Authorization",token.getAccessToken());
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    return true;
                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(EntornoLocal.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error_1");
                return this.PedirAbandonarSesion();
            } catch (IOException ex) {
                Logger.getLogger(EntornoLocal.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error_2");
                return this.PedirAbandonarSesion();
            }
        }
        else{
            return false;
        }
    }
    //Esto hay que probarlo
    public boolean PedirAbandonarSesion() {
        if(this.RefreshToken()){
            URL url;
            try {
                url = new URL("http://"+IP+"/api/sesion/"+this.sesion_actual+"/"+this.usuario);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("DELETE");
                con.setRequestProperty("Authorization",token.getAccessToken());
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println("Hola");
                    return true;
                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(EntornoLocal.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error_1");
                return false;
            } catch (IOException ex) {
                Logger.getLogger(EntornoLocal.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error_2");
                return false;
            }
        }
        else{
            return false;
        }
    }
    
    
    public SensorResponse PeticionSensor(Long id_agente,Long id_sensor) {
        if(this.RefreshToken()){
            URL url;
            ObjectMapper objectMapper = new ObjectMapper(); 
            try {
                url = new URL("http://"+IP+"/api/sesion/"+this.sesion_actual+"/sensor?id_agente="+id_agente+"&id_sensor="+id_sensor);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Authorization",token.getAccessToken());
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");


                //Respuesta
                try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }     

                    SensorResponse informacion  = objectMapper.readValue(response.toString(),SensorResponse.class);

                    return informacion;
                }

            } catch (MalformedURLException ex) {
                Logger.getLogger(EntornoLocal.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            } catch (IOException ex) {
                Logger.getLogger(EntornoLocal.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }
        else{
            return null;
        }
    }
    
    public boolean PedirInvitar(SesionUserRequest peticion){
        if(this.RefreshToken()){
            ObjectMapper objectMapper = new ObjectMapper();  
            URL url;
            try {
                url = new URL("http://"+IP+"/api/sesion/"+this.sesion_actual+"/usuario");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Authorization",token.getAccessToken());
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                //Mensaje
                con.setDoOutput(true);
                String jsonInputString = objectMapper.writeValueAsString(
                        peticion
                );
                try(OutputStream os = con.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);			
                }
                //Respuesta
                try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }     

                    SesionUserStart info_accion = objectMapper.readValue(response.toString(),SesionUserStart.class);

                    return true;
                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(EntornoLocal.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            } catch (IOException ex) {
                Logger.getLogger(EntornoLocal.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        else{
            return false;
        }
    }
    
    public ActionResponse PeticionHabilidad(Long id_agente,Long id_habilidad){
        if(this.RefreshToken()){
            ObjectMapper objectMapper = new ObjectMapper();  
            URL url;
            try {
                url = new URL("http://"+IP+"/api/sesion/"+this.sesion_actual+"/habilidad");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Authorization",token.getAccessToken());
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                //Mensaje
                con.setDoOutput(true);
                String jsonInputString = objectMapper.writeValueAsString(
                        new ActionRequest(
                                id_agente,
                                id_habilidad
                        )
                );
                try(OutputStream os = con.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);			
                }
                //Respuesta
                try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }     

                    ActionResponse info_accion = objectMapper.readValue(response.toString(),ActionResponse.class);

                    return info_accion;
                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(EntornoLocal.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            } catch (IOException ex) {
                Logger.getLogger(EntornoLocal.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }
        else{
            return null;
        }
    }
    
    public List<ObjetivoSesionResponse> PeticionObjetivos() {
        if(this.RefreshToken()){
            URL url;
            ObjectMapper objectMapper = new ObjectMapper(); 
            try {
                url = new URL("http://"+IP+"/api/sesion/"+this.sesion_actual+"/objetivos");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Authorization",token.getAccessToken());
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");


                //Respuesta
                try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }     

                    List<ObjetivoSesionResponse> informacion  = objectMapper.readValue(response.toString(),new TypeReference<List<ObjetivoSesionResponse>>(){});

                    return informacion;
                }

            } catch (MalformedURLException ex) {
                Logger.getLogger(EntornoLocal.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            } catch (IOException ex) {
                Logger.getLogger(EntornoLocal.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }
        else{
            return null;
        }
    }

}
