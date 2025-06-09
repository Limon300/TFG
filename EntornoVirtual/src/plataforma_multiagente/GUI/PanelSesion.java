/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plataforma_multiagente.GUI;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.sql.Timestamp;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.WindowConstants;
import plataforma_multiagente.datos.AgentePanel;
import plataforma_multiagente.datos.Casilla;
import plataforma_multiagente.datos.CasillaPanel;
import plataforma_multiagente.datos.LogResponse;

/**
 *
 * @author usuario
 */
public class PanelSesion extends JPanel {
    GUI padre;
    SpringLayout layout;
    //Canvas
    private boolean isRunning = false;
    private boolean drawing = false;
    private int scale = 1;
    private Canvas canvas;
    private BufferStrategy strategy;
    private BufferedImage background;
    private Graphics2D backgroundGraphics;
    private Graphics2D graphics;
    private int width = 500;
    private int height = 500;
    private GraphicsConfiguration config =
            GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration();
    //Botones
    private JButton salir;
    private JButton abandonar;
    //Invitar
    private JButton invitar;
    private JFrame frame_invitar;
    //Etiquetas
    private JLabel sesionId;
    
    //Cuantas casillas vamos a pintar por fila y columna
    private int zoom = 25;
    
    //Informacion del entorno
    private Map<Long,Map<Long,CasillaPanel>> mapa;
    private Map<Long,AgentePanel> agentes;
    
    //Posicion del lienzo en la que se empieza a pintar
    private Long x_0;
    private Long y_0;
    
    //Ultimo log recibido
    private LogResponse ultima_peticion;
    
    //Posiciones que delimitan el segmento del mapa que pintamos
    private long x_min = 0;
    private long x_max = 0;
    private long y_min = 0;
    private long y_max = 0;
    private boolean mapa_grande = false;
    
    private AgentePanel foco;
    
    public final BufferedImage create(final int width, final int height,
            final boolean alpha) {
        return config.createCompatibleImage(width, height, alpha
                ? Transparency.TRANSLUCENT : Transparency.OPAQUE);
    }
    
    public PanelSesion(GUI aThis) {
        this.padre = aThis;
        layout = new SpringLayout();
        this.setLayout(layout);
        //Canvas
        // Canvas
        canvas = new Canvas(config);
        canvas.setSize(width * scale, height * scale);
        this.add(canvas);
        //Buffer
        background = create(width, height, false);

        //Etiquetas
        sesionId = new JLabel();
        this.add(sesionId);
        //Campos de texto
        
        //Boton salir
        salir = new JButton("salir a menu");
        salir.setPreferredSize(new Dimension(164,25));
        this.add(salir);
        //Boton abandonar
        abandonar = new JButton("abandonar sesion");
        abandonar.setPreferredSize(new Dimension(164,25));
        this.add(abandonar);
        
        //Boton Invitar usuario
        invitar = new JButton("invitar usuario");
        invitar.setPreferredSize(new Dimension(164,25));
        this.add(invitar);
        
        //Eventos
        salir.addActionListener(new SalirPress());
        abandonar.addActionListener(new AbandonarPress());
        invitar.addActionListener(new InvitarPress());
        
        //Layout
        this.configureLayout();
        this.setVisible(true);
    
    }
    
