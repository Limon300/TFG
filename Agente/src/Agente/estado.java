
package Agente;

import static java.lang.Thread.sleep;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pruebagrafica.EntornoLocal;
import pruebagrafica.EntornoVirtual;
import pruebagrafica.datos.Agente;
import pruebagrafica.datos.Casilla;
import pruebagrafica.datos.ObjetivoSesionResponse;
import pruebagrafica.datos.SesionData;

/*Represents the state of the agent, including its vision, current position, target position,
 * algorithm in use, and other relevant attributes. */
 
public class estado {


     // Current vision field of the agent (starts from the upper-left diagonal
    // clockwise, ending in the cell to the left of the agent))
    private int[] vision;
    // Next move (updated in think and used in move)
    private int nextMove;
    // Current Position
    private int[] currentPosition = new int[3];
    // Casilla objetivo
    private int[] targetPosition = new int[2];
    // Algoritmo en uso
    private int alg;
    //Distancia del obstaculo al objetivo
    private double distanciaObstaculo = 0;
    //Direccion de la mano
    private int mano;
    //Esta en la primera iteracion de alg3?
	// Is it in the first iteration of alg3?
    private boolean inicio;
    //Ha vuelto al mismo punto?
	// Has it returned to the same point?
    private int[] vuelta = new int[3];
    //Direccion de la mano al chocar con el obstaculo o al reposicionarse si se encuentra con el en una diagonal
	// Direction of the hand when hitting the obstacle or repositioning if it finds it on a diagonal
    private int mano_inicial;
    // Intentos restantes para Alg_2
	 // Remaining attempts for Alg_2
    private int attempts_Alg2 = 8;  // Initializes with 5 attempts
    // Direction of algorithm 3
    private int direction = 1;
    // Initial distance in algorithm 2
    private double distanciaAlg2;
    
