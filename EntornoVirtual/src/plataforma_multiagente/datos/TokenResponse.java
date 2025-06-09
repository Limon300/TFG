/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plataforma_multiagente.datos;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 *
 * @author usuario
 */
public class TokenResponse
        
{
    @JsonProperty("access_token")
    String accessToken;
    @JsonProperty("refresh_token")
    String refreshToken;
    @JsonProperty("expiration")
    Timestamp expiration;

    public TokenResponse() {
    }

    public TokenResponse(String accessToken, String refreshToken, Timestamp expiration) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiration = expiration;
    }

    public Timestamp getExpiration() {
        return expiration;
    }

    public void setExpiration(Timestamp expiration) {
        this.expiration = expiration;
    }

    
    
    

    public TokenResponse(String a) {
        a = a.replace('{', '\"');
        a = a.replace('}', '\"');
        a = a.replaceAll("\"", "");
        a = a.replace(',', ':');        
        List<String> c = new ArrayList<>(Arrays.asList(a.split(":")));
        c.removeAll(Arrays.asList("", null));
        
        for(int i = 0;i < c.size();i++){
            if(c.get(i).equals("access_token")){
                i++;
                if("null".equals(c.get(i))){
                    accessToken = null;
                }
                else{
                    accessToken = c.get(i);
                }
            }
            if(c.get(i).equals("refresh_token")){
                i++;
                if("null".equals(c.get(i))){
                    refreshToken = null;
                }
                else{
                    refreshToken = c.get(i);
                }
            }
        }
        
    }
    
    

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    
}