    public void start_this() throws IOException{
        if(!this.isRunning){
            sesionId.setText("Id Sesion: "+padre.getPrograma().getSesion_actual());
            canvas.createBufferStrategy(2);
            do {
                strategy = canvas.getBufferStrategy();
            } while (strategy == null);
            backgroundGraphics = (Graphics2D) background.getGraphics();
            Optional<List<Casilla>> casillas=padre.getPrograma().PedirCasillas();
            if(casillas.isPresent()){
                //Esto necesita bastante mas trabajo
                //Donde se situa el x=0 y y=0
                float x_in = padre.getPrograma().getMapa_actual().getX_tam();
                if(x_in > zoom){
                    x_0 = Long.valueOf(0);
                }
                else{
                    x_0 = width/2 - (long) (x_in * (float) width / (float) zoom)/2;
                }
                float y_in = padre.getPrograma().getMapa_actual().getY_tam();
                if(y_in > zoom){
                    y_0 = Long.valueOf(height - height/zoom);
                }
                else{
                    y_0 = height/2 - height/zoom + (long) (y_in * (float) height / (float) zoom)/2;
                }
                //Iniciar el mapa
                mapa = new HashMap();
                for(long i = 0;i<padre.getPrograma().getMapa_actual().getX_tam();i++){
                    mapa.put(i, new HashMap());
                }
                for(int i=0;i<casillas.get().size();i++){
                    //Añadir casilla
                    int n_obj = casillas.get().get(i).getObjetivos().size();
                    int n_poi = casillas.get().get(i).getPuntos().size();
                    float j = casillas.get().get(i).getZ().floatValue() / 255;
                    Color nuevo = new Color((float) j,j,j);
                    mapa.get(casillas.get().get(i).getX()).put(casillas.get().get(i).getY(), new CasillaPanel(nuevo,n_obj,n_poi)); 
                    
                }
                
                
                //Obtener los agentes, aunque quizas el bucle es redundante
                this.agentes = new HashMap();
                //Mis agentes
                for(int i=0;i<padre.getPrograma().getAgentesActuales().size();i++){
                    agentes.put(padre.getPrograma().getAgentesActuales().get(i).getId_juego(), 
                            new AgentePanel(
                                    true,
                                    padre.getPrograma().getAgentesActuales().get(i).getId_juego(),
                                    padre.getPrograma().getMapa_actual().getX_inicio(),
                                    padre.getPrograma().getMapa_actual().getY_inicio()
                            ));
                    this.mapa
                        .get(padre.getPrograma().getMapa_actual().getX_inicio())
                        .get(padre.getPrograma().getMapa_actual().getY_inicio())
                        .incAgentes();
                    
                }
                //Otros agentes
                for(int i=0;i<padre.getPrograma().getOtrosAgentes().size();i++){
                    agentes.put(padre.getPrograma().getOtroAgente(i).getId_juego(), 
                            new AgentePanel(
                                    false,
                                    padre.getPrograma().getOtroAgente(i).getId_juego(),
                                    padre.getPrograma().getMapa_actual().getX_inicio(),
                                    padre.getPrograma().getMapa_actual().getY_inicio()
                            ));
                    this.mapa
                        .get(padre.getPrograma().getMapa_actual().getX_inicio())
                        .get(padre.getPrograma().getMapa_actual().getY_inicio())
                        .incAgentes();
                }
                
                
                //Pintar el mapa
                this.foco = this.agentes.get(padre.getPrograma().getAgentesActuales().get(0).getId_juego());
                x_min = 0;
                x_max = this.padre.getPrograma().getMapa_actual().getX_tam()-1;
                y_min = 0;
                y_max = this.padre.getPrograma().getMapa_actual().getY_tam()-1;
                if(zoom < padre.getPrograma().getMapa_actual().getX_tam()){
                    this.mapa_grande = true;
                    x_minmax();
                }
                if(zoom < padre.getPrograma().getMapa_actual().getY_tam()){
                    this.mapa_grande = true;
                    y_minmax();
                }
                
               
                //Pintar mapa
                pintar_mapa();
                
                //Pintar los agentes
                for(int i=0;i<padre.getPrograma().getAgentesActuales().size();i++){
                    this.pintar_agente(true, padre.getPrograma().getMapa_actual().getX_inicio(), padre.getPrograma().getMapa_actual().getY_inicio());
                }
                for(int i=0;i<padre.getPrograma().getOtrosAgentes().size();i++){
                    this.pintar_agente(false, padre.getPrograma().getMapa_actual().getX_inicio(), padre.getPrograma().getMapa_actual().getY_inicio());
                }
                
                
                //Primera consulta a logs
                ultima_peticion = new LogResponse();
                ultima_peticion.setFecha(Timestamp.valueOf(LocalDateTime.now().minusYears(500)));
                ultima_peticion.setId_entrada(null);
                Optional<List<LogResponse>> cosas = this.padre.getPrograma().PedirLogs(Timestamp.valueOf(LocalDateTime.now().minusYears(500)));
                if(cosas.isPresent()){
                    List<LogResponse> logs = cosas.get();
                    logs.sort((x,y) -> x.getFecha().compareTo(y.getFecha()));
                    this.renderGame(backgroundGraphics,logs);
                }
                
                
            }
            else{
                //no se, un mensaje de error o algo
            }
            
            this.isRunning = true;
        }
    }
    
    public void end_this(){
        this.isRunning = false;
        this.setVisible(false);
        while(this.drawing){}
        
        backgroundGraphics = null;
        strategy = null;
    }
    
