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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import plataforma_multiagente.datos.Agente;
import plataforma_multiagente.datos.AgenteStoreResponse;
import plataforma_multiagente.datos.SesionUserRequest;

/**
 *
 * @author usuario
 */
public class PanelInvitar extends JPanel {

    GUI padre;
    ScrollPane agentes_disponibles;
    ScrollPane agentes_escogidos;
    ScrollPane agentes_disponibles_d;
    ScrollPane agentes_escogidos_d;
    //JList ad;
    //JList ae;
    JList lista_ad;
    JList lista_ae;
    
    //Agentes invitados
    private SesionUserRequest request;
    private List<Agente> agentes;
    private int cont_agentes;
    //JTextField fciclo;
    //JTextField fespera;
    JTextArea ad_detalles;
    JTextArea ae_detalles;
    
    JButton cancelar;
    JButton invitar;
    
    JButton anadir;
    JButton eliminar;
    
    
    private JLabel id;
    private JTextField fid;
    
    JFrame frame;

    SpringLayout layout;
    
    public PanelInvitar(GUI aThis, JFrame frame) {
        this.padre = aThis;
        layout = new SpringLayout();
        this.setLayout(layout);
        
        this.frame = frame;
        
        //Etiquetas
        id = new JLabel("ID usuario");
        this.add(id);
        //Campos de texto
        this.fid = new JTextField();
        fid.setPreferredSize(new Dimension(200,20));
        this.add(fid);
        
        //Agentes escogidos
        this.agentes = new ArrayList();
        cont_agentes = 0;
        //Lista agentes disponibles
        agentes_disponibles = new ScrollPane();
        lista_ad = new JList();
        agentes_disponibles.add(lista_ad);
        agentes_disponibles.setPreferredSize(new Dimension(300,215));
        this.add(agentes_disponibles);
        
        //Detalles agentes disponibles
        agentes_disponibles_d = new ScrollPane();
        ad_detalles = new JTextArea();
        ad_detalles.setEditable(false);
        ad_detalles.setLineWrap(true);
        ad_detalles.setWrapStyleWord(true);
        agentes_disponibles_d.add(ad_detalles);
        agentes_disponibles_d.setPreferredSize(new Dimension(300,215));
        this.add(agentes_disponibles_d);
        
        //Lista agentes escogidos
        agentes_escogidos = new ScrollPane();
        lista_ae = new JList();
        agentes_escogidos.add(lista_ae);
        agentes_escogidos.setPreferredSize(new Dimension(300,175));
        this.add(agentes_escogidos);
        
        //Detalles agentes escogidos
        agentes_escogidos_d = new ScrollPane();
        ae_detalles = new JTextArea();
        ae_detalles.setEditable(false);
        ae_detalles.setLineWrap(true);
        ae_detalles.setWrapStyleWord(true);
        agentes_escogidos_d.add(ae_detalles);
        agentes_escogidos_d.setPreferredSize(new Dimension(300,215));
        this.add(agentes_escogidos_d);
        
        //Boton siguiente
        invitar = new JButton("invitar");
        invitar.setPreferredSize(new Dimension(120,25));
        invitar.setEnabled(false);
        this.add(invitar);
        
        //Boton atras
        cancelar = new JButton("cancelar");
        cancelar.setPreferredSize(new Dimension(120,25));
        this.add(cancelar);
        
        //Boton anadir
        anadir = new JButton("añadir");
        anadir.setPreferredSize(new Dimension(120,25));
        anadir.setEnabled(false);
        this.add(anadir);
        
        //Boton eliminar
        eliminar = new JButton("eliminar");
        eliminar.setPreferredSize(new Dimension(120,25));
        eliminar.setEnabled(false);
        this.add(eliminar);
        
        //Eventos
        cancelar.addActionListener(new CancelarPress());
        anadir.addActionListener(new AnadirPress());
        eliminar.addActionListener(new EliminarPress());
        invitar.addActionListener(new InvitarPress());
        lista_ad.addListSelectionListener(new DisponiblesPress());
        lista_ae.addListSelectionListener(new EscogidosPress());
        
        //Layout
        this.configureLayout();
        
        //Actualizar
        this.actualizarLista();
        
        //Visible
        this.setVisible(true);
    
    }
    
