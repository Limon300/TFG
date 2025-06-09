/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.entorno.service;

import com.example.entorno.dto.LoginRequest;
import com.example.entorno.dto.TokenResponse;
import com.example.entorno.dto.UsuarioResponse;
import com.example.entorno.models.UsuariosModelo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author usuario
 */
@Service
public class SecurityService {
    @Value("${application.security.jwt.secret-key}")
    private String key;
    @Value("${application.security.jwt.expiration}")
    private long expiration;
    @Value("${application.security.jwt.refresh}")
    private long refresh;
    
    @Autowired
    UsuarioService servicio;
    
    @Autowired
    SesionService servicioSesion;
    
    public String Password_Hash(String raw){
        PasswordEncoder codificador = new BCryptPasswordEncoder();
        return codificador.encode(raw);
    }
    
    public boolean Password_Verify(String raw,String hashed){
        PasswordEncoder codificador = new BCryptPasswordEncoder();
        return codificador.matches(raw, hashed);
    }
    
    public Optional<TokenResponse> login(LoginRequest peticion){
        
        Optional<UsuarioResponse> datos = servicio.getFromStore(peticion.getId());
        if(datos.isEmpty()){
            return Optional.empty();
        }
        else{
            if(this.Password_Verify(peticion.getPassword(), datos.get().getPassword())){
                String token = generateToken(peticion.getId().toString());
                TokenResponse respuesta = new TokenResponse(
                        token,
                        this.generateRefreshToken(peticion.getId().toString()),
                        new Timestamp(extractExpiration(token).getTime())
                );
                if(servicio.isLocal(peticion.getId())){
                    servicio.updateSesionUsuario(peticion.getId(),respuesta.accessToken(),respuesta.refreshToken());
                }
                else{
                    servicio.saveSesionUsuario(peticion.getId(),respuesta.accessToken(),respuesta.refreshToken());
                }
                System.out.print(extractExpiration(respuesta.accessToken()));
                return Optional.of(respuesta);
            }
            else{
                return Optional.empty();
            }
        }

    }
    
    public String generateToken(String id){
        String salida = Jwts.builder()
                .id(id)
                .subject(id)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getNewKey())
                .compact();
        return salida;
    }
    
    public String generateRefreshToken(String id){
        String salida = Jwts.builder()
                .id(id)
                .subject(id)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + refresh))
                .signWith(getNewKey())
                .compact();
        return salida;
    }
    
    public SecretKey getNewKey(){
        byte[] keyb = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyb);
    }
    
    public boolean isTokenValidOpenAccess(String token) {
        final Long username = Long.decode(extractId(token));
        return (servicio.getFromStore(username).isPresent() && !isTokenExpired(token));
    }
    
    public boolean isTokenValidRestricted(String token, Long usuario) {
        final String username = extractId(token);
        if(this.servicio.isLocal(usuario)){
            return ((username.equals(String.valueOf(usuario))) && !isTokenExpired(token));
        }
        else{
            return false;
        }
    }
    
    public boolean isTokenValidSesion(String token, Long sessionId) {
        final String username = extractId(token);
        List<String> autorizados = this.servicioSesion.GetUsuarios(sessionId);
        return (autorizados.contains(username) && !isTokenExpired(token));
    }
    
    public boolean isTokenValidAdmin(String token) {
        final String username = extractId(token);
        String adm = servicio.getDataFromStore(Long.decode(username)).get().getStatus();
        return adm.equalsIgnoreCase("ADM") && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return Jwts.parser()
                .verifyWith(getNewKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
    }
    
    public String extractId(String token) {
        return Jwts.parser()
                .verifyWith(getNewKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
    
    

    public ResponseEntity logout(Long user_id) {
        return this.servicio.deleteUsuario(user_id);
    }

    public Optional<TokenResponse> refresh(String token) {
        String id = this.extractId(token);
        Optional<TokenResponse> respuesta;
        Optional<UsuariosModelo> usr = this.servicio.getSesionUsuario(Long.valueOf(id));
        if(usr.isPresent()){
            if(!this.isTokenExpired(token) && (token == null ? usr.get().getJwt_estado() == null : token.equals(usr.get().getJwt_estado()))){
                String new_token = generateToken(id);
                respuesta = Optional.of(
                        new TokenResponse(generateToken(id),this.generateRefreshToken(id),new Timestamp(extractExpiration(new_token).getTime()))
                );
                servicio.updateSesionUsuario(Long.valueOf(id),respuesta.get().accessToken(),respuesta.get().refreshToken());
            }
            else{
                respuesta = Optional.empty();
            }
        }
        else{
            respuesta = Optional.empty();
        }
        return respuesta;
    }

    
}