    private void pintar_casilla(Long x,Long y){
        
                  //  System.out.println(x+" "+y);
        backgroundGraphics.setColor(mapa.get(x).get(y).getColor());
        backgroundGraphics.fillRect(
                (int) (x_0.intValue() + ((x - x_min) * (0 + width/zoom))),
                (int) (y_0.intValue() - ((y - y_min) * (0 + height/zoom))),
                width/zoom, height/zoom);
    }
    
    private void pintar_poi(Long x,Long y){
        
                  //  System.out.println(x+" "+y);
        backgroundGraphics.setColor(Color.YELLOW);
        backgroundGraphics.fillOval(
                x_0.intValue() + (x.intValue() - Long.valueOf(x_min).intValue()) * (0 + width/zoom),
                y_0.intValue() - (y.intValue() - Long.valueOf(y_min).intValue()) * (0 + height/zoom),
                width/zoom, height/zoom);
    }
    
    private void pintar_obj(Long x,Long y){
        
                  //  System.out.println(x+" "+y);
        backgroundGraphics.setColor(Color.GREEN);
        backgroundGraphics.fillOval(
                x_0.intValue() + (x.intValue() - Long.valueOf(x_min).intValue()) * (0 + width/zoom),
                y_0.intValue() - (y.intValue() - Long.valueOf(y_min).intValue()) * (0 + height/zoom),
                width/zoom, height/zoom);
    }
    
    private void pintar_agente(boolean propiedad,Long x,Long y){
        if(!this.mapa_grande || (x_min <= x && x <= x_max && y_min <= y && y <= y_max)){
        
            if(propiedad){
                backgroundGraphics.setColor(Color.BLUE);
            }
            else{
                backgroundGraphics.setColor(Color.RED);
            }
            backgroundGraphics.fillOval(
                    x_0.intValue() + (x.intValue() - Long.valueOf(x_min).intValue()) * (0 + width/zoom),
                    y_0.intValue() - (y.intValue() - Long.valueOf(y_min).intValue()) * (0 + height/zoom),
                    width/zoom, height/zoom);
        }
    }
    
    private void pintar_mapa(){
        for(long j=x_min;j<=x_max;j++){
            for(long k=y_min;k<=y_max;k++){
                this.pintar_casilla(j, k);
                if(mapa.get(j).get(k).getPOI() > 0){
                    this.pintar_poi(j, k);
                }
                if(mapa.get(j).get(k).getObj() > 0){
                    this.pintar_obj(j, k);
                }
            }
        }
        
                System.out.println(agentes.values().toArray().getClass());
        Object[] a = agentes.values().toArray();
        for (Object a1 : a) {
            this.pintar_agente(((AgentePanel)a1).getPropio(), ((AgentePanel)a1).getX(), ((AgentePanel)a1).getY());
        }
        
        
    }
    
    private void x_minmax(){
        if(foco.getX() < (zoom)/2){
            x_min = 0;
            x_max = zoom - 1;
        }    
        else if(foco.getX() > padre.getPrograma().getMapa_actual().getX_tam() - (zoom)/2 - 1){
            x_min = padre.getPrograma().getMapa_actual().getX_tam() - zoom;
            x_max = padre.getPrograma().getMapa_actual().getX_tam() - 1;
        }                
        else{
            x_min = foco.getX() - (zoom)/2;
            x_max = foco.getX() + (zoom)/2;
            if(x_max - x_min < (zoom)-1){
                x_max++;
            }
            else if(x_max - x_min > (zoom)-1){
                x_max--;
            }
        }
    }
    
    private void y_minmax(){
        if(foco.getY() < (zoom)/2){
            y_min = 0;
            y_max = zoom - 1;
        }
        else if(foco.getY() > padre.getPrograma().getMapa_actual().getY_tam() - (zoom)/2 - 1){
            y_min = padre.getPrograma().getMapa_actual().getY_tam() - zoom;
            y_max = padre.getPrograma().getMapa_actual().getY_tam() - 1;
        }
        else{
            y_min = foco.getY() - (zoom)/2;
            y_max = foco.getY() + (zoom)/2;
            if(y_max - y_min < (zoom)-1){
                y_max++;
            }
            else if(y_max - y_min > (zoom)-1){
                y_max--;
            }
        }
    }
    
