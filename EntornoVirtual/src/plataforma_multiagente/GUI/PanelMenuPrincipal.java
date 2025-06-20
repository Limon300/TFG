/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package plataforma_multiagente.GUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.SpringLayout;

/**
 *
 * @author usuario
 */
public class PanelMenuPrincipal extends javax.swing.JPanel {
    GUI padre;
    
    JButton nueva;
    JButton ver;
    JButton cambiar;
    JButton salir;
    SpringLayout layout;
    
    /**
     * Creates new form PanelMenuPrincipal
     */
    public PanelMenuPrincipal(GUI padre) {
        this.padre = padre;
        layout = new SpringLayout();
        this.setLayout(layout);
        //Botones
        nueva = new JButton("Nueva sesion");
        ver = new JButton("Ver sesion");
        cambiar = new JButton("Cambiar usuario");
        salir = new JButton("Salir");
        nueva.setPreferredSize(new Dimension(164,25));
        ver.setPreferredSize(new Dimension(164,25));
        cambiar.setPreferredSize(new Dimension(164,25));
        salir.setPreferredSize(new Dimension(164,25));
        this.add(nueva);
        this.add(ver);
        this.add(cambiar);
        this.add(salir);
        
        this.configureLayout();
        nueva.addActionListener(new UnirsePress());
        ver.addActionListener(new VerPress());
        cambiar.addActionListener(new CambiarPress());
        salir.addActionListener(new SalirPress());
    }
    
    private void configureLayout(){
        //Posicionar el boton nuevo
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.nueva,
                     0,
                     SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, this.nueva,
                     -45,
                     SpringLayout.VERTICAL_CENTER, this);
        //Posicionar el boton ver
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.ver,
                     0,
                     SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, this.ver,
                     -15,
                     SpringLayout.VERTICAL_CENTER, this);
        //Posicionar el boton salir
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.cambiar,
                     0,
                     SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, this.cambiar,
                     15,
                     SpringLayout.VERTICAL_CENTER, this);
        //Posicionar el boton cmbiar
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, this.salir,
                     0,
                     SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, this.salir,
                     45,
                     SpringLayout.VERTICAL_CENTER, this);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private class VerPress implements ActionListener {
        public VerPress() {
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            
            if(padre.VerSesion.actualizarLista()){
                padre.CambiarPanel("menu", "ver");
            }
            
        }
    }

    private class UnirsePress implements ActionListener {
        public UnirsePress() {
        }
        @Override
        public void actionPerformed(ActionEvent e){
            if(padre.CrearMapa.actualizarLista()){
                if(((PanelCrearSesionAgentes) padre.CrearAgentes).actualizarLista()){
                    ((PanelCrearSesionFin) padre.CrearFin).actualizarLista();
                    padre.CambiarPanel("menu", "crear_mapa");
                }
            }
            
            //Quizas convendria un mensaje de error
        }
    }
    
    private class CambiarPress implements ActionListener {
        public CambiarPress() {
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            padre.getPrograma().CambioSesion();
            padre.CambiarPanel("menu", "login");
        }
    }
    
    private class SalirPress implements ActionListener {
        public SalirPress() {
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            padre.isRunning = false;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
