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
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import plataforma_multiagente.datos.Agente;
import plataforma_multiagente.datos.AgenteStoreResponse;

/**
 *
 * @author usuario
 */
public class PanelCrearSesionFin extends JPanel {

    GUI padre;
    JButton atras;
    JButton terminar;
    JLabel ciclo;
    JLabel espera;
    JTextField fciclo;
    JTextField fespera;
    
    SpringLayout layout;
    
    public PanelCrearSesionFin(GUI aThis) {
        this.padre = aThis;
        layout = new SpringLayout();
        this.setLayout(layout);
        
        //Etiquetas
        ciclo = new JLabel("ciclo");
        espera = new JLabel("espera");
        this.add(ciclo);
        this.add(espera);
        //Campos de texto
        this.fciclo = new JTextField();
        fciclo.setPreferredSize(new Dimension(120,20));
        this.fespera = new JTextField();
        fespera.setPreferredSize(new Dimension(120,20));
        this.add(fciclo);
        this.add(fespera);
        //Boton
        atras = new JButton("atras");
        atras.setPreferredSize(new Dimension(80,25));
        this.add(atras);
        terminar = new JButton("fin");
        terminar.setPreferredSize(new Dimension(80,25));
        this.add(terminar);
        
        //Eventos
        atras.addActionListener(new AtrasPress());
        terminar.addActionListener(new TerminarPress());
        
        //Layout
        this.configureLayout();
        this.setVisible(true);
    
    }
    
    private void configureLayout(){
//Posicionar etiqueta ciclo
        layout.putConstraint(SpringLayout.WEST, ciclo,
                     -130,
                     SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, ciclo,
                     -30,
                     SpringLayout.VERTICAL_CENTER, this);
        //Posicionar etiqueta espera
        layout.putConstraint(SpringLayout.WEST, espera,
                     -130,
                     SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, espera,
                     0,
                     SpringLayout.VERTICAL_CENTER, this);
        //Posicionar campo ciclo
        layout.putConstraint(SpringLayout.WEST, fciclo,
                     10,
                     SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, fciclo,
                     -30,
                     SpringLayout.VERTICAL_CENTER, this);
        
        //Posicionar campo espera
        layout.putConstraint(SpringLayout.WEST, fespera,
                     10,
                     SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, fespera,
                     0,
                     SpringLayout.VERTICAL_CENTER, this);

        //Posicionar el boton terminar
        layout.putConstraint(SpringLayout.EAST, terminar,
                     130,
                     SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, terminar,
                     30,
                     SpringLayout.VERTICAL_CENTER, this);
        //Posicionar el boton terminar
        layout.putConstraint(SpringLayout.WEST, atras,
                     -130,
                     SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, atras,
                     30,
                     SpringLayout.VERTICAL_CENTER, this);
        
    }

    
    protected boolean actualizarLista(){
        fciclo.setText("");
        fespera.setText("");
        return true;
    }

    private class TerminarPress implements ActionListener {

        public TerminarPress() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int i;
            int j;
            try {
                i = Integer.valueOf(fciclo.getText());
                j = Integer.valueOf(fespera.getText());
                if(i < 0 || j < 0){
                    this.error();
                }
                else{
                    padre.getPrograma().setTiempos(i, j);
                    if(padre.getPrograma().PedirCrearSesion()){
                        padre.CambiarPanel("crear_fin", "sesion");
                    }
                }
            }
            catch(NumberFormatException err){
                    this.error();
                }
        }
        
        private void error(){
            System.out.println("Error");
            JOptionPane.showMessageDialog(padre.CrearFin,"Los valores que ha introducido son incorrectos, "
                    + "use numeros enteros positivos","Error",ERROR_MESSAGE); 
        }
    }

    private class AtrasPress implements ActionListener {

        public AtrasPress() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            padre.CambiarPanel("crear_fin", "crear_agentes");
        }
    }

    
}