    private void configureLayout(){
        //Posicionar canvas;
        layout.putConstraint(SpringLayout.WEST, canvas,
                     20,
                     SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, canvas,
                     20,
                     SpringLayout.NORTH, this);
        //Posicionar boton salir;
        layout.putConstraint(SpringLayout.EAST, salir,
                     -60,
                     SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, salir,
                     40,
                     SpringLayout.VERTICAL_CENTER, this);
        //Posicionar boton abandonar;
        layout.putConstraint(SpringLayout.EAST, abandonar,
                     -60,
                     SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, abandonar,
                     0,
                     SpringLayout.VERTICAL_CENTER, this);
        //Posicionar boton invitar;
        layout.putConstraint(SpringLayout.EAST, invitar,
                     -60,
                     SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, invitar,
                     -40,
                     SpringLayout.VERTICAL_CENTER, this);
        //Posicionar etiqueta;
        layout.putConstraint(SpringLayout.WEST, sesionId,
                     5,
                     SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.SOUTH, sesionId,
                     -5,
                     SpringLayout.SOUTH, this);

    }
    //Aqui pedimos cosas al servidor
    private List<LogResponse> updateGame() {
        //Probemos que se ejecuta
        //Timestamp aux = (Timestamp) peticion.clone();
        //peticion = Timestamp.valueOf(LocalDateTime.now().minusMinutes(1));
        Optional<List<LogResponse>> cosas = this.padre.getPrograma().PedirLogs(this.ultima_peticion.getFecha());
        if(cosas.isPresent() && !cosas.get().isEmpty()){
            List<LogResponse> logs = cosas.get();
            logs.sort((x,y) -> x.getFecha().compareTo(y.getFecha()));
            return logs;
        }
        else{
            return new ArrayList();
        }

    }
    //Aqui hacemos los cambios graficos
    private void renderGame(Graphics2D g,List<LogResponse> logs) {
        for(int i=0;i<logs.size();i++){
            if(!Objects.equals(logs.get(i).getId_entrada(), this.ultima_peticion.getId_entrada())){
                switch(logs.get(i).getEvento()){
                    case "MOV_LEFT":case "MOV_RIGHT":
                        if(this.mapa
                                .get(agentes.get(logs.get(i).getId_sujeto()).getX())
                                .get(agentes.get(logs.get(i).getId_sujeto()).getY())
                                .getAgentes() == 1
                        ){
                            this.pintar_casilla(
                                    agentes.get(logs.get(i).getId_sujeto()).getX(),
                                    agentes.get(logs.get(i).getId_sujeto()).getY());
                        }
                        else{
                            RepintarAgenteCasillaElse(
                                    agentes.get(logs.get(i).getId_sujeto()).getX(),
                                    agentes.get(logs.get(i).getId_sujeto()).getY(),
                                    logs.get(i).getId_sujeto()
                            );
                        }

                        this.mapa.get(agentes.get(logs.get(i).getId_sujeto()).getX())
                        .get(agentes.get(logs.get(i).getId_sujeto()).getY()).decAgentes();
                        this.agentes.get(logs.get(i).getId_sujeto()).setXY(logs.get(i).getVal_1(), logs.get(i).getVal_2());
                        this.mapa
                            .get(logs.get(i).getVal_1())
                            .get(logs.get(i).getVal_2())
                            .incAgentes();

                        if(this.mapa_grande && Objects.equals(logs.get(i).getId_sujeto(), foco.getId())){
                            long aux = this.x_max;
                            this.x_minmax();
                            if(aux != x_max){
                                this.pintar_mapa();
                            }
                        }
                        this.pintar_agente(agentes.get(logs.get(i).getId_sujeto()).getPropio(), logs.get(i).getVal_1(), logs.get(i).getVal_2());
                        break;
                    case "MOV_DOWN":case "MOV_UP":
                        if(this.mapa
                                .get(agentes.get(logs.get(i).getId_sujeto()).getX())
                                .get(agentes.get(logs.get(i).getId_sujeto()).getY())
                                .getAgentes() == 1
                        ){
                            this.pintar_casilla(
                                    agentes.get(logs.get(i).getId_sujeto()).getX(),
                                    agentes.get(logs.get(i).getId_sujeto()).getY());
                        }
                        else{
                            RepintarAgenteCasillaElse(
                                    agentes.get(logs.get(i).getId_sujeto()).getX(),
                                    agentes.get(logs.get(i).getId_sujeto()).getY(),
                                    logs.get(i).getId_sujeto()
                            );
                        }

                        this.mapa.get(agentes.get(logs.get(i).getId_sujeto()).getX())
                        .get(agentes.get(logs.get(i).getId_sujeto()).getY()).decAgentes();
                        this.agentes.get(logs.get(i).getId_sujeto()).setXY(logs.get(i).getVal_1(), logs.get(i).getVal_2());
                        this.mapa
                            .get(logs.get(i).getVal_1())
                            .get(logs.get(i).getVal_2())
                            .incAgentes();

                        if(this.mapa_grande && Objects.equals(logs.get(i).getId_sujeto(), foco.getId())){
                            long aux = this.y_max;
                            this.y_minmax();
                            if(aux != y_max){
                                this.pintar_mapa();
                            }
                        }
                        this.pintar_agente(agentes.get(logs.get(i).getId_sujeto()).getPropio(), logs.get(i).getVal_1(), logs.get(i).getVal_2());
                        break;
                    case "MOV_UP_LEFT":case "MOV_UP_RIGHT":case "MOV_DOWN_LEFT":case "MOV_DOWN_RIGHT":
                        if(this.mapa
                                .get(agentes.get(logs.get(i).getId_sujeto()).getX())
                                .get(agentes.get(logs.get(i).getId_sujeto()).getY())
                                .getAgentes() == 1
                        ){
                            this.pintar_casilla(
                                    agentes.get(logs.get(i).getId_sujeto()).getX(),
                                    agentes.get(logs.get(i).getId_sujeto()).getY());
                        }
                        else{
                            RepintarAgenteCasillaElse(
                                    agentes.get(logs.get(i).getId_sujeto()).getX(),
                                    agentes.get(logs.get(i).getId_sujeto()).getY(),
                                    logs.get(i).getId_sujeto()
                            );
                        }

                        this.mapa.get(agentes.get(logs.get(i).getId_sujeto()).getX())
                        .get(agentes.get(logs.get(i).getId_sujeto()).getY()).decAgentes();
                        this.agentes.get(logs.get(i).getId_sujeto()).setXY(logs.get(i).getVal_1(), logs.get(i).getVal_2());
                        this.mapa
                            .get(logs.get(i).getVal_1())
                            .get(logs.get(i).getVal_2())
                            .incAgentes();
                        
                        if(this.mapa_grande && Objects.equals(logs.get(i).getId_sujeto(), foco.getId())){
                            long aux_x = this.x_max;
                            long aux_y = this.y_max;
                            this.x_minmax();
                            this.y_minmax();
                            if(aux_x != x_max || aux_y != y_max){
                                this.pintar_mapa();
                            }
                        }
                        this.pintar_agente(agentes.get(logs.get(i).getId_sujeto()).getPropio(), logs.get(i).getVal_1(), logs.get(i).getVal_2());
                        break;
                    case "POS_OBJ":
                        this.mapa.get(agentes.get(logs.get(i).getId_sujeto()).getX())
                        .get(agentes.get(logs.get(i).getId_sujeto()).getY()).decObj();
                        break;
                    case "POS_POI":
                        this.mapa.get(agentes.get(logs.get(i).getId_sujeto()).getX())
                        .get(agentes.get(logs.get(i).getId_sujeto()).getY()).decPOI();
                        break;
                    case "ENTER":
                        if(!agentes.containsKey(logs.get(i).getId_sujeto())){
                            this.mapa.get(logs.get(i).getVal_1()).get(logs.get(i).getVal_2()).incAgentes();
                            agentes.put(
                                    logs.get(i).getId_sujeto(),
                                    new AgentePanel(false,logs.get(i).getId_sujeto(),logs.get(i).getVal_1(),logs.get(i).getVal_2())
                            );
                            
                        }
                        this.pintar_agente(agentes.get(logs.get(i).getId_sujeto()).getPropio(), logs.get(i).getVal_1(), logs.get(i).getVal_2());
                        break;
                    case "EXIT":
                        if(agentes.containsKey(logs.get(i).getId_sujeto())){
                            
                            this.RepintarAgenteCasilla(logs.get(i).getVal_1(), logs.get(i).getVal_2(),logs.get(i).getId_sujeto());
                            this.mapa.get(logs.get(i).getVal_1()).get(logs.get(i).getVal_2()).decAgentes();
                            agentes.remove(logs.get(i).getId_sujeto());
                        }
                        break;
                    default:
                        break;
                }
                //System.out.println("Log: "+logs.get(i).getId_entrada()+ " "+ logs.get(i).getFecha().toString());
                ultima_peticion = logs.get(i);
            }
        }
    }
    
