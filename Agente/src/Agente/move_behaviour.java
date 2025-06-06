package Agente;


import jade.core.behaviours.*;
import java.util.ArrayList;
import java.util.List;
import pruebagrafica.EntornoVirtual;

//Represents the behavior of the agent related to movement.
public class move_behaviour extends Behaviour{
    
	// The state of the agent
    private estado state;
	// The environment in which the agent operates
    private EntornoVirtual env;
    private List<Long> habilidades;
    
	// Constructor that initializes the move behavior with the given agent state and environment
    public move_behaviour(estado a, EntornoVirtual env) {
        
        super();
        this.state = a;
        this.env = env;
        this.habilidades = new ArrayList();
        this.habilidades.add(Long.valueOf(894018788));
        this.habilidades.add(Long.valueOf(888913919));
        this.habilidades.add(Long.valueOf(450601107));
        this.habilidades.add(Long.valueOf(140169230));
        this.habilidades.add(Long.valueOf(687048843));
        this.habilidades.add(Long.valueOf(419345001));
        this.habilidades.add(Long.valueOf(119382076));
        this.habilidades.add(Long.valueOf(283020845));
        /*
        0 - Abajo izquierda     894018788
        1 - Abajo               888913919
        2 - Abajo derecha       450601107
        3 - Derecha             140169230
        4 - Arriba derecha      687048843
        5 - Arriba              419345001
        6 - Arriba izquierda    119382076
        7 - Izquierda           283020845
        */
        
        
    }
    
    @Override
    public void action() {
        
        // Accedemos al agente
		// Access the agent's next move
        int next_move = state.getNextMove();
        
        // Realizamos movimiento --> actualizamos env.currentPosition
		// Perform movement --> update env.currentPosition
        System.out.println("Vamos a movernos");        
        System.out.println("X: "+this.state.getCurrentPosition()[0]);
        System.out.println("Y: "+this.state.getCurrentPosition()[1]);
        boolean accion = this.env.UsarHabilidad(state.getId_agente(), this.habilidades.get(next_move)).isExito();
        
        if(accion){
            state.updatePosition(state.getNextMove());
            System.out.println("Exito");
        }
        /*
        switch (next_move) {
            
            case 0:
                       
                break;
            
            case 1:
                this.env.moveLeft();
                break;
            
            case 2:
                this.env.moveLeftDown();
                break;
            
            case 3:
                this.env.moveDown();
                break;
            
            case 4:
                this.env.moveRightDown();
                break;
            
            case 5:
                this.env.moveRight();
                break;
            
            case 6:
                this.env.moveRightUp();
                break;
            
            case 7:
                this.env.moveUp();
                break;
        }
        */
        // Actualizamos a.distanciaObjetivo
		//Upadates a.distanciaObjetivo

              
    }
    @Override
    public boolean done(){
		// Indicate that the behavior is not finished
        return false;
    }
}