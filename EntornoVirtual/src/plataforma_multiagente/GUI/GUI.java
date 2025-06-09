/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plataforma_multiagente.GUI;

import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import plataforma_multiagente.EntornoLocal;

/**
 *
 * @author usuario
 */
public class GUI extends Thread {
    JFrame frame;
    PanelLogin Login;
    PanelMenuPrincipal Menu;
    PanelCrearSesionMapa CrearMapa;
    JPanel CrearAgentes;
    JPanel CrearFin;
    PanelSesion Sesion;
    PanelVerSesion VerSesion;
    EntornoLocal programa;
    boolean isRunning = true;
    private final int width = 800;
    private final int height = 600;
    private final int scale = 1;
    private final Map<String,JPanel> paneles;
    
    // Setup
    public GUI(EntornoLocal prin) {
        //Programa
        programa = prin;
        // JFrame
        frame = new JFrame();
        frame.addWindowListener(new FrameClose());
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setSize(width *  scale, height * scale);

        // Login
        Login = new PanelLogin(this);
        Login.setSize(width *  scale, height * scale);
        //Login.setVisible(false);
        Login.setVisible(true);
        frame.add(Login);

        // Menu
        Menu = new PanelMenuPrincipal(this);
        Menu.setSize(width *  scale, height * scale);
        //Menu.setVisible(true);
        Menu.setVisible(false);
        
        //frame.add(Menu);
        
        //Crear/Mapa
        CrearMapa = new PanelCrearSesionMapa(this);
        CrearMapa.setSize(width *  scale, height * scale);
        CrearMapa.setVisible(false);
        
        //Crear/Agentes
        CrearAgentes = new PanelCrearSesionAgentes(this);
        CrearAgentes.setSize(width *  scale, height * scale);
        CrearAgentes.setVisible(false);
        
        //Crear/Fin
        CrearFin = new PanelCrearSesionFin(this);
        CrearFin.setSize(width *  scale, height * scale);
        CrearFin.setVisible(false);
        
        //Sesion
        Sesion = new PanelSesion(this);
        Sesion.setSize(width *  scale, height * scale);
        Sesion.setVisible(false);
        
        //Ver
        VerSesion = new PanelVerSesion(this);
        VerSesion.setSize(width *  scale, height * scale);
        VerSesion.setVisible(false);
        
        //frame.add(Sesion);
        
        paneles = new HashMap();
        paneles.put("login", Login);
        paneles.put("menu", Menu);
        paneles.put("crear_mapa",CrearMapa);
        paneles.put("crear_agentes",CrearAgentes);
        paneles.put("crear_fin",CrearFin);
        paneles.put("sesion",Sesion);
        paneles.put("ver",VerSesion);
        
        frame.setVisible(true);
        start();
        
    }
    
    public GUI(EntornoLocal prin,Long id,String password) {
        //Programa
        programa = prin;
        // JFrame
        frame = new JFrame();
        frame.addWindowListener(new FrameClose());
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setSize(width *  scale, height * scale);

        boolean login_attemp = prin.LoginConection(id, password);
        
        
        // Login
        Login = new PanelLogin(this);
        Login.setSize(width *  scale, height * scale);
        if(login_attemp){
            Login.setVisible(false);
        }
        else{
            Login.setVisible(true);
            frame.add(Login);
        }
        
        // Menu
        Menu = new PanelMenuPrincipal(this);
        Menu.setSize(width *  scale, height * scale);
        if(login_attemp){
            Menu.setVisible(true);
            frame.add(Menu);
        }
        else{
            Menu.setVisible(false);
        }
        
        //Crear/Mapa
        CrearMapa = new PanelCrearSesionMapa(this);
        CrearMapa.setSize(width *  scale, height * scale);
        CrearMapa.setVisible(false);
        
        //Crear/Agentes
        CrearAgentes = new PanelCrearSesionAgentes(this);
        CrearAgentes.setSize(width *  scale, height * scale);
        CrearAgentes.setVisible(false);
        
        //Crear/Fin
        CrearFin = new PanelCrearSesionFin(this);
        CrearFin.setSize(width *  scale, height * scale);
        CrearFin.setVisible(false);
        
        //Sesion
        Sesion = new PanelSesion(this);
        Sesion.setSize(width *  scale, height * scale);
        Sesion.setVisible(false);
        
        //Ver
        VerSesion = new PanelVerSesion(this);
        VerSesion.setSize(width *  scale, height * scale);
        VerSesion.setVisible(false);
        
        //frame.add(Sesion);
        
        paneles = new HashMap();
        paneles.put("login", Login);
        paneles.put("menu", Menu);
        paneles.put("crear_mapa",CrearMapa);
        paneles.put("crear_agentes",CrearAgentes);
        paneles.put("crear_fin",CrearFin);
        paneles.put("sesion",Sesion);
        paneles.put("ver",VerSesion);
        
        frame.setVisible(true);
        start();
        
    }

    public EntornoLocal getPrograma() {
        return programa;
    }
    
    public void CambiarPanel(String actual,String nuevo){
        this.frame.remove(this.paneles.get(actual));
        this.paneles.get(actual).setVisible(false);
        this.frame.add(this.paneles.get(nuevo));
        this.paneles.get(nuevo).setVisible(true);
    }
    
    private class FrameClose extends WindowAdapter {
        @Override
        public void windowClosing(final WindowEvent e) {
            isRunning = false;
        }
    }
    
    public void run() {
        long fpsWait = (long) (1.0 / 30 * 1000);
        main: while (isRunning) {
            long renderStart = System.nanoTime();
            if(Sesion.isVisible()){
                try {
                    Sesion.start_this();
                } catch (IOException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                Sesion.actualizarSesion();
            }
            // Better do some FPS limiting here
            long renderTime = (System.nanoTime() - renderStart) / 1000000;
            try {
                Thread.sleep(Math.max(0, fpsWait - renderTime));
            } catch (InterruptedException e) {
                Thread.interrupted();
                break;
            }
            renderTime = (System.nanoTime() - renderStart) / 1000000;

        }
        this.Sesion.disposeFrame_invitar();
        frame.dispose();
    }

    protected int getWidth() {
        return width;
    }

    protected int getHeight() {
        return height;
    }

    protected int getScale() {
        return scale;
    }
    
    
}
