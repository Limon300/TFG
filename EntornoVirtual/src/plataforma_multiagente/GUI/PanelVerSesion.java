/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plataforma_multiagente.GUI;

import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import plataforma_multiagente.datos.SesionData;

/**
 *
 * @author usuario
 */
public class PanelVerSesion extends JPanel {
    GUI padre;
    //Lista
    ScrollPane sesiones;
    JList lista_sesiones;
    //Texto
    ScrollPane detalles;
    JTextArea fsesiones_detalles;
    //Botones
    JButton atras;
    JButton cargar;
    JButton abandonar;

    SpringLayout layout;
    
    public PanelVerSesion(GUI padre) {
        this.padre = padre;
        //initComponents();
        layout = new SpringLayout();
        this.setLayout(layout);
        
        //Lista mapas disponibles
        sesiones = new ScrollPane();
        lista_sesiones = new JList();
        sesiones.add(lista_sesiones);
        sesiones.setPreferredSize(new Dimension(300,450));
        this.add(sesiones);
        
        //Panel de informacion del mapa
        detalles = new ScrollPane();
        fsesiones_detalles = new JTextArea();
        fsesiones_detalles.setEditable(false);
        fsesiones_detalles.setLineWrap(true);
        fsesiones_detalles.setWrapStyleWord(true);
        detalles.add(fsesiones_detalles);
        detalles.setPreferredSize(new Dimension(300,450));
        this.add(detalles);
        
        //Boton siguiente
        cargar = new JButton("cargar");
        cargar.setPreferredSize(new Dimension(120,25));
        cargar.setEnabled(false);
        this.add(cargar);
        
        //Boton abandonar
        abandonar = new JButton("abandonar");
        abandonar.setPreferredSize(new Dimension(120,25));
        abandonar.setEnabled(false);
        this.add(abandonar);
        
        //Boton atras
        atras = new JButton("atras");
        atras.setPreferredSize(new Dimension(120,25));
        this.add(atras);
        
        //Eventos
        lista_sesiones.addListSelectionListener(new SesionPress());
        atras.addActionListener(new AtrasPress());
        abandonar.addActionListener(new AbandonarPress());
        cargar.addActionListener(new CargarPress());
        //Layout
        this.configureLayout();
        this.setVisible(true);
    }
    
    
    private void configureLayout(){
        //Posicionar lista
        layout.putConstraint(SpringLayout.WEST, this.sesiones,
                     50,
                     SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, this.sesiones,
                     -25,
                     SpringLayout.VERTICAL_CENTER, this);
        //Posicionar detalles
        layout.putConstraint(SpringLayout.EAST, this.detalles,
                     -50,
                     SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, this.detalles,
                     -25,
                     SpringLayout.VERTICAL_CENTER, this);
        //Boton atras
        layout.putConstraint(SpringLayout.WEST, this.atras,
                     0,
                     SpringLayout.WEST, this.detalles);
        layout.putConstraint(SpringLayout.NORTH, this.atras,
                     20,
                     SpringLayout.SOUTH, this.detalles);
        //Boton cargar
        layout.putConstraint(SpringLayout.EAST, this.cargar,
                     0,
                     SpringLayout.EAST, this.sesiones);
        layout.putConstraint(SpringLayout.NORTH, this.cargar,
                     20,
                     SpringLayout.SOUTH, this.sesiones);
        //Boton cargar
        layout.putConstraint(SpringLayout.EAST, this.cargar,
                     0,
                     SpringLayout.EAST, this.sesiones);
        layout.putConstraint(SpringLayout.NORTH, this.cargar,
                     20,
                     SpringLayout.SOUTH, this.sesiones);
        //Boton abandonar
        layout.putConstraint(SpringLayout.WEST, this.abandonar,
                     0,
                     SpringLayout.WEST, this.sesiones);
        layout.putConstraint(SpringLayout.NORTH, this.abandonar,
                     20,
                     SpringLayout.SOUTH, this.sesiones);
    }
    
    protected boolean actualizarLista(){
        List<String> a = new ArrayList();
        //System.out.println("Actualizar lista 139");
        //System.out.println(this.padre);
        boolean com_status = this.padre.getPrograma().PedirVerSesion();
        cargar.setEnabled(false);
        abandonar.setEnabled(false);
        this.lista_sesiones.clearSelection();
        this.fsesiones_detalles.setText(null);
        if(com_status){
            this.padre.getPrograma().getSesiones_actuales().forEach(n -> a.add(n.getId_sesion().toString()));
            lista_sesiones.setListData(a.toArray());
        }
        return com_status;
    }

    private class CargarPress implements ActionListener {

        public CargarPress() {
        
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(padre.getPrograma().loadSesion_actual(lista_sesiones.getSelectedIndex())){
                padre.CambiarPanel("ver", "sesion");
                padre.getPrograma().setSesiones_actuales(null);
                cargar.setEnabled(false);
                abandonar.setEnabled(false);
                lista_sesiones.clearSelection();
                fsesiones_detalles.setText(null);
            }
        }
    }

    private class AbandonarPress implements ActionListener {

        public AbandonarPress() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            padre.getPrograma().setSesion_actual(padre.getPrograma().getSesiones_actuales().get(lista_sesiones.getSelectedIndex()).getId_sesion());
            padre.getPrograma().PedirEliminarSesion();
            actualizarLista();
        }
    }
    
    private class AtrasPress implements ActionListener {

        public AtrasPress() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            padre.CambiarPanel("ver", "menu");
            padre.getPrograma().setSesiones_actuales(null);
            cargar.setEnabled(false);
            abandonar.setEnabled(false);
            lista_sesiones.clearSelection();
            fsesiones_detalles.setText(null);
        }
    }
    
    private class SesionPress implements ListSelectionListener {
        public SesionPress() {
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if(!e.getValueIsAdjusting()){
                int i = lista_sesiones.getSelectedIndex();
                if(i >= 0){
                    SesionData select = padre.getPrograma().getSesiones_actuales().get(i);
                    cargar.setEnabled(true);
                    abandonar.setEnabled(true);
                    fsesiones_detalles.setText(select.toString());
                }
            }
        }
    }
    
}