    protected boolean actualizarSesion(){

        List<LogResponse> logs = updateGame();
            // Update Graphics
        do {
            this.drawing = true;
            Graphics2D bg = getBuffer();
            if(bg != null){
                renderGame(backgroundGraphics,logs); // this calls your draw method
                // thingy
                if (scale != 1) {
                    bg.drawImage(background, 0, 0, width * scale, height
                            * scale, 0, 0, width, height, null);
                } else {
                    bg.drawImage(background, 0, 0, null);
                }
                bg.dispose();
            }
            this.drawing = false;
        } while (!updateScreen());
        return true;
    }

    private boolean updateScreen() {
        try {
            graphics.dispose();
        }
        catch (Exception e){
            return true;
        }
        graphics = null;
        try {
            strategy.show();
            Toolkit.getDefaultToolkit().sync();
            return (!strategy.contentsLost());

        } catch (NullPointerException e) {
            return true;

        } catch (IllegalStateException e) {
            return true;
        }
    }
    
    private Graphics2D getBuffer() {
        if (graphics == null) {
            try {
                graphics = (Graphics2D) strategy.getDrawGraphics();
            } catch (Exception e) {
                return null;
            }
        }
        return graphics;
    }

    protected void disposeFrame_invitar() {
        if(frame_invitar != null){
            frame_invitar.dispose();
        }
    }
    
    
    private void RepintarAgenteCasilla(Long casilla_x,Long casilla_y,Long id_agente){
        if(this.mapa
            .get(casilla_x)
            .get(casilla_y)
            .getAgentes() == 1
        ){
            this.pintar_casilla(
                agentes.get(id_agente).getX(),
                agentes.get(id_agente).getY()
            );
        }
        else{
            RepintarAgenteCasillaElse(
                agentes.get(id_agente).getX(),
                agentes.get(id_agente).getY(),
                id_agente
            );
        }
    }
    
