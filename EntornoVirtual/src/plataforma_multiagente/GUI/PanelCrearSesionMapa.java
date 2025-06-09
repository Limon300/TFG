/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import plataforma_multiagente.datos.MapaResponse;

/**
 *
 * @author usuario
 */
public class PanelCrearSesionMapa extends javax.swing.JPanel {
    GUI padre;
    //Lista
    ScrollPane mapas;
    JList lista_mapas;
    //Texto
    JTextArea fmapa_detalles;
    //Botones
    JButton atras;
    JButton siguiente;

    SpringLayout layout;
    /**
     * Creates new form PanelCrearSesion
     * @param padre
     */
    public PanelCrearSesionMapa(GUI padre) {
        this.padre = padre;
        //initComponents();
        layout = new SpringLayout();
        this.setLayout(layout);
        
        //Lista mapas disponibles
        mapas = new ScrollPane();
        lista_mapas = new JList();
        mapas.add(lista_mapas);
        mapas.setPreferredSize(new Dimension(300,450));
        this.add(mapas);
        
        //Panel de informacion del mapa
        fmapa_detalles = new JTextArea();
        fmapa_detalles.setPreferredSize(new Dimension(300,450));
        fmapa_detalles.setEditable(false);
        fmapa_detalles.setLineWrap(true);
        fmapa_detalles.setWrapStyleWord(true);
        this.add(fmapa_detalles);
        
        //Boton siguiente
        siguiente = new JButton("siguiente");
        siguiente.setPreferredSize(new Dimension(120,25));
        siguiente.setEnabled(false);
        this.add(siguiente);
        
        //Boton atras
        atras = new JButton("atras");
        atras.setPreferredSize(new Dimension(120,25));
        this.add(atras);
        
        //Eventos
        lista_mapas.addListSelectionListener(new MapaPress());
        atras.addActionListener(new AtrasPress());
        siguiente.addActionListener(new SiguientePress());
        //Layout
        this.configureLayout();
        this.setVisible(true);
    }
    
    
    private void configureLayout(){
        //Posicionar lista
        layout.putConstraint(SpringLayout.WEST, this.mapas,
                     50,
                     SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, this.mapas,
                     -25,
                     SpringLayout.VERTICAL_CENTER, this);
        //Posicionar detalles
        layout.putConstraint(SpringLayout.EAST, this.fmapa_detalles,
                     -50,
                     SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, this.fmapa_detalles,
                     -25,
                     SpringLayout.VERTICAL_CENTER, this);
        //Boton atras
        layout.putConstraint(SpringLayout.WEST, this.atras,
                     0,
                     SpringLayout.WEST, this.fmapa_detalles);
        layout.putConstraint(SpringLayout.NORTH, this.atras,
                     20,
                     SpringLayout.SOUTH, this.fmapa_detalles);
        //Boton siguiente
        layout.putConstraint(SpringLayout.EAST, this.siguiente,
                     0,
                     SpringLayout.EAST, this.mapas);
        layout.putConstraint(SpringLayout.NORTH, this.siguiente,
                     20,
                     SpringLayout.SOUTH, this.mapas);
    }

    protected boolean actualizarLista(){
        List<String> a = new ArrayList();
        boolean com_status = this.padre.getPrograma().PedirMapas();
        siguiente.setEnabled(false);
        lista_mapas.clearSelection();
        fmapa_detalles.setText(null);
        if(com_status){
            this.padre.getPrograma().getMapas().forEach(n -> a.add(n.getNombre()));
            lista_mapas.setListData(a.toArray());
        }
        return com_status;
    }

    private class SiguientePress implements ActionListener {

        public SiguientePress() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(padre.getPrograma().setMapa_actual(lista_mapas.getSelectedIndex())){

                    padre.CambiarPanel("crear_mapa", "crear_agentes");

            }
        }
    }

    private class AtrasPress implements ActionListener {

        public AtrasPress() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            padre.CambiarPanel("crear_mapa", "menu");
            padre.getPrograma().Reset();
            siguiente.setEnabled(false);
            lista_mapas.clearSelection();
            fmapa_detalles.setText(null);
        }
    }
    
    
    private class MapaPress implements ListSelectionListener {
        public MapaPress() {
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if(!e.getValueIsAdjusting()){
                int i = lista_mapas.getSelectedIndex();
                if(i >= 0){
                    MapaResponse select = padre.getPrograma().getMapa(i);
                    siguiente.setEnabled(true);
                    fmapa_detalles.setText(select.toString());
                }
            }
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenuBar3 = new javax.swing.JMenuBar();
        jMenu5 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        jMenuBar4 = new javax.swing.JMenuBar();
        jMenu7 = new javax.swing.JMenu();
        jMenu8 = new javax.swing.JMenu();
        jMenuBar5 = new javax.swing.JMenuBar();
        jMenu9 = new javax.swing.JMenu();
        jMenu10 = new javax.swing.JMenu();
        jMenu11 = new javax.swing.JMenu();
        jDialog1 = new javax.swing.JDialog();
        jMenuItem1 = new javax.swing.JMenuItem();
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jTextField2 = new javax.swing.JTextField();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("File");
        jMenuBar2.add(jMenu3);

        jMenu4.setText("Edit");
        jMenuBar2.add(jMenu4);

        jMenu5.setText("File");
        jMenuBar3.add(jMenu5);

        jMenu6.setText("Edit");
        jMenuBar3.add(jMenu6);

        jMenu7.setText("File");
        jMenuBar4.add(jMenu7);

        jMenu8.setText("Edit");
        jMenuBar4.add(jMenu8);

        jMenu9.setText("File");
        jMenuBar5.add(jMenu9);

        jMenu10.setText("Edit");
        jMenuBar5.add(jMenu10);

        jMenu11.setText("jMenu11");

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jMenuItem1.setText("jMenuItem1");

        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("jRadioButtonMenuItem1");

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        jTextField2.setText("jTextField2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 483, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 375, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog jDialog1;
    private javax.swing.JList<String> jList1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu10;
    private javax.swing.JMenu jMenu11;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JMenuBar jMenuBar4;
    private javax.swing.JMenuBar jMenuBar5;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