    private void configureLayout(){
        //Posicionar agentes disponibles
        layout.putConstraint(SpringLayout.WEST, this.agentes_disponibles,
                     50,
                     SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, this.agentes_disponibles,
                     -250,
                     SpringLayout.VERTICAL_CENTER, this);
        //Posicionar agentes disponibles detalles
        layout.putConstraint(SpringLayout.WEST, this.agentes_disponibles_d,
                     50,
                     SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, this.agentes_disponibles_d,
                     20,
                     SpringLayout.SOUTH, this.agentes_disponibles);
        //Posicionar agentes escogidos
        layout.putConstraint(SpringLayout.EAST, this.agentes_escogidos,
                     -50,
                     SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, this.agentes_escogidos,
                     -210,
                     SpringLayout.VERTICAL_CENTER, this);
        //Posicionar agentes escogidos detalles
        layout.putConstraint(SpringLayout.EAST, this.agentes_escogidos_d,
                     -50,
                     SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, this.agentes_escogidos_d,
                     20,
                     SpringLayout.SOUTH, this.agentes_escogidos);
        
        //Posicionar label id
        layout.putConstraint(SpringLayout.WEST, this.id,
                     0,
                     SpringLayout.WEST, this.agentes_escogidos_d);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, this.id,
                     0,
                     SpringLayout.VERTICAL_CENTER, this.fid);
        //Posicionar campo id
        layout.putConstraint(SpringLayout.EAST, this.fid,
                     -50,
                     SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.SOUTH, this.fid,
                     -20,
                     SpringLayout.NORTH, this.agentes_escogidos);
        //Boton atras
        layout.putConstraint(SpringLayout.WEST, this.cancelar,
                     0,
                     SpringLayout.WEST, this.agentes_escogidos_d);
        layout.putConstraint(SpringLayout.NORTH, this.cancelar,
                     20,
                     SpringLayout.SOUTH, this.agentes_escogidos_d);
        //Boton siguiente
        layout.putConstraint(SpringLayout.EAST, this.invitar,
                     0,
                     SpringLayout.EAST, this.agentes_disponibles_d);
        layout.putConstraint(SpringLayout.NORTH, this.invitar,
                     20,
                     SpringLayout.SOUTH, this.agentes_disponibles_d);
        //Boton eliminar
        layout.putConstraint(SpringLayout.EAST, this.eliminar,
                     0,
                     SpringLayout.EAST, this.agentes_escogidos_d);
        layout.putConstraint(SpringLayout.NORTH, this.eliminar,
                     20,
                     SpringLayout.SOUTH, this.agentes_escogidos_d);
        //Boton anadir
        layout.putConstraint(SpringLayout.WEST, this.anadir,
                     0,
                     SpringLayout.WEST, this.agentes_disponibles_d);
        layout.putConstraint(SpringLayout.NORTH, this.anadir,
                     20,
                     SpringLayout.SOUTH, this.agentes_disponibles_d);
        
    }
    
    public boolean addAgentesActuales(int agente_actual) {
        
        if(agente_actual >= 0 && agente_actual < padre.getPrograma().getAgentes().size()){
            String nuevo = "Agente_" + cont_agentes;
            cont_agentes ++;
            this.agentes.add(new Agente(nuevo,padre.getPrograma().getAgente(agente_actual)));
            return true;
        }
        else{
            return false;
        }
    }

    private class InvitarPress implements ActionListener {

        public InvitarPress() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            request = new SesionUserRequest();
            request.setId_usuario(Long.valueOf(fid.getText()));
            List<Long> a = new ArrayList();
            agentes.forEach(n -> a.add(Long.valueOf(n.getId_store())));
            request.setAgentes(a);
            //padre.getPrograma().PedirInvitar(request);
            boolean respuesta;
            try {
                respuesta = padre.getPrograma().PedirInvitar(request);
            }
            catch (Exception ex){
                respuesta = false;
            }
            
            if(respuesta){
                frame.dispose();
            }
        }
    }

    private class EliminarPress implements ActionListener {

        public EliminarPress() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            List<String> a = new ArrayList();
            agentes.remove(lista_ae.getSelectedIndex());
            agentes.forEach(n -> a.add(n.getNombre_juego()));
            eliminar.setEnabled(false);
            lista_ae.setListData(a.toArray());
            ae_detalles.setText("");
            if(a.size() <= 0){
                invitar.setEnabled(false);
            }
            
        }
    }

    private class EscogidosPress implements ListSelectionListener {

        public EscogidosPress() {
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if(!e.getValueIsAdjusting()){
                int i = lista_ae.getSelectedIndex();
                if(i >= 0){
                    Agente select = agentes.get(i);
                    eliminar.setEnabled(true);
                    ae_detalles.setText(select.toString());
                }
            }
        }
    }

    private class AnadirPress implements ActionListener {
        public AnadirPress() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            List<String> a = new ArrayList();
            
            addAgentesActuales(lista_ad.getSelectedIndex());
            agentes.forEach(n -> a.add(n.getNombre_juego()));
            invitar.setEnabled(true);
            lista_ae.setListData(a.toArray());
        }
    }

    private class DisponiblesPress implements ListSelectionListener {
        public DisponiblesPress() {
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if(!e.getValueIsAdjusting()){
                int i = lista_ad.getSelectedIndex();
                if(i >= 0){
                    AgenteStoreResponse select = padre.getPrograma().getAgente(i);
                    anadir.setEnabled(true);
                    ad_detalles.setText(select.toString());
                }
            }
        }
    }

    private class CancelarPress implements ActionListener {
        public CancelarPress() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
        }
    }
    
    protected boolean actualizarLista(){
        //Reset
        this.cont_agentes = 0;
        
        //Agentes
        List<String> a = new ArrayList();
        boolean com_status = this.padre.getPrograma().PedirAgentes();
        if(com_status){
            this.padre.getPrograma().getAgentes().forEach(n -> a.add(n.getNombre()));
            lista_ad.setListData(a.toArray());
        }
        //Actuales
        
        List<String> b = new ArrayList();
        //padre.getPrograma().getAgentesActuales().forEach(n -> b.add(n.getNombre_juego()));
        lista_ae.setListData(b.toArray());
        if(b.size() <= 0){
            invitar.setEnabled(false);
        }
        
        int i = lista_ad.getSelectedIndex();
        if(i >= 0){
            AgenteStoreResponse select = padre.getPrograma().getAgente(i);
            anadir.setEnabled(true);
            ad_detalles.setText(select.toString());
        }
        else{
            ad_detalles.setText("");
            anadir.setEnabled(false);
        }
        /*
        i = lista_ae.getSelectedIndex();
        if(i >= 0){
            Agente select = padre.getPrograma().getAgenteActual(i);
            eliminar.setEnabled(true);
            ae_detalles.setText(select.toString());
        }
        else{
            ae_detalles.setText("");
            eliminar.setEnabled(false);
        }
        */
        return com_status;
    }

    
}