    //Id agente
    private Long id_agente;
    //Id sesion
    private SesionData id_sesion;
    
	
	//Constructor that intialize the estado object with the current and target positions and the algorithm in use
    public estado(EntornoVirtual env,Long id_store){
        
        //Determina que sesion tiene
        this.id_sesion = null;
        do{
            if(env.ConsultarSesiones() == null || env.ConsultarSesiones().isEmpty()){
                try {
                    System.out.println("Esperando sesion");
                    sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(estado.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{
                System.out.println(env.ConsultarSesiones().getFirst().getId_sesion());
                this.id_sesion = env.ConsultarSesiones().getFirst();
            }
        }while(this.id_sesion == null);
        
        //Determina que agente va a controlar
        List<Agente> agentes = env.ConsultarMisAgentes();
        id_agente = null;
        int i=0;
        while(id_agente == null){
            if(i >= agentes.size() && id_agente == null){
                i = 0;
                try {
                    System.out.println("Agente no encontrado");
                    sleep(2000);
                    agentes = env.ConsultarMisAgentes();
                } catch (InterruptedException ex) {
                    Logger.getLogger(estado.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{
                if(agentes.get(i).getId_store() == id_store){
                    this.id_agente = agentes.get(i).getId_juego();
                }
                i++;
            }
        }
        
        //Determinar posicion inicial
        Casilla actual = env.UsarSensor(this.id_agente, Long.valueOf(945254358)).getCasillas().getFirst();  
        this.currentPosition[0] = actual.getX().intValue();
        this.currentPosition[1] = actual.getY().intValue();
        this.currentPosition[2] = actual.getZ().intValue();
        
        System.out.println("Posicion: " + this.currentPosition[0] + " y " + this.currentPosition[1]);
        
        //Determinar primer objetivo
        List<ObjetivoSesionResponse> objetivos = env.ConsultarObjetivos();
        this.targetPosition[0] = -1;
        this.targetPosition[1] = -1;
        if(objetivos.isEmpty()){
            System.out.println("No hay objetivos");
        }
        else{
            i = 0;
            for(int j = 0;j < objetivos.size();j++){
                if("on".equals(objetivos.get(i).getEstatus())){
                    this.targetPosition[0] = objetivos.get(i).getX().intValue();
                    this.targetPosition[1] = objetivos.get(i).getY().intValue();
                }
            }
        }
        System.out.println("Objetivo: " + this.targetPosition[0] + " y " + this.targetPosition[1]);
        
        //Algoritmo inicial
        this.alg = 0;
    }

    public Long getId_agente() {
        return id_agente;
    }
    
    

    //Getters and setters
    public double getDistanciaAlg2() {
        return distanciaAlg2;
    }

    public void setDistanciaAlg2(double distanciaAlg2) {
        this.distanciaAlg2 = distanciaAlg2;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
    
    public boolean getInicio(){
        return this.inicio;
    }
    
    public void setInicio(boolean i){
        if(i){
            this.vuelta[0]=0;
            this.vuelta[1]=0;
        }
        this.inicio = i;
    }

    public int getAttemptsAlg2(){
        return attempts_Alg2;
    }

    public void setAttemptsAlg2(int i) {
        this.attempts_Alg2 = i;
    }
    
	// Method that updates the vuelta attribute based on the provided index
    public void updateVuelta(int i){
        switch (i) {
            
            case 0:
                this.vuelta[0]--;
                this.vuelta[1]--;
                //this.env.moveLeftUp();        
                break;
            
            case 1:
                this.vuelta[1]--;
                //this.env.moveLeft();
                break;
            
            case 2:
                this.vuelta[0]++;
                this.vuelta[1]--;
                //this.env.moveLeftDown();
                break;
            
            case 3:
                this.vuelta[0]++;
                //this.env.moveDown();
                break;
            
            case 4:
                this.vuelta[0]++;
                this.vuelta[1]++;
                //this.env.moveRightDown();
                break;
            
            case 5:
                this.vuelta[1]++;
                //this.env.moveRight();
                break;
            
            case 6:
                this.vuelta[0]--;
                this.vuelta[1]++;
                //this.env.moveRightUp();
                break;
            
            case 7:
                this.vuelta[0]--;
                //this.env.moveUp();
                break;
        }
    }
    
	// Method that checks if the agent is in a stalemate situation
    public boolean checkStalemate(){
        return (this.vuelta[0]==0)&&(this.vuelta[1]==0)&&!this.inicio&&(this.mano==this.mano_inicial);
    }
    
    public int getAlg(){
        return alg;
    }
    
    public void setAlg(int i){
        this.alg = i;
    }

    public int[] getCurrentPosition() {
        return currentPosition;
    }

    // Method that updates the agent's position based on the provided index
    public void updatePosition(int i){
        switch (i) {
            
            case 0:
                this.currentPosition[0]--;
                this.currentPosition[1]--;
                //this.env.moveLeftUp();        
                break;
            
            case 1:
                this.currentPosition[1]--;
                //this.env.moveLeft();
                break;
            
            case 2:
                this.currentPosition[0]++;
                this.currentPosition[1]--;
                //this.env.moveLeftDown();
                break;
            
            case 3:
                this.currentPosition[0]++;
                //this.env.moveDown();
                break;
            
            case 4:
                this.currentPosition[0]++;
                this.currentPosition[1]++;
                //this.env.moveRightDown();
                break;
            
            case 5:
                this.currentPosition[1]++;
                //this.env.moveRight();
                break;
            
            case 6:
                this.currentPosition[0]--;
                this.currentPosition[1]++;
                //this.env.moveRightUp();
                break;
            
            case 7:
                this.currentPosition[0]--;
                //this.env.moveUp();
                break;
        }
    }
    

    

    public int[] getVision() {
        return vision;
    }

    public void setVision(int[] vision) {
        this.vision = vision;
    }

    public double getDistanciaObstaculo() {
        return distanciaObstaculo;
    }

    public void setDistanciaObstaculo(double distancia) {
        this.distanciaObstaculo = distancia;
    }
    
    public int getMano(int i){
        return ((((this.mano + i) % 8) + 8) % 8);
    }
    
    public void setMano(int i){
        this.mano = (((i % 8) + 8) % 8);
    }
    
    public void setManoInicial(int i){
        this.mano_inicial = (((i % 8) + 8) % 8);
    }

    //Calculates and returns the distance to the target position
    public double getDistanciaObjetivo() {
        return this.actualizarDistanciaObjetivo(this.currentPosition[0], this.currentPosition[1]);
    }

    public int getNextMove() {
        return this.nextMove;
    }

    public void setNextMove(int next_move) {
        this.nextMove = next_move;
    }

    // Actualiza "distancia" del la posicion pasada como argumento con respecto al objetivo
	// Updates the "distance" from the position passed as an argument with respect to the objective
    public double actualizarDistanciaObjetivo(int x,int y) {

        // Accedemos a la casilla objetivo
        int target_x = targetPosition[0];
        int target_y = targetPosition[1];
        // Calculamos la distancia euclídea
        double dx = Math.pow(target_x - x, 2);
        double dy = Math.pow(target_y - y, 2);

        return Math.sqrt(dx + dy);
    }
    //Para detectar que hemos llegado al objetivo
	//Checks if the agent has reached its goal or a stalemate
    public boolean end(Long sensor){
        
        
        if(8==this.nextMove) {
            System.out.println("Hemos alcanzado un punto muerto");
            return true;
        }
        else if(-1==this.targetPosition[0]&&-1==this.targetPosition[1]){
            System.out.println("No hay objetivos validos");
            return true; 
        }
        else if(this.currentPosition[0]==this.targetPosition[0]&&this.currentPosition[1]==this.targetPosition[1]){
            System.out.println("Hemos llegado al objetivo");
            return true;
        }
        else if(sensor == 0){
            System.out.println("Nos hemos quedado sin bateria");
            return true;
        }
        else{
            return false;
        }
        
    }

}