    private void RepintarAgenteCasillaElse(Long x,Long y,Long id){
        if(!this.mapa_grande || (x_min <= x && x <= x_max && y_min <= y && y <= y_max)){
            Iterator<AgentePanel> remanente = agentes.values().iterator();
            AgentePanel examinado;
            boolean fin = false;
            while(remanente.hasNext() && !fin){
                examinado = remanente.next();
                if(
                        Objects.equals(examinado.getX(), x) &&
                        Objects.equals(examinado.getY(), y) &&
                        !Objects.equals(examinado.getId(), id)
                ){

                    this.pintar_agente(
                            examinado.getPropio(),
                            x,
                            y
                    );
                    fin = true;
                }
            }
        }
    }

    
    
    private class SalirPress implements ActionListener {

        public SalirPress() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            end_this();
            disposeFrame_invitar();
            padre.getPrograma().Reset();
            padre.CambiarPanel("sesion", "menu");
        }
    }

    private class AbandonarPress implements ActionListener {

        public AbandonarPress() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            end_this();
            disposeFrame_invitar();
            padre.getPrograma().PedirEliminarSesion();
            padre.getPrograma().Reset();
            padre.CambiarPanel("sesion", "menu");
        }
    }
    
    private class InvitarPress implements ActionListener {
        
        private PanelInvitar Invitar;
        
        public InvitarPress() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // JFrame
            frame_invitar = new JFrame();
            frame_invitar.addWindowListener(new FrameClose());
            frame_invitar.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            frame_invitar.setSize(padre.getWidth() *  padre.getScale(), padre.getHeight() * padre.getScale());
            frame_invitar.setLocation(padre.getWidth() / 100 * 110, 0);
            
            // Login
            Invitar = new PanelInvitar(padre,frame_invitar);
            Invitar.setSize(width *  scale, height * scale);
            //Login.setVisible(false);
            Invitar.setVisible(true);
            frame_invitar.add(Invitar);
            
            frame_invitar.setVisible(true);
        }
        
        private class FrameClose extends WindowAdapter {
            @Override
            public void windowClosing(final WindowEvent e) {
                e.getWindow().dispose();
            }
        }
    }
    
    
    
}
